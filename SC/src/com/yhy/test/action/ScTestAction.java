package com.yhy.test.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.injection.MultipartFile;
import sc.yhy.annotation.injection.MultipartFileStream;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.ResponseBody;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;

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

	@RequestMapping(value = "/toEamil.action")
	public String toEmail(HttpServletRequest request, @RequestParam(value = "bb") String bb, @RequestParam(value = "email1") TestBean testBean) {
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
			// 设置上传进度监听
			this.files.setProgressListener(true, request);
			// 解析requesst
			this.files.parseRequest(request);
			//
			MultipartFileStream[] fileStream = this.files.getMultipartFilesStream();
			for (MultipartFileStream mfs : fileStream) {
				if (mfs != null) {
					InputStream fis = mfs.getInputStream();
					byte[] b = new byte[2048];
					int r = 0;
					File file = new File("e:\\upload\\bak" + Math.random() + mfs.getFileName());
					if (!file.exists()) {
						file.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(file);
					while ((r = fis.read(b)) != -1) {
						fos.write(b, 0, r);
					}
					fos.close();
					fis.close();
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
