<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="leftmain">
	<div class="avatar_con">
		<div class="user_avatar"><a href="userInfo.jspx?userId=${userId}"><img src="" id="imgcon" style="display:none;"></a></div>
		<div class="user_edit"><a href="userInfo.jspx?userId=${userId}" id="user_name"></a></div>
	</div>
	<div class="resume" id="resume">
	</div>
	<div class="opts">
		<c:if test="${user!=null&&user.userId==userId}">
			<a href="addBlog.jspx?userId=${userId}" class="blog">
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
		<div id="item">
			
		</div>
	</div>
	<a href="createdGroups.jspx?userId=${userId}" class="createwo">创建的窝窝</a>
	<a href="joinedGroups.jspx?userId=${userId}" class="joinwo">加入的窝窝</a>
	<a href="postTopics.jspx?userId=${userId}"class="topic">发表的文章</a>
	<a href="reTopics.jspx?userId=${userId}" class="reply">回复的文章</a>
	<script type="text/javascript" src="../js/user.left.js"></script>
	<script type="text/javascript">
		var userId = "${userId}";
	</script>
</div>