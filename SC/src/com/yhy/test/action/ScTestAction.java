package com.yhy.test.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sc.yhy.annotation.annot.Autowired;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.annotation.request.ResponseBody;
import sc.yhy.fileupload.MultipartFile;

import com.yhy.test.entity.TestBean;
import com.yhy.test.service.TestService;
import com.yhy.test.service.TranService;

/**
 * 测试action
 * 
 * @author YHY
 *
 */
@Action
@RequestMapping(value = "/send")
public class ScTestAction {
	@Autowired
	private TranService tranService;
	@Autowired
	private TestService testService;

	@RequestParam
	private String str1;

	@RequestParam
	private Integer int1;

	@RequestParam
	private TestBean testBean;

	@RequestParam
	private MultipartFile files;

	@RequestMapping(value = "/index.action")
	public String index(HttpServletRequest request) throws SQLException {
		request.setAttribute("ts", testService.getStr());
		return "/test_index.jsp";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax.action")
	public String ajax(HttpServletRequest request) throws SQLException {
		request.setAttribute("aaaaa", testService.getStr());
		return "/test_index.jsp";
	}

	@RequestMapping(value = "/test.action")
	public String test(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam(value = "bb") String bb, @RequestParam(value = "testBean") TestBean testBean) {
		try {
			tranService.saveTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/index.jsp";
	}

	@RequestMapping(value = "/totest.action")
	public String toTest() {
		return "/index.jsp";
	}
}
