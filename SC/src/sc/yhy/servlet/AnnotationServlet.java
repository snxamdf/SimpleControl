package sc.yhy.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.yhy.annotation.Constant;
import sc.yhy.annotation.GetBeanClass;
import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.injection.FieldObjectInjection;
import sc.yhy.annotation.request.ResponseBody;
import sc.yhy.data.DataBase;

/**
 * servlet请求解析注解 装配对像，装配请求参数
 * 
 * @author YHY
 *
 */
public class AnnotationServlet extends BaseServlet {
	private static final long serialVersionUID = -5225486712236009455L;

	@Override
	protected void doServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

		String uri = request.getRequestURI();
		if (path != null && !"".equals(path)) {
			uri = uri.substring(path.indexOf(path) + path.length());
		}
		ClassMapping mapping = GetBeanClass.getMappings(uri);
		FieldObjectInjection fieldObjectInjection = null;
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
				fieldObjectInjection = new FieldObjectInjection(request);
				Object newInstance = clazz.newInstance();
				// 实例类字段
				fieldObjectInjection.instanceClassField(clazz, newInstance);
				// 实例参数
				Object[] paramterObject = fieldObjectInjection.instanceClassMethodParam(m);
				// 调用执行方法
				Object resultObject = m.invoke(newInstance, paramterObject);

				// 释放资源
				this.release(fieldObjectInjection);
				// 异步注解响应
				if (m.isAnnotationPresent(ResponseBody.class)) {
					// 输出响应文本内容
					response.getWriter().write(resultObject.toString());
				} else {
					if (resultObject instanceof String) {
						// 判断返回请求页面
						String result = resultObject.toString();
						if (result.indexOf(Constant.REDIRECT) != -1) {
							result = result.replaceAll(Constant.REDIRECT, "");
							sendRedirect(response, result);
						} else {
							dispatcherForward(request, response, result);
						}
					}
				}
			}
		} else {
			response.getWriter().write("<h1>HTTP Status 404</h1> " + basePath + uri);
		}
	}

	private void release(FieldObjectInjection fieldObjectInjection) {
		if (fieldObjectInjection != null) {
			fieldObjectInjection.deleteMultipartFile();
		}
		DataBase.closeConnection();

	}

	@Override
	protected void before(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println("before");
	}

	@Override
	protected void after(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// System.out.println("after");
	}
}
