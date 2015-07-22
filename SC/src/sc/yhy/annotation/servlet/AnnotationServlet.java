package sc.yhy.annotation.servlet;

import java.lang.reflect.Method;

import sc.yhy.annotation.Constant;
import sc.yhy.annotation.GetBeanClass;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.injection.FieldObjectInjection;
import sc.yhy.annotation.request.RequestBody;

public class AnnotationServlet extends BaseServlet {
	private static final long serialVersionUID = -5225486712236009455L;

	@Override
	protected void doServlet() throws Exception {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		String uri = request.getRequestURI();
		if (path != null && !"".equals(path)) {
			uri = uri.substring(path.indexOf(path) + path.length());
		}
		ClassMapping mapping = GetBeanClass.getMappings(uri);
		if (mapping != null) {
			Class<?> clazz = mapping.getClazz();
			// 获取要调用的方法
			String methodName = mapping.getMethodName();
			Method m = null;
			// 遍历方法判断要调用的方法
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				// 判断要调用的方法
				if (methodName.equals(method.getName())) {
					m = method;
					break;
				}
			}
			// 所获取的类不是空
			if (m != null) {
				FieldObjectInjection fieldObjectInjection = new FieldObjectInjection(request);
				Object newInstance = clazz.newInstance();
				// 实例类字段
				fieldObjectInjection.instanceClassField(clazz, newInstance);
				// 实例参数
				Object[] paramterObject = fieldObjectInjection.instanceClassMethodParam(m);
				// 异步注解响应
				RequestBody resBody = m.getAnnotation(RequestBody.class);
				Object resultObject = m.invoke(newInstance, paramterObject);
				if (resBody != null) {
					// 输出ajax内容
					this.printWriter.write(resultObject.toString());
				} else {
					// 执行调用方法
					if (resultObject instanceof String) {
						// 返回页面
						String result = resultObject.toString();
						if (result.indexOf(Constant.REDIRECT) != -1) {
							result = result.replaceAll(Constant.REDIRECT, "");
							this.sendRedirect(result);
						} else {
							this.dispatcherForward(result);
						}
					}
				}
			}
		} else {
			this.printWriter.write("<h1>HTTP Status 404</h1> " + basePath + uri);
		}
	}
	
	@Override
	protected void before() throws Exception {
		//System.out.println("before");
	}

	@Override
	protected void after() throws Exception {
		//System.out.println("after");
	}
}
