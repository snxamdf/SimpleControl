<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.scyhytag.com/tags/scyhytag" prefix="sc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/SC/js/jquery.min.js"></script>
<script type="text/javascript">
</script>
<style type="text/css">
	input{
		width: 100%;
	}
</style>
<title>测试页面</title>
</head>
<body>
	<form action="/SC/send/test" method="post" enctype="multipart/form-data">
		<input type="text" id="str1" name="str1" value="str1杨杨" /><br/>
		<input type="text" id="int1" name="int1" value="1123" /><br/>
		<input type="text" id="testBean.emailId" name="testBean.emailId" value="testBean.emailId333" /><br/>
		<input type="text" id="testBean.emailName" name="testBean.emailName" value="电子邮件" /><br/>
		<input type="text" id="testBean.emailAddress" name="testBean.emailAddress" value="地址" /><br/>
		<input type="text" id="testBean.emailId" name="testBean.emailId" value="testBean.emailId444" /><br/>
		<input type="text" id="testBean.trans.uid" name="testBean.trans.uid" value="testid" /><br/>
		<input type="text" id="testBean.trans.uname" name="testBean.trans.uname" value="testName" /><br/>
		<input type="file" id="files" name="files" /><br/>
		<input type="file" id="files" name="files" /><br/>
		<input type="file" id="files" name="files" /><br/>
		<input type="file" id="files" name="files" /><br/>
		<input type="file" id="files" name="files" /><br/>
		<input type="submit" id="sit" name="sit" value="提交" /><br/>
	</form>
<br/>
${ts }
<sc:if test="${ts!=null }">
	<sc:each var="l" items="${ts }">
		${l.emailId }
	</sc:each>
</sc:if>
<br/>
</body>
</html>