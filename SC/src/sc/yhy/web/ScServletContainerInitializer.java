package sc.yhy.web;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;

import sc.yhy.annotation.annot.Order;

/**
 * 初始化加载所有配置类
 * 
 * @author YHY
 *
 */
@HandlesTypes(WebApplicationInitializer.class)
public class ScServletContainerInitializer implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> webAppInitializerClasses, ServletContext servletContext) throws ServletException {
		List<WebApplicationInitializer> initializers = new LinkedList<WebApplicationInitializer>();
		if (webAppInitializerClasses != null) {
			for (Class<?> waiClass : webAppInitializerClasses) {
				if (!waiClass.isInterface() && !Modifier.isAbstract(waiClass.getModifiers()) && WebApplicationInitializer.class.isAssignableFrom(waiClass)) {
					try {
						initializers.add((WebApplicationInitializer) waiClass.newInstance());
					} catch (Throwable ex) {
						throw new ServletException("Failed to instantiate ScServletContainerInitializer class", ex);
					}
				}
			}
		}
		if (initializers.isEmpty()) {
			servletContext.log("No SC ScServletContainerInitializer types detected on classpath");
			return;
		}
		// 排序
		Collections.sort(initializers, new Comparator<WebApplicationInitializer>() {
			public int compare(WebApplicationInitializer arg0, WebApplicationInitializer arg1) {
				Order arg0order = arg0.getClass().getAnnotation(Order.class);
				Order arg1order = arg1.getClass().getAnnotation(Order.class);
				return arg0order.value().compareTo(arg1order.value());
			}
		});
		servletContext.log("SC ScServletContainerInitializer detected on classpath: " + initializers);
		for (WebApplicationInitializer initializer : initializers) {
			initializer.init(servletContext);
		}
	}

}
