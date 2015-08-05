package com.yhy.test.config;

import java.util.ArrayList;
import java.util.List;

import org.logicalcobwebs.proxool.configuration.ServletConfigurator;

import com.yhy.test.filter.TestFilter;
import com.yhy.test.servlet.TestServlet;

import sc.yhy.annotation.annot.Order;
import sc.yhy.annotation.bean.FilterBean;
import sc.yhy.annotation.bean.ServletBean;
import sc.yhy.web.RegistrationBean;

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
		List<ServletBean> servletBeans = new ArrayList<ServletBean>();
		ServletBean servletBean = new ServletBean();
		servletBean.setServletName("ServletConfigurator");
		servletBean.setParameter("propertyFile", "/WEB-INF/classes/proxool.properties");
		servletBean.setClazz(ServletConfigurator.class);
		servletBean.setLoadOnStartup(1);
		servletBeans.add(servletBean);

		servletBean = new ServletBean();
		servletBean.setServletName("testServlet");
		servletBean.setClazz(TestServlet.class);
		servletBean.setMapping(new String[] { "/testServlet" });
		servletBeans.add(servletBean);

		this.setServletBean(servletBeans);

		List<FilterBean> filterBeans = new ArrayList<FilterBean>();
		FilterBean filterBean = new FilterBean();
		filterBean.setFilterName("testFilter");
		filterBean.setClazz(TestFilter.class);
		filterBean.setMappings(new String[] { "/*" });
		filterBeans.add(filterBean);

		this.setFilterBean(filterBeans);

	}

}
