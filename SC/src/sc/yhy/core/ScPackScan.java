package sc.yhy.core;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Interceptor;
import sc.yhy.annotation.annot.Order;
import sc.yhy.annotation.annot.Value;
import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.servlet.interceptor.HandlerInterceptor;
import sc.yhy.util.Constant;

/**
 * 获取并反射bean对像，装配action
 * 
 * @author YHY
 *
 */
class ScPackScan {
	static final Logger logfile = Logger.getLogger(ScPackScan.class.getName());
	// action 映射
	static ConcurrentHashMap<String, ClassMapping> actionMappingsMap = new ConcurrentHashMap<String, ClassMapping>();
	// entity 映射
	static ConcurrentHashMap<String, ClassBean> beanMappingsMap = new ConcurrentHashMap<String, ClassBean>();
	// 配置文件 propertie
	static ConcurrentHashMap<String, String> propertieBeanMappingsMap = new ConcurrentHashMap<String, String>();
	// 拦截器
	static List<HandlerInterceptor> handlerInterceptors = new LinkedList<HandlerInterceptor>();

	public ScPackScan() {
		logfile.info("开始初始化包的所有类");
		// 获取所有类的包
		this.packagesClasss();
		// 排序
		this.sort();
	}

	private void sort() {
		logfile.info("开始排序拦截器");
		// 排序
		Collections.sort(handlerInterceptors, new Comparator<HandlerInterceptor>() {
			public int compare(HandlerInterceptor arg0, HandlerInterceptor arg1) {
				Order arg0order = arg0.getClass().getAnnotation(Order.class);
				Order arg1order = arg1.getClass().getAnnotation(Order.class);
				return arg0order.value().compareTo(arg1order.value());
			}
		});
	}

	private void packagesClasss() {
		TreeMap<File, LinkedList<File>> dirFiles = new TreeMap<File, LinkedList<File>>();
		String absolutePath = null, packagePath, className;
		String path = this.getClass().getClassLoader().getResource("").getPath();
		File file = new File(path);
		this.getDirectoryFiles(file, dirFiles);
		Iterator<File> iterator = dirFiles.keySet().iterator();
		while (iterator.hasNext()) {
			File dir = iterator.next();
			LinkedList<File> fileInDir = dirFiles.get(dir);
			if (fileInDir != null) {
				Iterator<File> it = fileInDir.iterator();
				while (it.hasNext()) {
					absolutePath = it.next().getAbsolutePath();
					// 判断当前文件是class类
					if (absolutePath.lastIndexOf(Constant.$CLASS) != -1) {
						absolutePath = absolutePath.substring(absolutePath.indexOf(Constant.CLASSES) + 8);
						packagePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)).replaceAll("\\" + File.separator, "\\.");
						className = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1, absolutePath.lastIndexOf(Constant.POINT));
						this.classForName(packagePath + Constant.POINT + className);
					}
				}
			}
		}
	}

	private void getDirectoryFiles(File dir, TreeMap<File, LinkedList<File>> dirFiles) {
		if (!dir.isDirectory()) {
			return;
		}
		LinkedList<File> files = new LinkedList<File>();
		File[] filesinDir = dir.listFiles();
		if (filesinDir.length > 0) {
			for (int i = 0; i < filesinDir.length; i++) {
				files.add(filesinDir[i]);
			}
		} else {
			dirFiles.put(dir, null);
			return;
		}
		dirFiles.put(dir, files);
		for (int i = 0; i < filesinDir.length; i++) {
			if (filesinDir[i].isDirectory()) {
				getDirectoryFiles(filesinDir[i], dirFiles);
			}
		}
	}

	// 创建对像
	private void classForName(String classPack) {
		try {
			Class<?> clazz = Class.forName(classPack);
			// 判断是否为Action注解
			if (clazz.isAnnotationPresent(Action.class)) {
				// 获取action方法里映射的mapping
				this.getClassMethod(clazz, classPack);
			} else if (clazz.isAnnotationPresent(BeanToTable.class)) {
				BeanToTable btt = clazz.getAnnotation(BeanToTable.class);
				beanMappingsMap.put(classPack, new ClassBean(clazz, classPack, btt.name()));
			} else if (clazz.isAnnotationPresent(Interceptor.class)) {
				// 判断是否实现拦截器
				if (HandlerInterceptor.class.isAssignableFrom(clazz)) {
					if (clazz.isAnnotationPresent(Order.class)) {
						this.getHandlerInterceptor(clazz);
					} else {
						throw new Exception(clazz.toString() + " is not sc.yhy.annotation.annot.Order annotations;");
					}
				} else {
					throw new Exception(clazz.toString() + " is not implements sc.yhy.servlet.interceptor.HandlerInterceptor interface;");
				}
			}
			// 获取类里@Value字段
			this.getClassField(clazz, classPack);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取action类里映射的mapping
	// 用于处理action url
	private void getClassMethod(Class<?> clazz, String classPack) {
		String mappingRoot = "";
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			mappingRoot = requestMapping.value();
		}
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				String methodName = m.getName();
				String mappingMethod = rm.value();
				actionMappingsMap.put(mappingRoot + mappingMethod, new ClassMapping(clazz, classPack, mappingRoot, mappingMethod, methodName));
			}
		}
	}

	// 获取action类里字段
	// 用于处理配置文件 value取值
	private void getClassField(Class<?> clazz, String classPack) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Value.class)) {
				Value value = field.getAnnotation(Value.class);
				String valueStr = value.value();
				String key = this.get$Value(valueStr);
				if (key != null) {
					propertieBeanMappingsMap.put(classPack + Constant.POINT + field.getName(), Properties.getValue(key));
				}
			}
		}
	}

	// 获取拦截器实例
	// HandlerInterceptor
	public void getHandlerInterceptor(Class<?> clazz) {
		try {
			handlerInterceptors.add((HandlerInterceptor) clazz.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private String get$Value(String value) {
		String regex = "\\$\\{(.*)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			String group = matcher.group(1);
			return group;
		}
		return null;
	}
}
