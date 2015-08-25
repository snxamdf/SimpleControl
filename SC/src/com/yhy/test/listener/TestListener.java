package com.yhy.test.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TestListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println(arg0);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println(arg0);
	}

}
