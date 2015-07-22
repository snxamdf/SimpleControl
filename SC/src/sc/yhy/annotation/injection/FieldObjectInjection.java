package sc.yhy.annotation.injection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.Constant;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.annotation.util.Util;

public class FieldObjectInjection {

	private HttpServletRequest request;

	public FieldObjectInjection(HttpServletRequest request) {
		this.request = request;
	}

	// 创建类字段对像或赋值
	public void instanceClassField(Class<?> clazz, Object newInstance) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		this.newClassField(clazz, newInstance);
	}

	// 创建类字段对像或赋值
	private void newClassField(Class<?> clazz, Object newInstance) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			Autowired autowired = field.getAnnotation(Autowired.class);
			// 判断是不是java lang类型
			if (!Util.isFieldType(type.getName()) && autowired != null) {
				Object typeObject = type.newInstance();
				field.set(newInstance, typeObject);
				newClassField(type, typeObject);
			} else {
				RequestParam requestParam = field.getAnnotation(RequestParam.class);
				if (requestParam != null) {
					String fieldName = null;
					// 判断是不是java lang类型
					if (Util.isFieldType(type.getName())) {
						fieldName = requestParam.value();
						fieldName = "".equals(fieldName) ? field.getName() : fieldName;
						field.set(newInstance, Util.conversion(type.getName(), this.getRequestParamValue(fieldName)));
					} else {
						fieldName = requestParam.value();
						fieldName = "".equals(fieldName) ? field.getName() : fieldName;
						Object typeObject = type.newInstance();
						this.objectClassField(type, typeObject, fieldName);
						field.set(newInstance, typeObject);
					}
				}
			}
		}
	}

	// 创建对像或赋值
	private void objectClassField(Class<?> clazz, Object newInstance, String fieldName) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			if (Util.isFieldType(type.getName())) {
				String values = fieldName + Constant.POINT + field.getName();
				values = this.getRequestParamValue(values);
				field.set(newInstance, Util.conversion(type.getName(), values));
			} else if (Util.isList(type.getName())) {// 判断字段是否为集合
				Type tp = field.getGenericType();
				if (tp == null)
					continue;
				if (tp instanceof ParameterizedType) {// 判断是否为泛型
					if (clazz.isPrimitive()) {
						System.out.println(tp + "  isPrimitive");
					}
					ParameterizedType pt = (ParameterizedType) tp;
					Class<?> genericClazz = (Class<?>) pt.getActualTypeArguments()[0];
					String fns;
					// 不是java lang
					if (!Util.isFieldType(genericClazz.getName())) {
						fns = fieldName + Constant.POINT + field.getName();
						// 创建list对像
						List<Object> typeObject = new ArrayList<Object>();
						this.listField(genericClazz, fns, typeObject);
						field.set(newInstance, typeObject);
					} else if (Util.isFieldType(genericClazz.getName())) {
						// 是java lang
						fns = fieldName + Constant.POINT + field.getName();
						String[] values = this.getRequestParamValues(fns);
						if (values != null) {
							// 创建list对像
							List<Object> listObject = new ArrayList<Object>();
							for (String value : values) {
								listObject.add(value);
							}
							field.set(newInstance, listObject);
						}
					}
				}
			} else {
				Object typeObject = type.newInstance();
				objectClassField(type, typeObject, fieldName + Constant.POINT + field.getName());
				field.set(newInstance, typeObject);
			}
		}
	}

	private void listField(Class<?> clazz, String fieldName, List<Object> listObject) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		Object[] object = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			if (Util.isFieldType(type.getName())) {
				String key = fieldName + Constant.POINT + field.getName();
				String[] values = this.getRequestParamValues(key);
				if (values != null) {
					if (object == null) {
						object = new Object[values.length];
						for (int i = 0; i < values.length; i++) {
							object[i] = clazz.newInstance();
						}
					}
					for (int i = 0; i < values.length; i++) {
						field.set(object[i], Util.conversion(type.getName(), values[i]));
					}
				}
			} else {
				Object typeObject = type.newInstance();
				objectClassField(type, listObject, fieldName + Constant.POINT + field.getName());
				field.set(clazz.newInstance(), typeObject);
			}

		}
		if (object != null) {
			for (Object obj : object) {
				listObject.add(obj);
			}
		}
	}

	// 创建action方法参数对像或赋值
	public Object[] instanceClassMethodParam(Method m) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 获取参数注解
		Annotation[][] an = m.getParameterAnnotations();
		// 获取方法参数
		Class<?>[] parameterTypes = m.getParameterTypes();
		Object[] paramterObject = new Object[parameterTypes.length];
		String paramPack, fieldName;
		RequestParam rp = null;
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> cls = parameterTypes[i];
			paramPack = cls.getName();
			// 判断是不是java lang类型
			if (Util.isFieldType(paramPack, Constant.STRING)) {
				rp = (RequestParam) an[i][0];
				paramterObject[i] = this.getRequestParamValue(rp.value());
			} else if (Util.isFieldType(paramPack, Constant.HTTPSERVLETREQUEST)) {
				paramterObject[i] = request;
			} else {
				rp = (RequestParam) an[i][0];
				fieldName = rp.value();
				Object typeObject = cls.newInstance();
				this.objectClassField(cls, typeObject, fieldName);
				paramterObject[i] = typeObject;
			}
		}
		return paramterObject;
	}

	protected String getRequestParamValue(String key) {
		return this.request.getParameter(key);
	}

	protected String[] getRequestParamValues(String key) {
		return this.request.getParameterValues(key);
	}
}
