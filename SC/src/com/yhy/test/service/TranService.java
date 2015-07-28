package com.yhy.test.service;

import java.util.List;
import java.util.Map;

import sc.yhy.annotation.Autowired;

import com.yhy.test.dao.TranDao;

public class TranService {
	@Autowired
	private TranDao tranDao;

	public List<Map<String, Object>> getListMap() {
		return tranDao.getListMap();
	}
}
