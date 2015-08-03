package sc.yhy.core;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Value;
import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.util.Constant;

/**
 * 获取并反射bean对像，装配action
 * 
 * @author YHY
 *
 */
class GetBeanClass {
	static final Logger logfile = Logger.getLogger(GetBeanClass.class.getName());
	static ConcurrentHashMap<String, ClassMapping> actionMappingsMap = new ConcurrentHashMap<String, ClassMapping>();
	static ConcurrentHashMap<String, ClassBean> beanMappingsMap = new ConcurrentHashMap<String, ClassBean>();
	static ConcurrentHashMap<String, String> propertieBeanMappingsMap = new ConcurrentHashMap<String, String>();

	public GetBeanClass() {
		logfile.info(" start init package all class");
		// 获取所有类的包
		this.packagesClasss();
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
					if (absolutePath.lastIndexOf(Constant.$CLASS) != -1) {
						absolutePath = absolutePath.substring(absolutePath.indexOf(Constant.CLASSES) + 8);
						packagePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)).replaceAll("\\" + File.separator, "\\.");
						className = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1, absolutePath.lastIndexOf(Constant.POINT));
						this.classForName(packagePath + Constant.POINT + className);
					}
				}
			}
		}
		logfile.info(" end init package all class");
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
			if (classPack.indexOf(Constant.ANNOTATION) == -1) {
				// 判断是否为Action注解
				if (clazz.isAnnotationPresent(Action.class)) {
					// 获取action方法里映射的mapping
					this.getClassMethod(clazz, classPack);
				} else if (clazz.isAnnotationPresent(BeanToTable.class)) {
					BeanToTable btt = clazz.getAnnotation(BeanToTable.class);
					beanMappingsMap.put(classPack, new ClassBean(clazz, classPack, btt.name()));
				}
			}
			// 获取类里@Value字段
			this.getClassField(clazz, classPack);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取action类里映射的mapping
	private void getClassMethod(Class<?> clazz, String classPack) {
		String mappingRoot = "", mappingMethod = null, methodName = null;
		RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
		if (requestMapping != null) {
			mappingRoot = requestMapping.value();
		}
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			if (m.isAnnotationPresent(RequestMapping.class)) {
				RequestMapping rm = m.getAnnotation(RequestMapping.class);
				methodName = m.getName();
				mappingMethod = rm.value();
				actionMappingsMap.put(mappingRoot + mappingMethod, new ClassMapping(clazz, classPack, mappingRoot, mappingMethod, methodName));
			}
		}
	}

	// 获取action类里字段
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
