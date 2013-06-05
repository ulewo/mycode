<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人博客-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}css/user.blog.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
			<%@ include file="left.jsp" %>
			<div class="left_item">
				<div class="left_item_tit">博客分类</div>
				<div class="left_img_p">
					<c:forEach var="item" items="${blogItemList}">
						<div class="blog_item"><a href="blog?itemId=${item.id}">${item.itemName}</a><span>(${item.articleCount})</span></div>
					</c:forEach>
				</div>
			</div>
			<div class="left_item">
				<div class="left_item_tit">阅读排行</div>
				<div class="left_img_p">
					<div class="blog_rang"><a href="">1. 【CF 应用开发大赛】有乐窝 大型服务社区，让你的生活更精彩</a></div>
					<div class="blog_rang"><a href="">1. 【CF 应用开发大赛】有乐窝 大型服务社区，让你的生活更精彩</a></div>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="blog_list">
				<div class="right_top_m">
					<a href="">空间</a>&gt;&gt;<a href="blog">博客</a>
					&gt;&gt;<a href="/${userId}/blog?itemId=${blog.itemId}">${blog.itemName}</a>
				
				</div>
				<div class="blog_list_item">
						<div class="blog_list_tit"><a href="">${blog.title}</a></div>
						<div class="blog_item_op">
							<span>发布于 ${blog.postTime}，阅读(<span class="blog_item_op_red">${blog.readCount}</span>)|评论(<span class="blog_item_op_red">${blog.reCount}</span>)</span>
							<c:if test="${user.userId==userId}">(<a href="">修改</a>|<a href="">删除</a>)</c:if>
						</div>
						<div class="blog_summary">
							${blog.content}
						</div>
				</div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
</body>
</html>