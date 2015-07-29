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

	public void saveTran() throws Exception {
		TestBean testBean = new TestBean();
		testBean.setEmailId("emid111111");
		testBean.setEmailName("emname 张三");
		testBean.setEmailAddress("emadd 地址");
		int r = tranDao.saveTran(testBean);
		System.out.println(r);
		throw new Exception("测试异常");
	}

	public void updateTran() {
		System.out.println("updateTran  ");
	}
}
