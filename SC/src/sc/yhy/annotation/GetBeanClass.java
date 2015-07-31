package sc.yhy.annotation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;

/**
 * 获取并反射bean对像，装配action
 * 
 * @author YHY
 *
 */
public class GetBeanClass {
	static final Logger logfile = Logger.getLogger(GetBeanClass.class.getName());
	private static ConcurrentHashMap<String, ClassMapping> actionMappingsMap = new ConcurrentHashMap<String, ClassMapping>();
	private static ConcurrentHashMap<String, ClassBean> beanMappingsMap = new ConcurrentHashMap<String, ClassBean>();

	public void init() {
		logfile.info(" start init package all class");
		// 获取所有类的包
		this.packagesClasss();
	}

	public static ClassMapping getMappings(String key) {
		synchronized (actionMappingsMap) {
			return actionMappingsMap.get(key);
		}
	}

	public static ClassBean getClassBean(String key) {
		synchronized (beanMappingsMap) {
			return beanMappingsMap.get(key);
		}
	}

	public static void clearMappings() {
		actionMappingsMap.clear();
		actionMappingsMap = null;
		beanMappingsMap.clear();
		beanMappingsMap = null;
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
					if (absolutePath.lastIndexOf(".class") != -1 && absolutePath.indexOf("annotation") == -1) {
						absolutePath = absolutePath.substring(absolutePath.indexOf("classes") + 8);
						packagePath = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator)).replaceAll("\\" + File.separator, "\\.");
						className = absolutePath.substring(absolutePath.lastIndexOf(File.separator) + 1, absolutePath.lastIndexOf("."));
						this.classForName(packagePath + "." + className);
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

	private void classForName(String classPack) {
		try {
			Class<?> clazz = Class.forName(classPack);
			// 判断是否为Action注解
			if (clazz.isAnnotationPresent(Action.class)) {
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
			} else if (clazz.isAnnotationPresent(BeanToTable.class)) {
				BeanToTable btt = clazz.getAnnotation(BeanToTable.class);
				beanMappingsMap.put(classPack, new ClassBean(clazz, classPack, btt.name()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
