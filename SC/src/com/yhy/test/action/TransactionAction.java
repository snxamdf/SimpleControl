package com.yhy.test.action;

import sc.yhy.annotation.annot.Autowired;
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
			tranService.test();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/tran.jsp";
	}
}
