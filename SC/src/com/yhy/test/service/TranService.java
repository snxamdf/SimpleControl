package com.yhy.test.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.Transaction;

import com.yhy.test.dao.TranDao;
import com.yhy.test.entity.TestBean;

@Transaction
public class TranService {
	@Autowired
	private TranDao tranDao;

	public List<Map<String, Object>> getListMap() throws SQLException {
		return tranDao.getListMap();
	}

	public int saveTran(TestBean testBean) throws Exception {
		int r = tranDao.saveTran(testBean);
		System.out.println(r);
		return r;
	}

	public void updateTran() {
		System.out.println("updateTran  ");
	}
}
