package com.yhy.test.action;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Value;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;

import com.yhy.test.service.TestService;
import com.yhy.test.service.TranService;

@Action
@RequestMapping(value = "/tran")
public class TransactionAction {
	@Autowired
	private TranService tranService;
	@Autowired
	private TestService testService;
	private static int index = 0;

	@Value("${sc.annot.init}")
	private String testinit;

	@Value("${sc.annot.init.3}")
	private String scannotinit3;

	@Value("${sc.annot.init.3}")
	private String scannotinit1;
	@RequestParam
	private String str1;
	@RequestParam
	private String str2;

	@RequestMapping(value = "/test")
	public String testTran(HttpServletRequest request) {
		try {
			tranService.saveTest();
			if (request.getParameter("index") != null) {
				index = 0;
			}
			index = index + 1;
			request.setAttribute("index", index);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/tran.jsp";
	}

	@RequestMapping(value = "/totran")
	public String toTran(HttpServletRequest request) {
		request.setAttribute("msg", "msgmsgmsgmsgmsgmsgmsgmsgmsgmsg");
		return "/tran.jsp";
	}
}
