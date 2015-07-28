package com.yhy.test.action;

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
		System.out.println(tranService.getListMap());
		return "/tran.jsp";
	}
}
