package com.yhy.test.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;

import sc.yhy.annotation.Autowired;
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
@RequestMapping(value = "/email/send")
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
	public String test(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestParam(value = "bb") String bb, @RequestParam(value = "testBean") TestBean testBean) {
		request.setAttribute("message", "aa  " + request.getParameter("bb") + "  testService=" + testService.getStr() + "  this.testBean=" + this.testBean);
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
					File file = new File("E:\\upload\\" + fi.getName());
					if (!file.exists()) {
						file.createNewFile();
					}
					OutputStream os = new FileOutputStream(file);
					while ((r = is.read(b)) != -1) {
						os.write(b, 0, r);
					}
					os.close();
					os.flush();
					is.close();
				}
			}
			int r = tranService.saveTran(this.testBean);
			System.out.println(r);
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
