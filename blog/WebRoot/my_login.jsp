<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员登陆</title>
<style type="text/css">
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
		<div class="main">
			<input type="text" name="name" id="name"><br>
			<input type="password" name="password" id="password">
			<input type="button" value="登录" onclick="login()">
		</div>
		<div class="foot"></div>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function login(){
		var name = $("#name").val();
		var password = $("#password").val();
		if(name==""){
			alert("请输入姓名");
			return;
		}
		if(password==""){
			alert("请输入密码");
			return ;
		}
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			data:{
				name :name,
				password : password
			},
			dataType : "json",
			url : 'login',// 
			success : function(data) {
				if(data.result=="success"){
					alert("登录成功");
					document.location.href="article";
				}else{
					alert("登录失败");
				}
			}
		});
	}
</script>
</body>
</html>