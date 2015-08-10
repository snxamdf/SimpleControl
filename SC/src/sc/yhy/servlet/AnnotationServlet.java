package sc.yhy.servlet;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sc.yhy.annotation.bean.ClassMapping;
import sc.yhy.annotation.injection.FieldObjectInjection;
import sc.yhy.annotation.request.ResponseBody;
import sc.yhy.annotation.transaction.TransactionAssembly;
import sc.yhy.core.Entrance;
import sc.yhy.data.sql.DataBase;
import sc.yhy.fileupload.MultipartFile;
import sc.yhy.fileupload.MultipartFileInjection;
import sc.yhy.servlet.interceptor.HandlerInterceptor;
import sc.yhy.util.Constant;

/**
 * servlet请求解析注解 装配对像，装配请求参数
 * 
 * @author YHY
 *
 */
public class AnnotationServlet extends BaseServlet {
	private static final long serialVersionUID = -5225486712236009455L;
	private ConcurrentHashMap<String, ClassMapping> actionMappingsMap;
	private List<HandlerInterceptor> interceptors;

	public AnnotationServlet() {
		actionMappingsMap = Entrance.getActionMappingsMap();
		interceptors = Entrance.getInterceptor();
	}

	@Override
	protected void doServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		// 获取请求路径
		String uri = request.getRequestURI();
		if (path != null && !"".equals(path)) {
			uri = uri.substring(path.indexOf(path) + path.length());
		}

		// 获取action路径
		ClassMapping mapping = actionMappingsMap.get(uri);

		// 判断action获取是否为空
		if (mapping != null) {
			Class<?> clazz = mapping.getClazz();
			Method m = mapping.getMethod();

			// 遍历方法判断要调用的方法
			if (m != null) {
				Object resultObject = this.doDispatch(request, response, clazz, m);
				// 释放资源
				this.releaseResources();
				if (resultObject == null) {
					throw new Exception();
				}
				// 异步注解响应
				if (m.isAnnotationPresent(ResponseBody.class)) {
					PrintWriter printWriter = response.getWriter();
					printWriter.write(resultObject.toString());
					printWriter.close();
					printWriter.flush();
				} else {
					// 判断是否返回字符串 url 页面
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
			// 错误没有访问的action
			PrintWriter printWriter = response.getWriter();
			printWriter.write("<h1>HTTP Status 404</h1> " + basePath + uri);
			printWriter.close();
			printWriter.flush();
		}
	}

	Object doDispatch(HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Method m) throws Exception {
		for (HandlerInterceptor interceptor : interceptors) {
			try {
				if (!interceptor.preHandle(request, response)) {
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 调用执行方法
		Object resultObject = this.initialize(request, response, clazz, m);

		for (HandlerInterceptor interceptor : interceptors) {
			try {
				interceptor.afterCompletion(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return resultObject;
	}

	private Object initialize(HttpServletRequest request, HttpServletResponse response, Class<?> clazz, Method m) throws Exception {
		HttpRequest httpRequest = new HttpRequest();
		MultipartFile multipartFile = null;
		String contentType = request.getContentType();
		// 判断是否为二进制提交表单
		if (ServletFileUpload.isMultipartContent(request) && contentType.startsWith(Constant.MULTIPART_DATA)) {
			MultipartFileInjection mf = new MultipartFileInjection();
			multipartFile = mf.process();
			multipartFile.setHttpRequest(httpRequest);
			// 设置上传文件监听
			// multipartFile.setProgressListener(request.getSession());
			// 解析二进制流，分离普通表单数据和文件数据
			List<FileItem> fileItem = multipartFile.parseRequest(request);
			// 设置有效的文件数据
			multipartFile.setFileItem(fileItem);
			// 获取并设置请求参数
			httpRequest.setParamter(fileItem);
		} else {
			// 获取并设置请求参数
			httpRequest.setParamter(request);
		}
		// 创建事务装配器
		TransactionAssembly transactionAssembly = new TransactionAssembly();
		// 创建反射字段对像
		FieldObjectInjection fieldObjectInjection = new FieldObjectInjection(transactionAssembly, httpRequest, multipartFile, request, response);
		Object newInstance = clazz.newInstance();
		// 实例局部字段
		fieldObjectInjection.instanceClassField(clazz, newInstance);
		// 实例设置封装参数
		Object[] paramterObject = fieldObjectInjection.instanceClassMethodParam(m);
		// 调用执行方法
		Object resultObject = m.invoke(newInstance, paramterObject);
		return resultObject;
	}

	/**
	 * 释放资源
	 * 
	 */
	private void releaseResources() {
		DataBase.close();
		//MongoDB.close();
	}
}
