<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="left_userimg">
  	<a href="userInfo.jspx?userId=${userId}"><img src="../upload/${userInfo.userBigIcon }" id="imgcon"></a>
</div>
<div class="left_userid">
	<span><a href="userInfo.jspx?userId=${userId}">${userInfo.userName }</a></span>(${userInfo.userId})
</div>
<c:if test="${user!=null&&user.userId==userId}">
	<div class="safeInfo">
		<a href="getInfo.jspx?userId=${userId }" class="sel1">修改个人信息</a>
		<a href="userIcon.jspx?userId=${userId }" class="sel2">修改图像</a>
		<a href="changePassword.jspx?userId=${userId }" class="sel3">修改密码</a>
	</div>
	
</c:if>
