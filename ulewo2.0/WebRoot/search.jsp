<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全站搜索-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/search.css">
<%
	    String keyWord = String.valueOf(request.getAttribute("keyword"));
	    keyWord = URLEncoder.encode(URLEncoder.encode(keyWord, "utf-8"), "utf-8");
	    String type = String.valueOf(request.getAttribute("type"));
	   String url =  "search?type="+type+"&q="+keyWord;
%>
</head>
<body>
	<%@ include file="common/head_nosearch.jsp" %>
	<div class="main">
		<div class="search">
			<input type="button" class="head_search_btn" value="搜索一下" id="searchBtn">
			<input type="text" class="head_search_input" value="${keyword}" id="searchInput_search" name="keyWord">
			<div class="clear"></div>
		</div>
		<div class="searchtype">
			<ul>
				<li style="margin-left:5px;"><a href="${realPath}/search?type=group&q=<%=keyWord %>" <c:if test="${type!='blog'}">class="cur_type"</c:if>>讨&nbsp;论</a></li>
				<li><a href="${realPath}/search?type=blog&q=<%=keyWord %>" <c:if test="${type=='blog'}">class="cur_type"</c:if>>博&nbsp;客</a></li>
			</ul>
		</div>
		<c:if test="${type=='blog'}">
			<div id="bloglist">
				<c:forEach var="blog" items="${result.list}">
					<div class="item">
						<div class="title"><a href="${realPath}/user/${blog.userId}/blog/${blog.id}" target="_blank">${blog.title}</a></div>
						<div class="url">http://ulewo.com/user/${blog.userId}/blog/${blog.id}</div>
						<div class="summary">${blog.summary}</div>
						<div class="info">
							<span>${blog.userName}</span>
							<span>${blog.postTime}</span>
							<span>阅读(${blog.readCount})</span>
							<span>回复(${blog.reCount})</span>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty result.list}">
					<div class="noinfo">没有搜索到任何结果！</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${type!='blog'}">
			<div id="bloglist">
				<c:forEach var="article" items="${result.list}">
					<div class="item">
						<div class="title"><a href="${realPath}/group/${article.gid}/topic/${article.id}" target="_blank">${article.title}</a></div>
						<div class="url">http://ulewo.com/group/${article.gid}/topic/${article.id}</div>
						<div class="summary">${article.summary}</div>
						<div class="info">
							<span>${article.authorName}</span>
							<span>${article.postTime}</span>
							<span>阅读(${article.readNumber})</span>
							<span>回复(${article.reNumber})</span>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty result.list}">
					<div class="noinfo">没有搜索到任何结果！</div>
				</c:if>
			</div>
		</c:if>
		<div class="pagination">
			<p:pager url="<%=url%>" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
		</div>
  </div>
  <script type="text/javascript">
  	var searchType = "${type}";
  </script>
  <script type="text/javascript" src="${realPath}/js/search.js"></script>
  <%@ include file="common/foot.jsp" %>
  <div class="righ_ad">
		<script type="text/javascript">
			/*160*600，创建于2013-7-10*/
			var cpro_id = "u1317726";
			</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  </div>
</body>
</html>