package sc.yhy.test.action;

import sc.yhy.annotation.request.Action;
import sc.yhy.annotation.request.RequestMapping;

@Action
@RequestMapping(value = "/email/show")
public class ScShowTestAction {

	@RequestMapping(value = "/showEamil.action")
	public String getEmail() {
		return "/index.jsp";
	}

	@RequestMapping(value = "/addEmail.action")
	public String saveEmail() {
		return "/index.jsp";
	}
}
