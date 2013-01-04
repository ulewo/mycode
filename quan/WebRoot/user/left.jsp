<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="leftmain">
	<div class="avatar_con">
		<div class="user_avatar"><a href="userInfo.jspx?userId=${userId}"><img src="../upload/${userInfo.userBigIcon }" id="imgcon"></a></div>
		<div class="user_edit"><a href="userInfo.jspx?userId=${userId}">${userInfo.userName }</a></div>
	</div>
	<div class="resume">
		这个人很懒，什么都没留下
	</div>
	<div class="opts">
		<c:if test="${user!=null&&user.userId==userId}">
			<a href="####" class="blog">
				<span class="blog_icon"></span>
				<span class="blog_tit">发表博文</span>
			</a>
			<a href="####" class="manage">
				<span class="manage_icon"></span>
				<span class="manage_tit">空间管理</span>
			</a>
			<div class="clear"></div>
		</c:if>
		<c:if test="${user==null||user.userId!=userId}">
		
		</c:if>
	</div>
</div>