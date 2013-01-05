<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="leftmain">
	<div class="avatar_con">
		<div class="user_avatar"><a href="userInfo.jspx?userId=${userId}"><img src="../upload/${userVo.userLittleIcon }" id="imgcon"></a></div>
		<div class="user_edit"><a href="userInfo.jspx?userId=${userId}">${userVo.userName }</a></div>
	</div>
	<div class="resume">
		<c:if test="${userVo.characters!=null}">
			${userVo.characters}
		</c:if>
		<c:if test="${userVo.characters==null}">
			这个人很懒，什么都没留下
		</c:if>
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
	<div class="blogCatalogs">
		<div class="blogCatalogs_tit">博客分类</div>
		<div id="catalog">
			
		</div>
	</div>
	<div class="createwo">创建的窝窝</div>
	<div class="joinwo">加入的窝窝</div>
	<div class="topic">发表的文章</div>
	<div class="reply">回复的文章</div>
</div>