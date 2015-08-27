package com.yhy.test.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.annot.Value;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.data.nosql.redis.RedisCluster;
import sc.yhy.data.nosql.redis.RedisUtil;

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
			RedisCluster cluster = RedisUtil.newClusterInstance();
			System.out.println(cluster.set("un", "bbbb"));
			System.out.println(cluster.get("age"));
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", "zhangsan");
			map.put("age", "24");
			map.put("len", "159cm");
			cluster.hmset("zhangsan", map);
			System.out.println(cluster.hmget("zhangsan", "name","pp"));
			cluster.rpush("testr", "one","two");
			System.out.println(cluster.rpop("testr"));

			request.setAttribute("msg", json);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/jsp/html.jsp";
	}
}
