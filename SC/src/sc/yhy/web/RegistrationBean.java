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
import sc.yhy.annotation.bean.ListenerBean;
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
		// this.init();

		List<ListenerBean> listenerBeans = new ArrayList<ListenerBean>();
		this.addListener(listenerBeans);
		List<ServletBean> servletBeans = new ArrayList<ServletBean>();
		this.addServlet(servletBeans);
		List<FilterBean> filterBeans = new ArrayList<FilterBean>();
		this.addFilter(filterBeans);
		if (listenerBeans != null && listenerBeans.size() > 0) {
			for (ListenerBean bean : listenerBeans) {
				if (bean.getClazz() != null) {
					servletContext.addListener(bean.getClazz());
				} else if (bean.getClassPath() != null) {
					servletContext.addListener(bean.getClassPath());
				}
			}
		}
		if (servletBeans != null && servletBeans.size() > 0) {
			for (ServletBean bean : servletBeans) {
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
		if (filterBeans != null && filterBeans.size() > 0) {
			for (FilterBean bean : filterBeans) {
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

	public abstract void init();

	public final void addServlet(ServletBean servletBean) {
		this.servletBean.add(servletBean);
	}

	public final void addFilter(FilterBean filterBean) {
		this.filterBean.add(filterBean);
	}

	/**
	 * 配置servlet
	 * 
	 * @param servletBeans
	 */
	public abstract void addServlet(List<ServletBean> servletBeans);

	/**
	 * 配置过滤器
	 * 
	 * @param filterBeans
	 */
	public abstract void addFilter(List<FilterBean> filterBeans);

	/**
	 * 配置监听
	 * 
	 * @param listenerBeans
	 */
	public abstract void addListener(List<ListenerBean> listenerBeans);
}
