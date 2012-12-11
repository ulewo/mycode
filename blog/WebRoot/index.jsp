<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<c:set var="myparam" value=""></c:set>
<c:if test="${itemId!=0}">
	<c:set var="myparam" value="article?itemId=${itemId}"></c:set>
</c:if>
<c:if test="${time!=null}">
	<c:set var="myparam" value="article?time=${time}"></c:set>
</c:if>
<style type="text/css">
.menue div a.article{background-position:0px -39px;}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
		<div class="main">
			<div class="left">
				<div class="pageArea" style="padding-right:60px;">
					<p:pager url="${myparam}" page="${page}" pageTotal = "${pageTotal }"></p:pager>
				</div>
				<c:if test="${empty list}">
					<div class="post">
						<div class="description">
							该目录下没有文章哦，亲
						</div>
					</div>
				</c:if>
				<c:forEach var="article" items="${list}">
						<div class="post">
							<h2><a href="detail?id=${article.id}">${article.title}</a></h2>
							<div>
								<span class="date">${fn:substring(article.postTime,0,10)}</span>
								<c:if test="${!empty article.tagList}">
									<span class="categories">标签:
										<c:forEach var="tag" items="${article.tagList}">
											${tag}
										</c:forEach>
									</span>
								</c:if>
								<div class="clear_float"></div>
							</div>
							<div class="description">
								${article.content}
							</div>
							<p class="comments">回复：<a href="detail?id=${article.id}">${article.reCount}</a>   <span>|</span>  浏览：<span class="readCount">${article.readCount}</span> <span>|</span> <a href="detail?id=${article.id}">继续阅读....</a></p>
						</div>
				</c:forEach>
				<div class="pageArea" style="padding-right:60px;">
					<p:pager url="${myparam}" page="${page}" pageTotal = "${pageTotal }"></p:pager>
				</div>
			</div>
			<div class="right">
				<jsp:include page="right.jsp"></jsp:include>
			</div>
			<div class="clear_float"></div>
		</div>
		<div class="foot"></div>
</body>
<script type="text/javascript">
	var itemId = "${param.itemId}";
	var time = "${param.time}";
	$(function(){
		loadItem(itemId);
		loadRecord(time);
	})
</script>
</html>