<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.scyhytag.com/tags/scyhytag" prefix="sc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test_index.jsp</title>
</head>
<body>
<table width="100%">
	<sc:if test="${ts!=null }">
		<sc:each var="l" items="${ts }">
			<tr>
				<td>${l.emailId }</td>
				<td>${l.emailName }</td>
				<td>${l.emailAddress }</td>
			</tr>
		</sc:each>
	</sc:if>
</table>
</body>
</html>