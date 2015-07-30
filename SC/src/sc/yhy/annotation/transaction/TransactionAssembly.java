package sc.yhy.annotation.transaction;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sc.yhy.annotation.annot.Transaction;
import sc.yhy.data.DataBase;

/**
 * 事务装配
 *
 */
public class TransactionAssembly implements MethodInterceptor {
	private final static String[] defaultStartMethod = { "save", "insert", "put", "add", "update", "modify", "delete" };
	private Enhancer enhancer = new Enhancer();
	private String[] startMethod = null;

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
	 * @param 重写拦截
	 *            ，调用父类对像方法
	 * @param 或当前对像方法
	 *            提交事务，或回滚事务
	 */
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		Object result = null;
		boolean bool = false;
		String methodName = method.getName();
		// 判断是否有自己定义的方法前缀添加事务 startMethod
		if (startMethod != null && startMethod.length > 0) {
			for (String sm : startMethod) {
				if (methodName.startsWith(sm)) {
					bool = true;
					break;
				}
			}
		} else {// 如果没有，使用默认的前缀添加事务 defaultStartMethod
			for (String dsm : defaultStartMethod) {
				if (methodName.startsWith(dsm)) {
					bool = true;
					break;
				}
			}
		}
		// 开启事务程序
		if (bool) {
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
		} else {// 不使用事务程序
			if (this.obj != null) {
				result = proxy.invoke(this.obj, args);
			} else {
				result = proxy.invokeSuper(obj, args);
			}
		}
		return result;
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
	public Object bindTransaction(Object obj, Transaction transaction) {
		if (transaction != null) {
			this.startMethod = transaction.startMethod();
		}
		// 生成对像并重新赋值
		Object object = this.getInstrumentedClass(obj);
		return object;
	}
}
