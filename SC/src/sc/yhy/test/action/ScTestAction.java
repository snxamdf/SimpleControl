package sc.yhy.test.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import sc.yhy.annotation.Autowired;
import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestBody;
import sc.yhy.annotation.request.RequestMapping;
import sc.yhy.annotation.request.RequestParam;
import sc.yhy.test.entity.TestBean;
import sc.yhy.test.service.TestService;

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

	@RequestMapping(value = "/toEamil.action")
	public String toEmail(HttpServletRequest request, @RequestParam(value = "bb") String bb, @RequestParam(value = "email1") TestBean testBean) {
		System.out.println("aa  " + request.getParameter("bb"));
		request.setAttribute("message", "aa  " + request.getParameter("bb") + "  testService=" + testService + "  this.testBean=" + this.testBean);
		String str = testService.getStr();
		System.out.println(str);
		System.out.println(this.testBean);
		List<TestBean> list = new ArrayList<TestBean>();
		TestBean tb1 = new TestBean();
		tb1.setEmailId("123123");
		tb1.setEmailName("sn sn 杨");
		list.add(tb1);
		list.add(tb1);
		list.add(tb1);
		list.add(tb1);
		request.setAttribute("list", list);

		return "/index.jsp";
	}

	@RequestBody
	@RequestMapping(value = "/sendEmail.action")
	public String sendEmail() {
		return "/index.jsp";
	}
}
