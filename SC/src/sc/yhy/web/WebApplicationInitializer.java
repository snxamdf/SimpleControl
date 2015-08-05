package sc.yhy.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 应用程序初始化入口接口
 * 
 * @author YHY
 *
 */
public interface WebApplicationInitializer {
	public void init(ServletContext servletContext) throws ServletException;
}
