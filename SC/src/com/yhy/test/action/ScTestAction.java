package com.yhy.test.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.injection.MultipartFile;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.annotation.request.ResponseBody;

import com.yhy.test.entity.TestBean;
import com.yhy.test.service.TestService;

/**
 * 测试action
 * 
 * @author YHY
 *
 */
@Action
@RequestMapping(value = "/email/send")
public class ScTestAction {
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
	public String index(HttpServletRequest request) {
		request.setAttribute("aaaaa", testService.getStr());
		return "/test_index.jsp";
	}

	@ResponseBody
	@RequestMapping(value = "/ajax.action")
	public String ajax(HttpServletRequest request) {
		request.setAttribute("aaaaa", testService.getStr());
		return "/test_index.jsp";
	}

	@RequestMapping(value = "/test.action")
	public String test(HttpServletRequest request, @RequestParam(value = "bb") String bb, @RequestParam(value = "testBean") TestBean testBean) {
		request.setAttribute("message", "aa  " + request.getParameter("bb") + "  testService=" + testService + "  this.testBean=" + this.testBean);
		List<TestBean> list = new ArrayList<TestBean>();
		TestBean tb1 = new TestBean();
		tb1.setEmailId("123123");
		tb1.setEmailName("sn sn 杨");
		list.add(tb1);
		list.add(tb1);
		list.add(tb1);
		list.add(tb1);
		request.setAttribute("list", list);
		try {
			// 解析requesst
			List<FileItem> fileItems = this.files.getFileItem();
			if (fileItems != null) {
				for (FileItem fi : fileItems) {
					InputStream is = fi.getInputStream();
					byte[] b = new byte[1024];
					int r = 0;
					System.out.println(fi.getFieldName());
					while ((r = is.read(b)) != -1) {
						System.out.println(new String(b, 0, r));
					}
					is.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/index.jsp";
	}

	@ResponseBody
	@RequestMapping(value = "/sendEmail.action")
	public String sendEmail() {
		return "/index.jsp";
	}
}
