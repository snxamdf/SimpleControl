package sc.yhy.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import sc.yhy.annotation.annot.Order;
import sc.yhy.listener.AnnotationListener;

@Order("" + Integer.MAX_VALUE)
class CommInitializer implements WebApplicationInitializer {
	/**
	 * 框架基本配置加载
	 */
	@Override
	public void init(ServletContext servletContext) throws ServletException {
		servletContext.addListener(AnnotationListener.class);
		// 初始化servlet
		servletContext.addServlet("annotationServlet", "sc.yhy.servlet.AnnotationServlet").addMapping("/");
		// tomcat default
		servletContext.addServlet("default", "org.apache.catalina.servlets.DefaultServlet").addMapping("*.jpg", "*.png", "*.gif", "*.ico", "*.js", "*.css", "*.html", "*.htm");
		// 其它..待添加
	}

}
