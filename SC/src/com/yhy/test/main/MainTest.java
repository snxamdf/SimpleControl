package com.yhy.test.main;

import net.sf.cglib.beans.BeanMap;

import com.yhy.test.entity.TestBean;
import com.yhy.test.entity.Tran;

public class MainTest {
	// 初始化
	static BeanMap beanMap = null;

	public static void main(String[] args) throws Exception {
		long start = 0l, end = 0l;
		start = System.currentTimeMillis();
		beanMap = BeanMap.create(new Tran());
		// 构造
		Tran entity = new Tran();
		entity.setUid("123123");
		entity.setUname("杨红岩");
		// 赋值
		beanMap.setBean(entity);
		// 验证
		System.out.println("uid=" + beanMap.get("uid"));
		end = System.currentTimeMillis();
		System.out.println(end - start);

		start = System.currentTimeMillis();
		TestBean testBean = new TestBean();
		testBean.setEmailId("emid");
		beanMap = BeanMap.create(new TestBean());
		beanMap.setBean(testBean);
		// 验证
		System.out.println("emailId" + beanMap.get("emailId"));
		end = System.currentTimeMillis();
		System.out.println(end - start);

		start = System.currentTimeMillis();
		beanMap = BeanMap.create(new Tran());
		// 构造
		entity = new Tran();
		entity.setUid("333");
		entity.setUname("杨2123岩");
		// 赋值
		beanMap.setBean(entity);
		// 验证
		System.out.println("uid=" + beanMap.get("uid"));
		end = System.currentTimeMillis();
		System.out.println(end - start);
		
		//----------------------------
		//BulkBean.create(target, getters, setters, types);

	}

	public static void pring() {

	}
}
