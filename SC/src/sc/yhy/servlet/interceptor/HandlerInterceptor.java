package sc.yhy.servlet.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerInterceptor {
	boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception;

	//void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception;

	void afterCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
