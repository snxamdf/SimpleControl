package sc.yhy.annotation;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;

public class GetBeanClass {
	private static ConcurrentHashMap<String, ClassMapping> mappingsMap = new ConcurrentHashMap<String, ClassMapping>();

	public void init() {
		// 获取所有类的包
		this.packagesClasss();
	}

	public static ClassMapping getMappings(String key) {
		synchronized (mappingsMap) {
			return mappingsMap.get(key);
		}
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
						Map<String, ClassMapping> tempMap = this.classForName(packagePath + "." + className);
						if (tempMap.size() > 0) {
							mappingsMap.putAll(tempMap);
						}
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

	private ConcurrentHashMap<String, ClassMapping> classForName(String classPack) {
		ConcurrentHashMap<String, ClassMapping> mappingsMap = new ConcurrentHashMap<String, ClassMapping>();
		try {
			Class<?> clazz = Class.forName(classPack);
			Action action = clazz.getAnnotation(Action.class);
			// 判断是否为Action注解
			if (action != null) {
				String mappingRoot = "", mappingMethod = null, methodName = null;
				RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
				if (requestMapping != null) {
					mappingRoot = requestMapping.value();
				}
				Method[] methods = clazz.getMethods();
				for (Method m : methods) {
					RequestMapping rm = m.getAnnotation(RequestMapping.class);
					if (rm != null) {
						methodName = m.getName();
						mappingMethod = rm.value();
						mappingsMap.put(mappingRoot + mappingMethod, new ClassMapping(clazz, classPack, mappingRoot, mappingMethod, methodName));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mappingsMap;
	}
}
