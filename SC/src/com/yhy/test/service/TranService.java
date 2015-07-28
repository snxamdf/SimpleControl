package com.yhy.test.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.Transaction;

import com.yhy.test.dao.TranDao;

@Transaction
public class TranService {
	@Autowired
	private TranDao tranDao;

	public List<Map<String, Object>> getListMap() throws SQLException {
		return tranDao.getListMap();
	}

	@Transaction
	public void saveTran() throws Exception {
		System.out.println("saveTran  ");
		throw new Exception("测试异常");
	}

	public void updateTran() {
		System.out.println("updateTran  ");
	}
}
