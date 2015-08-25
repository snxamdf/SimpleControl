package sc.yhy.web;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import sc.yhy.annotation.annot.Order;
import sc.yhy.annotation.bean.FilterBean;
import sc.yhy.annotation.bean.ServletBean;

/**
 * web.xml需要继承的类
 * 
 * @author YHY
 *
 */
@Order("9999")
public abstract class RegistrationBean implements WebApplicationInitializer {
	private List<ServletBean> servletBean = new ArrayList<ServletBean>();
	private List<FilterBean> filterBean = new ArrayList<FilterBean>();

	@Override
	public final void init(ServletContext servletContext) throws ServletException {
		servletContext.log("RegistrationBean init this.init() to servletBean and filterBean");
		this.init();
		if (servletBean != null && servletBean.size() > 0) {
			for (ServletBean bean : servletBean) {
				ServletRegistration.Dynamic sr = null;
				if (bean.getClazz() != null) {
					sr = servletContext.addServlet(bean.getServletName(), bean.getClazz());
				} else if (bean.getClassPath() != null) {
					sr = servletContext.addServlet(bean.getServletName(), bean.getClassPath());
				}
				if (bean.getParameter() != null) {
					sr.setInitParameters(bean.getParameter());
				}
				if (bean.getMapping() != null) {
					sr.addMapping(bean.getMapping());
				}
				if (bean.getLoadOnStartup() != -1) {
					sr.setLoadOnStartup(bean.getLoadOnStartup());
				}
			}
		}
		if (filterBean != null && filterBean.size() > 0) {
			for (FilterBean bean : filterBean) {
				FilterRegistration.Dynamic fr = null;
				if (bean.getClazz() != null) {
					fr = servletContext.addFilter(bean.getFilterName(), bean.getClazz());
				} else if (bean.getClassPath() != null) {
					fr = servletContext.addFilter(bean.getFilterName(), bean.getClassPath());
				}
				if (bean.getParameter() != null) {
					fr.setInitParameters(bean.getParameter());
				}
				if (bean.getMappings() != null) {
					fr.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, bean.getMappings());
					// fr.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST),
					// true, bean.getMappings());
				}
				fr.setAsyncSupported(true);
			}
		}
		servletContext.log("RegistrationBean end this.init() to servletBean and filterBean");
		servletBean.clear();
		filterBean.clear();
		servletBean = null;
		filterBean = null;
	}

	public final void addServlet(ServletBean servletBean) {
		this.servletBean.add(servletBean);
	}

	public final void addFilter(FilterBean filterBean) {
		this.filterBean.add(filterBean);
	}

	public abstract void init();
}
