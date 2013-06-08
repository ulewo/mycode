﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
				<div class="blog_detail">
						<div class="blog_list_tit"><a href="">${blog.title}</a></div>
						<div class="blog_item_op">
							<span>发布于 ${blog.postTime}，阅读(<span class="blog_item_op_red">${blog.readCount}</span>)|评论(<span class="blog_item_op_red">${blog.reCount}</span>)</span>
							<c:if test="${user.userId==userId}">(<a href="">修改</a>|<a href="">删除</a>)</c:if>
						</div>
						<div class="blog_content">
							${blog.content}
						</div>
				</div>
			</div>
				<div class="blog_reply">
					<div class="blog_reply_tit">评论</div>
					<div class="blog_reply_list" id="messagelist">
					</div>
				</div>
				<div class="blog_reply_form">
					<form id="subform"> 
				  		<input type="hidden" name="atUserName" id="atUserName">
				  		<input type="hidden" name="atUserId" id="atUserId">
				  		<div>
				  			<div class="blogreply_icon">
				  				<c:if test="${user!=null}">
									<img src="../upload/${user.userLittleIcon}" width="37">
								</c:if>
								<c:if test="${user==null}">
									<img src="../upload/default.gif" width="37">
								</c:if>
				  			</div>
				  			<div class="blog_reply_content">
				  				<div class="content"><textarea name="content" id="content"></textarea></div>
				  				<div class="sub_op">
						  			<div class="subbtn">
						  				<a href="javascript:subReply('${param.id}')" class="btn" onfocus="this.blur()" id="sendBtn">发表留言</a>
						  				<img src="../images/load.gif" id="loading" style="display:none">
						  			</div>
				  					<div class="warm_info">最多输入500字符</div>
				  				</div>
				  			</div>
				  			<div class="clear"></div>
				  		</div>
				  	</form>
			  		<c:if test="${user==null}">
							<div class="shade blogshade" id="shade">
								<div class="shadeLogin">回复，请先 <a href="javascript:login()">登录</a>&nbsp;&nbsp;<a href="javascript:register()">注册</a></div>
							</div>
					</c:if>
				</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}js/user.blogreply.js"></script>
	<script type="text/javascript">
		var realPath = "${realPath}";
		var blogId = "${blog.id}";
		var userId="${userId}";
		var sessionUserId = "${user.userId}";
	</script>
</body>
</html>