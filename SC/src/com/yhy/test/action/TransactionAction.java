package com.yhy.test.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;

import com.yhy.test.service.TranService;

@Action
@RequestMapping(value = "/tran")
public class TransactionAction {
	@Autowired
	private TranService tranService;

	@RequestMapping(value = "/test.action")
	public String testTran() {
		try {
			List<Map<String, Object>> listmap=tranService.getListMap();
			System.out.println(listmap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			tranService.saveTran();
		} catch (Exception e) {
			e.printStackTrace();
		}
		tranService.updateTran();

		return "/tran.jsp";
	}
}
