<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理文章-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/blog.css">
<style>
.goto_ulewo{margin-top:20px;}
.goto_ulewo a{color:#999999;text-decoration:none;text-align:left;font-size:16px;}
.weekhot{text-align:center;font-size:16px;font-weight:bold;}
.article_attachedimg{display:inline-block;border:1px solid #B2B3B2;padding:2px;margin-top:10px;max-height:200px;overflow:hidden;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="goto_ulewo"><a href="">去有乐窝</a></div>
		<div class="weekhot">有乐窝一周精选</div>
		<c:forEach var="article" items="${list}">
			<div class="blogitem">
				<div class="blog_con">
					<div class="blog_title"><a href="${realPath}/group/${article.gid}/topic/${article.id}">${article.title}</a></div>
					<div class="blog_summary">${article.summary}</div>
					<c:if test="${article.image!=null&&article.image!=''}">
						<div class="article_attachedimg"><a href="${realPath}/group/${gid}/topic/${article.id}"><img src="${article.image}" style="max-width:150px;"/></a></div>
					</c:if>
					<div class="user_time">
						<span>${article.authorName}</span>
						<span>${article.postTime}</span>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</c:forEach>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript" src="${realPath}/js/admin.index.js"></script>
</body>
</html>