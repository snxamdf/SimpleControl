package sc.yhy.annotation.transaction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.Transaction;

public class TransactionReflection implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	public Object getInstrumentedClass(Class<?> clz) {
		enhancer.setSuperclass(clz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		try {
			System.out.println("method.getName()=" + method.getName());
			result = proxy.invokeSuper(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 初始化事务
	 * 
	 * @设置 为service层
	 * @注解 Transaction
	 * 
	 * @param clazz
	 *            action类
	 * @param newInstance
	 *            action实例
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public void findTransClassObject(Class<?> clazz, Object newInstance) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Class<?> type = field.getType();
			// 判断是否为自动注入实例
			if (field.isAnnotationPresent(Autowired.class)) {
				// 判断该实例是否开启事务
				if (type.isAnnotationPresent(Transaction.class)) {
					Transaction transaction = type.getAnnotation(Transaction.class);
					this.bindMethod(field, type, transaction, newInstance);
				}
			}
		}
	}

	public void bindMethod(Field field, Class<?> clazz, Transaction transaction, Object newInstance) throws IllegalArgumentException, IllegalAccessException {
		String[] startMethod = transaction.startMethod();
		Method[] methods = clazz.getMethods();
		String methodName;
		boolean isStart = false;
		for (Method method : methods) {
			methodName = method.getName();
			// 判断该方法上是否有事务
			if (method.isAnnotationPresent(Transaction.class)) {
				isStart = true;
			} else {
				// 判断是否自定义方法添加事务
				if (startMethod.length > 0) {
					for (String sm : startMethod) {
						if (method.getName().startsWith(sm)) {
							isStart = true;
							break;
						}
					}
				} else if (methodName.startsWith("insert") || methodName.startsWith("save") || methodName.startsWith("update")) {
					isStart = true;
				}
			}
			// 当前方法可以做事务处理
			if (isStart) {
				// 生成对像并重新赋值 ，改到FieldObjectInjection
				// 待完成 生成dao字段等对像，改到FieldObjectInjection
				Object object = this.getInstrumentedClass(clazz);
				field.set(newInstance, object);
				System.out.println(object);
			}

			isStart = false;
		}
	}
}
