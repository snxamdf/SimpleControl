package sc.yhy.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import sc.yhy.annotation.annot.Order;
import sc.yhy.listener.AnnotationListener;

@Order("" + Integer.MAX_VALUE)
class CommInitializer implements WebApplicationInitializer {
	String[] mapping = { "*.jpg", "*.jpeg", "*.swf", "*.png", "*.gif", "*.ico", "*.js", "*.css", "*.xml", "*.shtml", "*.html", "*.htm" };

	/**
	 * 框架基本配置加载
	 */
	@Override
	public void init(ServletContext servletContext) throws ServletException {
		String serverInfo = servletContext.getServerInfo();
		servletContext.log("java版本---" + System.getProperty("java.version") + "---运行 WEB 容器---" + serverInfo);
		// 添加主监听
		servletContext.addListener(AnnotationListener.class);
		// 添加主servlet
		servletContext.addServlet("annotationServlet", "sc.yhy.servlet.AnnotationServlet").addMapping("/");
		if (serverInfo.toLowerCase().indexOf("tomcat") != -1) {
			// 配置静态文件默认tomcat default
			servletContext.addServlet("default", "org.apache.catalina.servlets.DefaultServlet").addMapping(mapping);
		} else if (serverInfo.toLowerCase().indexOf("weblogic") != -1) {
		} else if (serverInfo.toLowerCase().indexOf("jboss") != -1) {
		} else if (serverInfo.toLowerCase().indexOf("websphere") != -1) {
		} else if (serverInfo.toLowerCase().indexOf("resin") != -1) {
		}
		// 其它..待添加
	}

}
