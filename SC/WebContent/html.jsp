<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.scyhytag.com/tags/scyhytag" prefix="sc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>测试页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="/SC/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		var o=$(".a.b");
		alert(o.html());
		o=$("[class='a b']");
		alert(o.html());
	})
</script>
</head>
<body class="a b">
	<input type="radio" name="list0" value="0" />0<br/>
	<input type="radio" name="list0" value="1" />1<br/>
	<input type="radio" name="list0" value="2" />2<br/>
	<input type="radio" name="list0" value="3" />3<br/>
	
	<input type="radio" name="list1" value="0" />0<br/>
	<input type="radio" name="list1" value="1" />1<br/>
	<input type="radio" name="list1" value="2" />2<br/>
	<input type="radio" name="list1" value="3" />3<br/>
	
	<input type="radio" name="list2" value="0" />0<br/>
	<input type="radio" name="list2" value="1" />1<br/>
	<input type="radio" name="list2" value="2" />2<br/>
	<input type="radio" name="list2" value="3" />3<br/>
</body>
</html>