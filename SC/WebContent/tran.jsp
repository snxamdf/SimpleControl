<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tran事务测试</title>
<script type="text/javascript" src="/SC/js/jquery.min.js"></script>
<script type="text/javascript">
	$(function() {
		var tbody = "";
		var obj = [ {
			"name" : "ghds",
			"password" : "12"
		}, {
			"name" : "qw",
			"password" : "123"
		} ];
		$("#result").html("遍历List集合.each的使用");
		$.each(obj, function(n, value) {
			var object = obj[n];
			//alert(object.name + "  " + object.password);
			var trs = "";
			trs += "<tr><th>" + value.name + "</th><th>" + value.password
					+ "</th></tr>";
			tbody += trs;
		});
		$("#project").append(tbody);
	});
</script>
</head>
<body>tran事务测试 () ${index}
</body>
</html>
