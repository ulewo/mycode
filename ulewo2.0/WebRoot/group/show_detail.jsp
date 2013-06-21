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
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.detail.css">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
global.articleId= "${article.id}";
</script>
<script type="text/javascript" src="${realPath}/ueditor/editor_config.js"></script>
<script type="text/javascript" src="${realPath}/ueditor/editor.js"></script>
<script type="text/javascript" src="${realPath}/js/group.showarticle.js"></script>
<script type="text/javascript" src="${realPath}/js/scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="${realPath}/js/styles/SyntaxHighlighter.css"/>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<ul class="group_item">
				<li style="margin-left:2px;"><a href="${realPath}/group/${gid}" <c:if test="${article.itemId==0}">class="select"</c:if>>全部文章</a></li>
				<c:forEach var="item" items="${itemList}">
					<li><a href="${realPath}/group/${gid}?itemId=${item.id}" <c:if test="${article.itemId==item.id}">class="select"</c:if>>${item.itemName}</a></li>
				</c:forEach>
			</ul>
			<div class="author_info">
				<div class="author_icon"><img src="${realPath}/upload/${article.author.userLittleIcon}"></div>
				<div class="author_info_con">
					<div class="article_tit">
						<span class="article_tit_title">${article.title}</span>
					</div>
					<div class="author_info_content">
						<a href="${realPath}/user/${article.author.userId}">${article.author.userName}</a>&nbsp;
						发表于 ${article.postTime}&nbsp;
						阅读  ${article.readNumber}&nbsp;
						回复  ${article.reNumber} 
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="article_detail">
				${article.content}
			</div>
			<c:if test="${article.file!=''&&article.file!=null}">
			<div class="article_attached">
				<div class="attached_tit">附件</div>
				<div class="attached_con"><span>${article.file.fileName}</span>&nbsp;&nbsp;<a href="">点击下载</a></div>
			</div>
			</c:if>
			<div class="recomment_tit">共有${article.reNumber}个回帖</div>
			<div id="recomment"></div>
			<div id="pager" class="pagination"></div>
			<div class="new_article_p" id="new_article_p">
				<div class="new_article_input"></div>
				<div class="new_article_btn">回复帖子</div>
				<div class="clear"></div>
			</div>
			<div class="add_article" id="add_article">
				<%@ include file="rearticle.jsp" %>
			</div>
		</div>
		<%@ include file="../common/foot_manage.jsp" %>
	</div>
</body>
</html>