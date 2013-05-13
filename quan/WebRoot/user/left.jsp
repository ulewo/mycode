<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<c:set var="uid" value="${userId}">
</c:set>
<c:if test="${uid==null}">
<c:set var="uid" value="${param.userId}" />
</c:if>
<div class="leftmain">
	<div class="avatar_con">
		<div class="user_avatar"><a href="userInfo.jspx?userId=${uid}"><img src="" id="imgcon" style="display:none;" width="60px;" height="60px;"></a></div>
		<div class="user_edit"><a href="userInfo.jspx?userId=${uid}" id="user_name"></a></div>
	</div>
	<div class="resume" id="resume">
	</div>
	<div class="opts">
		<c:if test="${user!=null&&user.userId==uid}">
			<a href="addBlog.jspx?userId=${userId}" class="blog">
				<span class="blog_icon"></span>
				<span class="blog_tit">发表博文</span>
			</a>
			<a href="manage.jspx" class="manage">
				空间管理
			</a>
			<div class="clear"></div>
		</c:if>
		<c:if test="${user==null||user.userId!=uid}">
			<a href="message.jsp?userId=${uid}" class="sendMsg">
				发送留言
			</a>
		</c:if>
	</div>
	<div class="blogCatalogs">
		<div class="blogCatalogs_tit">博客分类</div>
		<div id="item">
			
		</div>
	</div>
	<a href="createdGroups.jspx?userId=${uid}" class="createwo">创建的窝窝</a>
	<a href="joinedGroups.jspx?userId=${uid}" class="joinwo">加入的窝窝</a>
	<a href="postTopics.jspx?userId=${uid}"class="topic">发表的文章</a>
	<a href="reTopics.jspx?userId=${uid}" class="reply">回复的文章</a>
	<script type="text/javascript" src="../js/user.left.js"></script>
	<script type="text/javascript">
		var userId = "${userId}"||"${param.userId}";
	</script>
</div>