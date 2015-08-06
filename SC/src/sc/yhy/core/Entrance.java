package sc.yhy.core;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import sc.yhy.annotation.bean.ClassBean;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.data.DataSourceType;
import sc.yhy.servlet.interceptor.HandlerInterceptor;

/**
 * 程序调用类入口
 * 
 * @author YHY
 *
 */
public class Entrance {
	public void ScPackScan() {
		new ScPackScan();
	}

	public void getProperties() {
		new Properties();
	}

	public void getDataSourceType() {
		// 加载数据源别名
		new DataSourceType();
	}

	public static ClassMapping getMappings(String key) {
		synchronized (ScPackScan.actionMappingsMap) {
			return ScPackScan.actionMappingsMap.get(key);
		}
	}

	public static ConcurrentHashMap<String, ClassMapping> getActionMappingsMap() {
		synchronized (ScPackScan.actionMappingsMap) {
			return ScPackScan.actionMappingsMap;
		}
	}

	public static ClassBean getClassBean(String key) {
		synchronized (ScPackScan.beanMappingsMap) {
			return ScPackScan.beanMappingsMap.get(key);
		}
	}

	public static String getPropertie(String key) {
		synchronized (ScPackScan.propertieBeanMappingsMap) {
			return ScPackScan.propertieBeanMappingsMap.get(key);
		}
	}

	public static List<HandlerInterceptor> getInterceptor() {
		synchronized (ScPackScan.handlerInterceptors) {
			return ScPackScan.handlerInterceptors;
		}
	}

	public static void clearMappings() {
		ScPackScan.actionMappingsMap.clear();
		ScPackScan.actionMappingsMap = null;
		ScPackScan.beanMappingsMap.clear();
		ScPackScan.beanMappingsMap = null;
		ScPackScan.propertieBeanMappingsMap.clear();
		ScPackScan.propertieBeanMappingsMap = null;
	}
}
