package com.yhy.test.main;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sc.yhy.util.Util;

import com.yhy.test.entity.TestBean;
import com.yhy.test.entity.TestBeanSon;

public class MainTest {

	public static void main(String[] args) throws Exception {
		System.out.println(StringUtils.isNotEmpty(null));
		System.out.println(StringUtils.isEmpty(""));
		System.out.println(StringUtils.isEmpty("null"));
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

class TestFanXing<T> {
	private T t;

	public List<T> getTest() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		Class<T> persistentClass = (Class<T>) getSuperClassGenricType(getClass(), 0);
		System.out.println(persistentClass);
		return null;
	}

	public static Class<Object> getSuperClassGenricType(final Class clazz, final int index) {

		// 返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		// 返回表示此类型实际类型参数的 Type 对象的数组。
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}

		return (Class) params[index];
	}

}
