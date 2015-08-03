package com.yhy.test.service;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Service;
import sc.yhy.annotation.annot.Transaction;
import sc.yhy.annotation.annot.Value;
import sc.yhy.data.DataBase;

import com.yhy.test.dao.TranDao;

@Service
@Transaction
public class TranService {
	@Autowired
	private TranDao tranDao;
	
	@Value("${sc.annot.init.2}")
	private String stc2;

	public void saveTest() throws Exception {
		tranDao.saveTest();
		DataBase.close();
	}
}
