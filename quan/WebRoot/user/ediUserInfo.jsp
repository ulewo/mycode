<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>友吧中国</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
		#sel_left1 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left1 a:hover{text-decoration:none;}
		a.sel1{background:#5A5A5A;color:white;font-weight:bold;}
	</style>
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
  <div class="bodycon">
  	  <jsp:include page="menue.jsp"></jsp:include>
	  <div class="user_main">
		  <div class="left">
		  	<jsp:include page="user_left.jsp"></jsp:include>
		  </div>
		  <div class="right">
		  	<form action="updateUserInfo.jspx" method="post">
		    <input type="hidden" name="userId" value="${user.userId }"/>
		  	ID:${user.userId}<br>
		  	昵称：${user.userName }<br>
		  	性别：
		  	<input type="radio" value="M" name="sex" <c:if test="${user.age=='靓仔'}">checked="checked" </c:if>>靓仔&nbsp;&nbsp;&nbsp;&nbsp;
		  	<input type="radio" value="F" name="sex" <c:if test="${user.age=='美女'}">checked="checked" </c:if>>美女<br>
		  		  
		  	年龄：<input type="text" value="${user.age }" name="age"><br>
		  	职业：<input type="text" value="${user.work}" name="work"><br>
		  	地址：<input type="text" value="${user.address }" name="address"><br>
		  	个人签名:<input type="text" value="${user.characters}" name="characters"><br>
		  	注册时间：${fn:substring(user.registerTime,0,10) }<br>
		  	<input type="submit" value="提交">
		  	</form>
		  </div>
	  </div>
  </div>
  </body>
</html>
