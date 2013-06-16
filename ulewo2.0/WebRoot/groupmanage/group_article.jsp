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
				<a href="javascript:void(0)" class="btn" id="setTop" >置顶</a>
				<a href="javascript:void(0)" class="btn" id="cancelTop">取消置顶</a>
				<a href="javascript:void(0)" class="btn" id="setGood">精华</a>
				<a href="javascript:void(0)" class="btn" id="cancelGood">取消精华</a>
				<a href="javascript:void(0)" class="btn" id="setTitle">个性标题</a>
				<a href="javascript:void(0)" class="btn" id="deleteArticle">删除</a>
				<select id="article_item">
					<option value="">全部分类</option>
					<c:forEach var="item" items="${itemList}">
						<option value="${item.id}" <c:if test="${itemId==item.id}">selected="selected""</c:if>>${item.itemName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="article_list">
				<form action="" method="post" id="subform">
				<input type="hidden" value="" id="opType" name="type">
				<c:forEach var="article" items="${result.list}">
					<div class="article_item">
						<div class="article_item_check"><input type="checkbox" name="id" class="checkId" value="${article.id}"></div>
						<div class="article_item_tit"><a href="${realPath}/group/${gid}/topic/${article.id}" title="${article.title}" target="_blank">${article.title}</a></div>
						<div class="article_item_itemname">
							${article.itemName}
							<c:if test="${article.itemName==null}">全部分类</c:if>
						</div>
						<div class="article_item_username">${article.authorName}</div>
						<div class="article_item_time">${article.postTime}</div>
						<div class="article_item_op"><a href="${realPath}/groupManage/${gid}/edit_article?id=${article.id}">修改</a></div>
						<div class="clear"></div>
					</div>
				</c:forEach>
				</form>
			</div>
			<div class="pagination" style="margin-left:8px;">
				<p:pager url="${realPath}/groupManage/${gid}/manage/group_article?itemId=${itemId}" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript">
		var page = "${page}";
	</script>
	<script type="text/javascript" src="${realPath}/js/group.manage.article.js"></script>
</body>
</html>