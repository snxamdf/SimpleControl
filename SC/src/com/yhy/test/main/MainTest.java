package com.yhy.test.main;

import java.lang.reflect.Array;

import net.sf.cglib.beans.BeanMap;

public class MainTest {
	// 初始化
	static BeanMap beanMap = null;

	public static void main(String[] args) throws Exception {
		int[] a = { 1, 2 };
		System.out.println(Array.get(a, 0));
		
	}
}
