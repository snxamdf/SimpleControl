package com.yhy.test.config;

import org.logicalcobwebs.proxool.admin.servlet.AdminServlet;
import org.logicalcobwebs.proxool.configuration.ServletConfigurator;

import sc.yhy.annotation.annot.Order;
import sc.yhy.annotation.bean.FilterBean;
import sc.yhy.annotation.bean.ServletBean;
import sc.yhy.web.RegistrationBean;

import com.yhy.test.filter.TestFilter;
import com.yhy.test.servlet.TestServlet;

/**
 * @p 该类为配置web.xml纯编码模式示例 添加测试servlet
 * @p 配置web.xml需要继承 RegistrationBean类
 * @p 需要添加order 启动顺序注解
 * @author hadoop
 *
 */
@Order("2")
public class WebXmlConfig extends RegistrationBean {
	/**
	 * @P 该方法内部编写 servlet配置和filter配置
	 * @P 以下代码为示例
	 */
	@Override
	public void init() {
		// 数据库连接池配置
		ServletBean servletBean = new ServletBean();
		servletBean.setServletName("ServletConfigurator");
		servletBean.setParameter("propertyFile", "/WEB-INF/classes/proxool.properties");
		servletBean.setClazz(ServletConfigurator.class);
		servletBean.setLoadOnStartup(1);
		// 添加到servlet配置
		super.addServlet(servletBean);

		// 测试servlet1
		servletBean = new ServletBean();
		servletBean.setServletName("Admin");
		servletBean.setClazz(AdminServlet.class);
		servletBean.setMapping(new String[] { "/admin" });
		// 添加到servlet配置
		super.addServlet(servletBean);

		// 测试servlet2
		servletBean = new ServletBean();
		servletBean.setServletName("testServlet");
		servletBean.setClazz(TestServlet.class);
		servletBean.setMapping(new String[] { "/testServlet" });
		// 添加到servlet配置
		super.addServlet(servletBean);

		FilterBean filterBean = new FilterBean();
		filterBean.setFilterName("testFilter");
		filterBean.setClazz(TestFilter.class);
		filterBean.setMappings(new String[] { "/*" });
		// 添加到fileter配置
		super.addFilter(filterBean);

	}

}
