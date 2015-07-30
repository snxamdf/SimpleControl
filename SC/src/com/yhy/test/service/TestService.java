package com.yhy.test.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.annotation.annot.Autowired;

import com.yhy.test.dao.TestDao;

public class TestService {
	@Autowired
	private TestDao testDao;

	public List<Map<String, Object>> getStr() throws SQLException {
		return testDao.print();
	}
}
