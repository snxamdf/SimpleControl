package sc.yhy.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import sc.yhy.core.Entrance;

/**
 * 容器启动监听
 * 
 * @author YHY
 *
 */
public class AnnotationListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("contextDestroyed");
		Entrance.clearMappings();
	}

	// 有加载顺序
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Entrance entrance=new Entrance();
		// 加载配置文件
		entrance.getProperties();
		// 加载class beans
		entrance.getBeanClass();;
	}

}
