<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.manage.blogreply.css">
<style type="text/css">
#selected6 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="right_top_m">
					<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt;评论管理
					<c:if test="${blogitem!=null}">
						&gt;&gt;<a href="">${blogitem.itemName}</a>
					</c:if>
			</div>
			<c:forEach var="reply" items="${replyList.list}">
				<div class="item_reply">
					<div class="user_icon"><a href="/${reply.userId}">
					<c:if test="${reply.reUserIcon==null}"><img src="../upload/default.gif"/></c:if>
					<c:if test="${reply.reUserIcon!=null}"><img src="../upload/${reply.reUserIcon}"/></c:if>
					</a></div>
					<div class="reply_con">
						<div class="title_op">
							<div class="blog_title"><a href="">${reply.blogTitle}</a></div>
							<div class="reply_op"><a href="">删除</a></div>
							<div class="clear"></div>
						</div>
						<div class="reply_content">${reply.content}</div>
					</div>
					<div class="clear"></div>
				</div>
			</c:forEach>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>