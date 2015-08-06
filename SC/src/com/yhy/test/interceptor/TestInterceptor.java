package com.yhy.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sc.yhy.annotation.annot.Interceptor;
import sc.yhy.annotation.annot.Order;
import sc.yhy.servlet.interceptor.HandlerInterceptor;

@Order("1")
@Interceptor
public class TestInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("TestInterceptor preHandler");
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("TestInterceptor afterCompletion");
	}

}
