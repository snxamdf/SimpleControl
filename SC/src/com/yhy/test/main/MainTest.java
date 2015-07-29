package com.yhy.test.main;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import sc.yhy.util.Util;

import com.yhy.test.entity.TestBean;
import com.yhy.test.entity.TestBeanSon;

public class MainTest {

	public static void main(String[] args) throws Exception {
		List<TestBeanSon> listSon = new ArrayList<TestBeanSon>();
		TestBeanSon tb1 = new TestBeanSon();
		tb1.setTestid("123123");
		tb1.setTestName("sn sn 杨");
		listSon.add(tb1);
		listSon.add(tb1);
		listSon.add(tb1);
		listSon.add(tb1);
		listSon.add(tb1);
		TestBean bean = new TestBean();
		bean.setListSon(listSon);
		Class<?> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Util.isList(field.getGenericType().toString())) {
				collection(field, bean);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void collection(Field field, Object bean) throws Exception {
		Type tp = field.getGenericType();
		if (tp == null)
			return;
		if (tp instanceof ParameterizedType) {// 判断是否为泛型
			ParameterizedType pt = (ParameterizedType) tp;
			Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
			if (!Util.isFieldType(genericClazz.getName())) {
				String getMeghtod = toGetMethod(field.getName());
				Object value = bean.getClass().getMethod(getMeghtod).invoke(bean);
				if (value != null) {
					List<Object> listObj = (List<Object>) value;
					for (Object obj : listObj) {
						System.out.println(obj);
					}
				}
			}
		}
	}

	static String toGetMethod(String fieldName) {
		return "get" + toFirstLetterUpperCase(fieldName);
	}

	static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}
}
