package com.yhy.test.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.annotation.annot.Dao;
import sc.yhy.data.Connect;
import sc.yhy.data.MySqlConnection;

import com.yhy.test.entity.TestBean;
@Dao
public class TestDao {
	public List<Map<String, Object>> print() throws SQLException {
		Connect<TestBean> conn = new MySqlConnection<TestBean>();
		try {
			String sql = "select emailId,emailName,emailAddress from users";
			List<Map<String, Object>> map = conn.queryToListMap(sql);
			List<TestBean> listBean = conn.queryToBeans(sql, null, TestBean.class);
			System.out.println(listBean);
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
		return null;
	}
}
