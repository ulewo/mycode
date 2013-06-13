<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.article.css">
<style type="text/css">
#selected4 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="articleop">
				<a href="javascript:void(0)" class="btn" onclick="setTop('set')">置顶</a>
				<a href="javascript:void(0)" class="btn" onclick="setTop('cancel')">取消置顶</a>
				<a href="javascript:void(0)" class="btn" onclick="setGood('set')">精华</a>
				<a href="javascript:void(0)" class="btn" onclick="setGood('cancel')">取消精华</a>
				<a href="javascript:void(0)" class="btn" onclick="setTitle('set')">个性标题</a>
				<a href="javascript:void(0)" class="btn" onclick="deleteArticle()">删除</a>
			</div>
			<c:forEach var="article" items="${result.list}">
				<div class="article_item">
					<div class="article_item_check"><input type="checkbox" value="${article.id}"></div>
					<div class="article_item_tit"><a href="" title="${article.title}">${article.title}</a></div>
					<div class="article_item_itemname">
						${article.itemName}
						<c:if test="${article.itemName==null}">全部分类</c:if>
					</div>
					<div class="article_item_username">${article.authorName}</div>
					<div class="article_item_time">${article.postTime}</div>
					<div class="article_item_op"><a href="">删除</a></div>
					<div class="clear"></div>
				</div>
			</c:forEach>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>