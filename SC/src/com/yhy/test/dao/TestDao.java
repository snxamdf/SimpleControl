package com.yhy.test.dao;

import java.sql.SQLException;
import java.util.Map;

import sc.yhy.data.Connect;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.TestBean;
import com.yhy.test.entity.TestBeanSon;

public class TestDao {
	public Map<String, Object> print() {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		try {
			TestBean bean = new TestBean();
			bean.setEmailId("123123");
			bean.setEmailName("name 杨杨");
			bean.setEmailAddress("住 址 址址");

			TestBeanSon beanSon = new TestBeanSon();
			beanSon.setTestid("idid");
			beanSon.setTestName("adssadfasdf");
			bean.setTestBeanSon(beanSon);

			conn.insertToClass(bean);
			Map<String, Object> map = conn.getOneRow("select * from tab_name");
			conn.commit();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		conn.close();
		return null;
	}
}
