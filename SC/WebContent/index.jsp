<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.scyhytag.com/tags/scyhytag" prefix="sc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试页面</title>
</head>
<body>
<sc:each var="l" items="${list }">
	${l.emailId }
</sc:each>
	<form action="/email/send/toEamil.action" method="post">
		<input type="text" id="str1" name="str1" value="str1杨杨" /><br/>
		<input type="text" id="int1" name="int1" value="1123" /><br/>
		<input type="text" id="testBean.emailId" name="testBean.emailId" value="testBean.emailId333" /><br/>
		<input type="text" id="testBean.emailName" name="testBean.emailName" value="电子邮件" /><br/>
		<input type="text" id="testBean.emailAddress" name="testBean.emailAddress" value="地址" /><br/>
		<input type="text" id="email1.emailId" name="email1.emailId" value="email1.emailId444" /><br/>
		<input type="text" id="bb" name="bb" value="bbb" /><br/>
		<input type="text" id="testBean.testBeanSon.testid" name="testBean.testBeanSon.testid" value="子testid" /><br/>
		<input type="text" id="testBean.testBeanSon.testName" name="testBean.testBeanSon.testName" value="子testName" /><br/>
		<input type="text" id="testBean.listSon.testid" name="testBean.listSon.testid" value="testBean.listSon.testid888" /><br/>
		<input type="text" id="testBean.listSon.testid" name="testBean.listSon.testid" value="testBean.listSon.testid999" /><br/>
		<input type="text" id="testBean.listSon.testName" name="testBean.listSon.testName" value="testBean.listSon.testName张1" /><br/>
		<input type="text" id="testBean.listSon.testName" name="testBean.listSon.testName" value="testBean.listSon.testName张2" /><br/>
		<input type="text" id="testBean.listStr" name="testBean.listStr" value="testBean.listStr张3" /><br/>
		<input type="text" id="testBean.listStr" name="testBean.listStr" value="testBean.listStr张4" /><br/>
		<input type="submit" id="sit" name="sit" value="提交" /><br/>
	</form>
</body>
</html>