package com.yhy.test.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.data.Connect;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.TestBean;

public class TestDao {
	public List<Map<String, Object>> print() {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		try {
			List<Map<String, Object>> map = conn.queryToListMap("select emailId,emailName,emailAddress from users");
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
