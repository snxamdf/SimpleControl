package com.yhy.test.service;

import java.util.List;
import java.util.Map;

import sc.yhy.annotation.Autowired;

import com.yhy.test.dao.TestDao;

public class TestService {
	@Autowired
	private TestDao testDao;

	public List<Map<String, Object>> getStr() {
		return testDao.print();
	}
}
