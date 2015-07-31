package com.yhy.test.service;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Service;
import sc.yhy.annotation.annot.Transaction;

import com.yhy.test.dao.TranDao;

@Service
@Transaction
public class TranService {
	@Autowired
	private TranDao tranDao;

	public void saveTest() throws Exception {
		tranDao.saveTest();
	}
}
