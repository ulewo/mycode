<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.groupreply.css">
<style type="text/css">
#selected5 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<c:forEach var="rearticle" items="${result.list}">
				<div class="item_reply">
					<div class="user_icon">
						<a href="${realPath}/user/${rearticle.authorid}" target="_blank"><img src="${rearticle.authorIcon}"/></a>
					</div>
					<div class="reply_con">
						<div class="title_op">
							<div class="blog_title"><a href="${realPaht}/group/${gid}/topic/${rearticle.articleId}" target="_blank">${rearticle.articleTitle}</a></div>
							<div class="reply_op"><a href="">删除</a></div>
							<div class="clear"></div>
						</div>
						<div class="reply_content">${rearticle.content}</div>
					</div>
					<div class="clear"></div>
				</div>
			</c:forEach>
			<div class="pagination" style="margin-top:10px;">
				<p:pager url="${realPath}/groupManage/${gid}/article_reply" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>