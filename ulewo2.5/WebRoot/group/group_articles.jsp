<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName} 文章-有乐窝</title>
<meta name="description" content="${group.groupName} 文章">
<meta name="keywords" content="${group.groupName} 文章">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>
<%@ include file="../common/ueditorcommon.jsp" %>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.index.css?version=2.5">
<script type="text/javascript" src="${realPath}/js/group.addarticle.js"></script>
<script type="text/javascript" src="${realPath}/js/calender/WdatePicker.js"></script>
<script type="text/javascript">
	$(function(){
		lazyLoadImage("article_attachedimg");
	})
</script>
<style type="text/css">
.new_article_input{width:900px;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${group.gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${group.gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${group.gid}/attachedFile">资&nbsp;&nbsp;源</a></li>
				<li><a href="${realPath}/group/${group.gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<ul class="group_item">
				<li style="margin-left:2px;"><a href="${realPath}/group/${gid}" <c:if test="${cateId==null}">class="select"</c:if>>全部文章</a></li>
				<c:forEach var="item" items="${categoryList}">
					<li><a href="${realPath}/group/${gid}/cateId/${item.categoryId}" <c:if test="${cateId==item.categoryId}">class="select"</c:if>>${item.name}</a></li>
				</c:forEach>
			</ul>
			<c:if test="${category.allowPost=='Y'}">
				<c:if test="${user!=null}">
						<div class="new_article_p" id="new_article_p">
							<div class="new_article_input"></div>
							<div class="new_article_btn">发表帖子</div>
							<div class="clear"></div>
						</div>
						<div class="add_article" id="add_article">
							<%@ include file="addarticle.jsp" %>
						</div>
					</c:if>
					<c:if test="${user==null}">
						<div class="new_article_p">
							<div class="new_article_input"></div>
							<div class="new_article_btn">发表帖子</div>
							<div class="clear"></div>
							<div class="add_hide">
								<div class="shadeLogin" style="margin-top:10px;margin-left:150px;">发帖，请先 <a href="javascript:goto_login()">登录</a>&nbsp;&nbsp;<a href="javascript:goto_register()">注册</a></div>
							</div>
						</div>
						<div class="add_article" id="add_article">
							<%@ include file="addarticle.jsp" %>
						</div>
				</c:if>
			</c:if>
			<div id="article_item_list" class="article_item_list">
				<c:forEach var="article" items="${topicResult.list}">
					<div class="article_item">
						<div class="article_tit">
							<div class="article_title">
								<c:if test="${article.grade=='2'}">
									<div class="article_title_icon"><img src="${realPath}/images/zding.gif"></div>
								</c:if>
								<c:if test="${article.essence=='Y'}">
									<div class="article_title_icon"><img src="${realPath}/images/jing.gif"></div>
								</c:if>
								<c:if test="${article.topicType=='1'}">
									<div class="article_title_icon"><img src="${realPath}/images/tpiao.gif"></div>
								</c:if>
								<a href="${realPath}/group/${gid}/topic/${article.topicId}">${article.title}</a>
								<span class="recount">${article.commentCount}/${article.readCount}</span>
								<div class="clear"></div>
							</div>
							<div class="article_author">
								<a href="${realPath}/user/${article.userId}">${article.userName}</a>
								<span class="article_posttime">发表于 ${article.showCreateTime}</span>
							</div>
							<div class="clear"></div>
						</div>
						<div class="article_summary">
							 ${article.summary}
						</div>
						<c:if test="${article.images!=''&&article.images!=null}">
							<div class="article_pic">
								<c:forEach items="${article.images}" var="tech" begin="0" end="2">
									<div class="article_attachedimg"><a href="${realPath}/group/${gid}/topic/${article.topicId}"><img src="${tech}" name="${tech}" style="max-width:150px;"/></a></div>
								</c:forEach>
								<div class="clear"></div>
							</div>
						</c:if>
					</div>
				</c:forEach>
			</div>
			<c:if test="${empty topicResult.list}">
					<div class="noinfo">没有文章,赶紧抢沙发吧！</div>
			</c:if>
			<div class="pagination">
				<c:if test="${cateId==null}">
					<p:pager url="${realPath}/group/${gid}" page="${topicResult.page.page}" pageTotal = "${topicResult.page.pageTotal }"></p:pager>
				</c:if>
				<c:if test="${cateId!=null}">
					<p:pager url="${realPath}/group/${gid}/cateId/${cateId}" page="${topicResult.page.page}" pageTotal = "${topicResult.page.pageTotal }"></p:pager>
				</c:if>
			</div>
		</div>
		<%@ include file="../common/foot.jsp" %>
	</div>
	<div class="righ_ad">
		<div><a href="javascript:void(0)" onclick="$('.righ_ad').hide()">关闭</a></div>
		<div>
		<script type="text/javascript">
			/*160*600，创建于2013-7-10*/
			var cpro_id = "u1317726";
			</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  		</div>
	</div>
</body>
</html>