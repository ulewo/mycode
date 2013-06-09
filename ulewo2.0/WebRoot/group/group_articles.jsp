<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}css/group.index.css">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}ueditor/";
//-->
</script>
<script type="text/javascript" src="${realPath}ueditor/editor_config.js"></script>
<script type="text/javascript" src="${realPath}ueditor/editor.js"></script>
<script type="text/javascript" src="${realPath}js/group.addarticle.js"></script>
<style type="text/css">
.new_article_input{width:900px;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="group_info">
			<div class="group_info_left">
				<div>
					<div class="group_icon"><img src="../upload/${group.groupIcon}"></div>
					<div class="group_info_con">
						<div class="group_title">${group.groupName}</div>
						<div class="group_author">
							<span class="group_info_tit" style="padding-left:0px;">管理员:</span><a href="">${group.authorName}</a> 
							<span class="group_info_tit">成员:</span>${group.members} 
							<span class="group_info_tit">创建时间:</span>${group.createTime}
						</div>
						<div class="group_url">http://group.ulewo.com/${group.id}&nbsp;&nbsp;<a href="" class="btn">+立即加入</a></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="group_desc">${group.groupDesc}</div>
			</div>
			<div class="group_info_notice">
				<div class="right_tit">公告</div>
				<div class="group_notic">暂无公告。</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}${gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}${gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}${gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<div class="new_article_p" id="new_article_p">
				<div class="new_article_input"></div>
				<div class="new_article_btn">发表帖子</div>
				<div class="clear"></div>
			</div>
			<div class="add_article" id="add_article">
				<%@ include file="addarticle.jsp" %>
			</div>
			<ul class="group_item">
				<li style="margin-left:2px;"><a href="${realPath}${gid}" <c:if test="${itemId==0}">class="select"</c:if>>全部文章</a></li>
				<c:forEach var="item" items="${itemList}">
					<li><a href="${realPath}${gid}?itemId=${item.id}" <c:if test="${itemId==item.id}">class="select"</c:if>>${item.itemName}</a></li>
				</c:forEach>
			</ul>
			<c:forEach var="article" items="${articles.list}">
				<div class="article_item">
					<div class="article_tit">
						<div class="article_title">
							<a href="${realPath}${gid}/topic?id=${article.id}">${article.title}</a>
							<span class="recount">${article.reNumber}/${article.readNumber}</span>
						</div>
						<div class="article_author">
							<a href="${myPath}${article.authorId}">${article.authorName}</a>
							<span class="article_posttime">发表于 ${article.postTime}</span>
						</div>
						<div class="clear"></div>
					</div>
					<div class="article_summary">
						 ${article.summary}
					</div>
				</div>
			</c:forEach>
			<div class="pagination">
				<c:if test="${itemId==0}">
					<p:pager url="${realPath}${gid}" page="${articles.page}" pageTotal = "${articles.pageTotal }"></p:pager>
				</c:if>
				<c:if test="${itemId!=0}">
					<p:pager url="${realPath}${gid}?itemId=${itemId}" page="${articles.page}" pageTotal = "${articles.pageTotal }"></p:pager>
				</c:if>
			</div>
		</div>
		<%@ include file="../common/foot_manage.jsp" %>
	</div>
</body>
</html>