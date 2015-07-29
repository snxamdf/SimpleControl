package sc.yhy.annotation.transaction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.yhy.annotation.Transaction;
import sc.yhy.data.DataBase;

/**
 * 事务装配
 *
 */
public class TransactionAssembly implements MethodInterceptor {
	private Enhancer enhancer = new Enhancer();

	/**
	 * 创建代理实例
	 * 
	 * @param clz
	 * @return
	 */
	public Object getInstrumentedClass(Class<?> clz) {
		enhancer.setSuperclass(clz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	private Object obj;

	/**
	 * 创建代理实例
	 * 
	 * @param clz
	 * @return
	 */
	public Object getInstrumentedClass(Object obj) {
		this.obj = obj;
		enhancer.setSuperclass(obj.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	/**
	 * @param 重写拦截，调用父类对像方法
	 * @param 或当前对像方法 提交事务，或回滚事务
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		try {
			if (this.obj != null) {
				result = proxy.invoke(this.obj, args);
			} else {
				result = proxy.invokeSuper(obj, args);
			}
			DataBase.commit();
		} catch (Exception e) {
			DataBase.rollback();
			throw e;
		}
		return result;
	}

	/**
	 * 检查方法事务注解
	 * 
	 * @param field
	 * @param newInstance
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void bindMethod(Field field, Object newInstance) throws IllegalArgumentException, IllegalAccessException {
		Transaction transaction = field.getAnnotation(Transaction.class);
		String[] startMethod = transaction.startMethod();
		Class<?> clazz = field.getType();
		Method[] methods = clazz.getMethods();
		String methodName;
		boolean isStart = false;
		for (Method method : methods) {
			methodName = method.getName();
			// 判断该方法上是否有事务
			if (method.isAnnotationPresent(Transaction.class)) {
				isStart = true;
			} else {
				// 判断是否自定义方法头添加事务
				if (startMethod.length > 0) {
					for (String sm : startMethod) {
						if (methodName.startsWith(sm)) {
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
				// 生成对像并重新赋值
				// Object object = this.getInstrumentedClass(clazz);
				// field.set(newInstance, object);
				// System.out.println(object);
			}

			isStart = false;
		}
	}

	/**
	 * 绑定创建class代理实例
	 * 
	 * @param clazz
	 * @return
	 */
	public Object bindTransaction(Class<?> clazz) {
		// 生成对像并重新赋值
		Object object = this.getInstrumentedClass(clazz);
		return object;
	}

	/**
	 * 绑定创建object代理实例
	 * 
	 * @param clazz
	 * @return
	 */
	public Object bindTransaction(Object obj) {
		// 生成对像并重新赋值
		Object object = this.getInstrumentedClass(obj);
		return object;
	}
}
