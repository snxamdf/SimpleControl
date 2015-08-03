package sc.yhy.core;

import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.annotation.bean.ClassMapping;

/**
 * 程序调用类入口
 * 
 * @author YHY
 *
 */
public class Entrance {
	public void getBeanClass() {
		new GetBeanClass();
	}

	public void getProperties() {
		new Properties();
	}

	public static ClassMapping getMappings(String key) {
		synchronized (GetBeanClass.actionMappingsMap) {
			return GetBeanClass.actionMappingsMap.get(key);
		}
	}

	public static ClassBean getClassBean(String key) {
		synchronized (GetBeanClass.beanMappingsMap) {
			return GetBeanClass.beanMappingsMap.get(key);
		}
	}

	public static String getPropertie(String key) {
		synchronized (GetBeanClass.propertieBeanMappingsMap) {
			return GetBeanClass.propertieBeanMappingsMap.get(key);
		}
	}

	public static void clearMappings() {
		GetBeanClass.actionMappingsMap.clear();
		GetBeanClass.actionMappingsMap = null;
		GetBeanClass.beanMappingsMap.clear();
		GetBeanClass.beanMappingsMap = null;
		GetBeanClass.propertieBeanMappingsMap.clear();
		GetBeanClass.propertieBeanMappingsMap = null;
	}
}
