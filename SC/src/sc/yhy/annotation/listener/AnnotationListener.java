package sc.yhy.annotation.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sc.yhy.annotation.GetBeanClass;

public class AnnotationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("contextDestroyed");
		GetBeanClass.clearMappings();
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		GetBeanClass beanClass = new GetBeanClass();
		beanClass.init();
	}

}
