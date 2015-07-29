package com.yhy.test.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.data.Connect;
import sc.yhy.data.DataBase;

import com.yhy.test.entity.TestBean;

public class TranDao {
	public List<Map<String, Object>> getListMap() throws SQLException {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		List<Map<String, Object>> map = conn.queryToListMap("select emailId,emailName,emailAddress from users");
		return map;
	}

	public int saveTran(TestBean testBean) throws Exception {
		Connect<TestBean> conn = DataBase.getMySqlConnection();
		return conn.insertToClass(testBean);
	}
}
