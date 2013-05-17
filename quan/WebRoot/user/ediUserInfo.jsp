<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/user.userinfo.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
		#selected1 a{background:#FFFFFF;}
	</style>
  </head>
  <body>
<%@ include file="../common/head.jsp" %>
  <div class="main">
	   <div class="left">
	  	<jsp:include page="menue.jsp"></jsp:include>
	   </div>
	  <div class="right" style="line-height:25px;">
	  	<div class="navPath"><a href="userInfo.jspx?userId=${user.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;编辑个人信息</div>
	  	<form  method="post" id="baseInfo">
	    <input type="hidden" name="userId" value="${user.userId }"/>
	  	ID:${user.userId}<br>
	  	昵称：${user.userName }<br>
	  	性别：
	  	<input type="radio" value="M" name="sex" <c:if test="${userVo.sex=='M'}">checked="checked" </c:if>>靓仔&nbsp;&nbsp;&nbsp;&nbsp;
	  	<input type="radio" value="F" name="sex" <c:if test="${userVo.sex=='F'}">checked="checked" </c:if>>美女<br>
	  	年龄：<input type="text" value="${userVo.age }" name="age"><br>
	  	职业：<input type="text" value="${userVo.work}" name="work"><br>
	  	地址：<input type="text" value="${userVo.address }" name="address"><br>
	  	个人签名:<input type="text" value="${userVo.characters}" name="characters"><br>
	  	注册时间：${userVo.registerTime}<br>
	  	<div style="margin-top:10px;">
	  		<div><input type="button" class="button" id="subBtn" value="提交" onclick="saveBaseInfo()"><img id="loadImg" style="display:none;" src="../images/loading.gif" width="20"></div>
	  		<div id="resultInfo" style="display:none;"></div>
	  	</div>
	  	</form>
	  </div>
  		<div class="clear"></div>
  </div>
   <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
