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
<meta name="description" content="${group.groupName}">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.index.css">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>
<script type="text/javascript" src="${realPath}/ueditor/editor_config.js"></script>
<script type="text/javascript" src="${realPath}/ueditor/editor.js"></script>
<script type="text/javascript" src="${realPath}/js/group.index.js"></script>
<script type="text/javascript" src="${realPath}/js/group.addarticle.js"></script>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<div class="left">
				<ul class="group_tag">
					<li><a href="${realPath}/group/${gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
					<li><a href="${realPath}/group/${gid}/img">图&nbsp;&nbsp;片</a></li>
					<li><a href="${realPath}/group/${gid}/member">成&nbsp;&nbsp;员</a></li>
				</ul>
				<ul class="group_item">
					<li style="margin-left:2px;"><a href="${realPath}/group/${gid}" <c:if test="${itemId==0}">class="select"</c:if>>全部文章</a></li>
					<c:forEach var="item" items="${itemList}">
						<li><a href="${realPath}/group/${gid}?itemId=${item.id}" <c:if test="${itemId==item.id}">class="select"</c:if>>${item.itemName}</a></li>
					</c:forEach>
				</ul>
				<div class="new_article_p" id="new_article_p">
					<div class="new_article_input"></div>
					<div class="new_article_btn">发表帖子</div>
					<div class="clear"></div>
				</div>
				<div class="add_article" id="add_article">
					<%@ include file="addarticle.jsp" %>
				</div>
				<div id="article_item_list">
				<c:forEach var="article" items="${articles.list}">
					<div class="article_item">
						<div class="article_tit">
							<div class="article_title">
								<c:if test="${article.grade=='2'}">
									<div class="article_title_icon"><img src="${realPath}/images/ico-top.gif"></div>
								</c:if>
								<c:if test="${article.essence=='Y'}">
									<div class="article_title_icon"><img src="${realPath}/images/ico-ess.gif"></div>
								</c:if>
								<a href="${realPath}/group/${gid}/topic/${article.id}">${article.title}</a>
								<span class="recount">${article.reNumber}/${article.readNumber}</span>
								<div class="clear"></div>
							</div>
							<div class="article_author">
								<a href="${realPath}/user/${article.authorId}">${article.authorName}</a>
								<span class="article_posttime">发表于 ${article.postTime}</span>
							</div>
							<div class="clear"></div>
						</div>
						<div class="article_summary">
							 ${article.summary}
						</div>
						<c:if test="${article.image!=''&&article.image!=null}">
							<div class="article_attachedimg"><a href="${realPath}/group/${gid}/topic/${article.id}"><img src="${article.image}" style="max-width:150px;"/></a></div>
						</c:if>
					</div>
				</c:forEach>
				</div>
				<c:if test="${empty articles.list}">
					<div class="noinfo">没有文章,赶紧抢沙发吧！</div>
				</c:if>
				<div class="pagination">
					<c:if test="${itemId==0}">
						<p:pager url="${realPath}/group/${gid}" page="${articles.page}" pageTotal = "${articles.pageTotal }"></p:pager>
					</c:if>
					<c:if test="${itemId!=0}">
						<p:pager url="${realPath}/group/${gid}?itemId=${itemId}" page="${articles.page}" pageTotal = "${articles.pageTotal }"></p:pager>
					</c:if>
				</div>
			</div>
			<div class="right">
				<div class="right_tit">
					最活跃成员
				</div>
				<div id="activer_list">
				
				</div>
				<div class="right_tit" style="margin-top:15px;">
					<span>最活跃成员</span>
					<a href="${realPath}/group/${gid}/member">更多</a>
					<div class="clear"></div>
				</div>
				<div id="member_list">
				
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<%@ include file="../common/foot_manage.jsp" %>
	</div>
</body>
</html>