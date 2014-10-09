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
<link rel="stylesheet" type="text/css" href="${realPath}/css/search.css?version=2.5">
<%
	String keyWord = String.valueOf(request.getAttribute("keyWord"));
	keyWord = URLEncoder.encode(URLEncoder.encode(keyWord, "utf-8"), "utf-8");
	String type = String.valueOf(request.getAttribute("type"));
	String url =  "search?type="+type+"&q="+keyWord;
%>
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="searchtype">
			<ul>
				<li style="margin-left:5px;"><a href="${realPath}/search?type=T&q=<%=keyWord %>" <c:if test="${type=='T'}">class="cur_type"</c:if>>讨&nbsp;论</a></li>
				<li><a href="${realPath}/search?type=B&q=<%=keyWord %>" <c:if test="${type=='B'}">class="cur_type"</c:if>>博&nbsp;客</a></li>
			</ul>
		</div>
		<c:if test="${type=='B'}">
			<div>
				<c:forEach var="blog" items="${result}">
					<div class="item">
						<div class="title"><a href="${realPath}/user/${blog.extendId}/blog/${blog.id}" target="_blank">${blog.title}</a></div>
						<div class="url">http://ulewo.com/user/${blog.userId}/blog/${blog.id}</div>
						<div class="summary">${blog.summary}</div>
						<div class="info">
							<span><a href="${realPath}/user/${blog.extendId}" target="_blank">${blog.userName}</a></span>
							<span>${blog.createTime}</span>
							<span>阅读(${blog.readCount})</span>
							<span>回复(${blog.commentCount})</span>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty result}">
					<div class="noinfo">没有搜索到任何结果！</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${type=='T'}">
			<div>
				<c:forEach var="article" items="${result}">
					<div class="item">
						<div class="title"><a href="${realPath}/group/${article.extendId}/topic/${article.id}" target="_blank">${article.title}</a></div>
						<div class="url">http://ulewo.com/group/${article.extendId}/topic/${article.id}</div>
						<div class="summary">${article.summary}</div>
						<div class="info">
							<span><a href="${realPath}/user/${article.userId}" target="_blank">${article.userName}</a></span>
							<span>${article.createTime}</span>
							<span>阅读(${article.readCount})</span>
							<span>回复(${article.commentCount})</span>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty result}">
					<div class="noinfo">没有搜索到任何结果！</div>
				</c:if>
			</div>
		</c:if>
		<c:if test="${!empty result}">
		<div style="margin-top:10px;color:#D70003">搜索结果只展示最新的100条</div>
		</c:if>
  </div>
  <script type="text/javascript">
  	var searchType = "${type}";
  </script>
  <%@ include file="common/foot.jsp" %>
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