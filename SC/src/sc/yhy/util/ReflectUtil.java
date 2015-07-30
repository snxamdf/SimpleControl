package sc.yhy.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import sc.yhy.annotation.annot.BeanToTable;
import sc.yhy.annotation.annot.Identify;

import com.yhy.test.entity.Tran;

public class ReflectUtil {
	/**
	 * 通过反射, 获得定义Class时声明的父类的泛型参数的类型. 如无法找到, 返回Object.class.
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz, final int index) {
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
		return (Class<?>) params[index];
	}

	/**
	 * 获取bean.class
	 * 
	 * @param bean
	 * @return
	 */
	public static Class<?> getClass(Object entity) {
		Class<?> clases;
		if (!(entity instanceof Class)) {
			clases = entity.getClass();
		} else {
			clases = (Class<?>) entity;
		}
		return clases;
	}

	/**
	 * 判断类注解
	 */
	public static boolean isAnnotation(final Class<?> clazz, Class<? extends Annotation> class1) {
		return clazz.isAnnotationPresent(class1);
	}

	/**
	 * 判断字段注解
	 */
	public static boolean isAnnotation(Field field, Class<? extends Annotation> class1) {
		return field.isAnnotationPresent(class1);
	}

	/**
	 * 获取Identify注解属性的value值
	 * 
	 * @throws Exception
	 */
	public static Object[] getIdentify(Object entity) throws Exception {
		Class<?> clases = getClass(entity);
		Field[] fields = clases.getDeclaredFields();
		for (Field field : fields) {
			if (isAnnotation(field, Identify.class)) {
				Object[] result = new Object[2];
				result[0] = field.getName();
				result[1] = clases.getMethod(toGetMethod(field.getName())).invoke(entity);
				return result;
			}
		}
		return null;
	}

	/**
	 * 获取Identify是否为空
	 * 
	 * @param entity
	 * @return
	 */
	public static boolean identifyIsEmpty(Object entity) {
		try {
			return getIdentify(entity)[1] == null ? false : true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取字段get方法名称
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String toGetMethod(String fieldName) {
		return "get" + toFirstLetterUpperCase(fieldName);
	}

	/**
	 * 获取字段set方法名称
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String toSetMethod(String fieldName) {
		return "set" + toFirstLetterUpperCase(fieldName);
	}

	/**
	 * 转换字段首字母大写
	 * 
	 * @param fieldName
	 * @return
	 */
	public static String toFirstLetterUpperCase(String str) {
		if (str == null || str.length() < 2) {
			return str;
		}
		String firstLetter = str.substring(0, 1).toUpperCase();
		return firstLetter + str.substring(1, str.length());
	}

	/**
	 * 获取表名
	 * 
	 * @param clases
	 * @return
	 */
	public static String getTableName(Class<?> clases) {
		if (isAnnotation(clases, BeanToTable.class)) {
			BeanToTable beanTable = clases.getAnnotation(BeanToTable.class);
			return beanTable.name();
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Tran entity = new Tran();
		entity.setUid("123123");
		entity.setUname("杨红岩");
		Object value = getIdentify(entity);
		System.out.println(value);
	}
}
