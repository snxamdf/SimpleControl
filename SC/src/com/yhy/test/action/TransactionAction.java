package com.yhy.test.action;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Value;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.data.nosql.redis.Redis;
import sc.yhy.data.nosql.redis.RedisCache;

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
		try {
			String json = tranService.saveTestMongo();
			request.setAttribute("msg", json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/tran.jsp";
	}

	@RequestMapping(value = "/tohtml")
	public String toHtml(HttpServletRequest request) {
		try {
			String json = tranService.saveTestMongo();
			Redis redis = RedisCache.newInstance();
			if (redis.exists("msg")) {
				request.setAttribute("msg", json);
			} else {
				redis.set("msg", Math.random() + "");
			}
			redis.set("abcd", "[{aaaa},{bbbb}]");
			System.out.println(redis.get("abcd"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/jsp/html.jsp";
	}
}
