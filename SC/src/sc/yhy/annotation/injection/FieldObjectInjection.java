package sc.yhy.annotation.injection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.Constant;
import sc.yhy.annotation.Transaction;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.annotation.transaction.TransactionAssembly;
import sc.yhy.fileupload.MultipartFile;
import sc.yhy.servlet.HttpRequest;
import sc.yhy.util.Util;

/**
 * 反射类
 * 
 * @author YHY
 *
 */
public class FieldObjectInjection {
	private MultipartFile multipartFile;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpRequest httpRequest;
	private TransactionAssembly transactionAssembly;

	public FieldObjectInjection(TransactionAssembly transactionAssembly, HttpRequest httpRequest, MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
		this.transactionAssembly = transactionAssembly;
		this.httpRequest = httpRequest;
		this.multipartFile = multipartFile;
		this.request = request;
		this.response = response;
	}

	/**
	 * 创建类字段对像或赋值
	 * 
	 * @param clazz
	 * @param newInstance
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void instanceClassField(Class<?> clazz, Object newInstance) throws IllegalArgumentException, IllegalAccessException, InstantiationException, IOException, ServletException {
		this.newClassField(clazz, newInstance);
	}

	/**
	 * 创建类字段对像或赋值
	 * 
	 * @param clazz
	 * @param newInstance
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void newClassField(Class<?> clazz, Object newInstance) throws IllegalArgumentException, IllegalAccessException, InstantiationException, IOException, ServletException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			// 判断是否为Autowired注解,自动注入
			if (field.isAnnotationPresent(Autowired.class)) {
				Object thisNewInstance = null;
				Object fieldObject=type.newInstance();
				// 判断该字段是否开启事务
				if (type.isAnnotationPresent(Transaction.class)) {
					// 生成代理对像并返回实例
					thisNewInstance = transactionAssembly.bindTransaction(fieldObject);
				} else {
					// 生成实例
					thisNewInstance = fieldObject;
				}
				// 设置字段
				field.set(newInstance, thisNewInstance);
				// 递归调用
				newClassField(type, fieldObject);
			} else {
				boolean bool = field.isAnnotationPresent(RequestParam.class);
				if (bool) {
					RequestParam requestParam = field.getAnnotation(RequestParam.class);
					String fieldName = null;
					// 判断是不是java lang类型
					if (Util.isFieldType(type.getName())) {
						fieldName = requestParam.value();
						fieldName = "".equals(fieldName) ? field.getName() : fieldName;
						field.set(newInstance, Util.conversion(type.getName(), this.getRequestParamValue(fieldName)));
					} else if (Util.isFile(type.getName())) {
						if (multipartFile != null) {
							field.set(newInstance, multipartFile);
						} else {
							field.set(newInstance, new MultipartFile());
						}
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

	/**
	 * 创建对像或赋值
	 * 
	 * @param clazz
	 * @param newInstance
	 * @param fieldName
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
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

	/**
	 * 封装list对像泛型数据
	 * 
	 * @param clazz
	 * @param fieldName
	 * @param listObject
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
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
				this.objectClassField(type, listObject, fieldName + Constant.POINT + field.getName());
				field.set(clazz.newInstance(), typeObject);
			}

		}
		if (object != null) {
			for (Object obj : object) {
				listObject.add(obj);
			}
		}
	}

	/**
	 * 创建action方法参数对像或赋值
	 * 
	 * @param m
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
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
			} else if (Util.isFieldType(paramPack, Constant.HTTP_SERVLET_REQUEST)) {
				paramterObject[i] = request;
			} else if (Util.isFieldType(paramPack, Constant.HTTP_SERVLET_RESPONSE)) {
				paramterObject[i] = response;
			} else if (Util.isFieldType(paramPack, Constant.HTTP_SESSION)) {
				paramterObject[i] = request.getSession();
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

	/**
	 * 获取请求参数
	 * 
	 * @param key
	 * @return
	 */
	protected String getRequestParamValue(String key) {
		return httpRequest.getParamter(key);
	}

	/**
	 * 获取相同请求参数集合
	 * 
	 * @param key
	 * @return
	 */
	protected String[] getRequestParamValues(String key) {
		return httpRequest.getParamters(key);
	}
}
