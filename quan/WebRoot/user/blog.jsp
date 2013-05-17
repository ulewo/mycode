<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
		#sel_left6 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left6 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <%@ include file="../common/head.jsp" %>
   <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
  	<div class="right">
  		<div class="navPath">
  			<a href="userInfo.jspx?userId=${userId}">空间</a>&nbsp;&gt;&gt;&nbsp;<a href="blog.jspx?userId=${userId}">博客</a>
  			<c:if test="${blogItem!=null}">&nbsp;&gt;&gt;&nbsp;${blogItem.itemName}</c:if>
  		</div>
  		<div>
  		<c:forEach var="blog" items="${blogList}">
  			<div class="blog_link">
  				<a href="blogdetail.jspx?id=${blog.id}" target="_blank">${blog.title}</a><span>${blog.reCount}/${blog.readCount}</span>
  			</div>
		 </c:forEach>
  		</div>
  		<div  class="pagination" style="margin-top:20px;">
			<p:pager url="blog.jspx?userId=${userId}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
  	 	</div>
 	</div>
 	<div style="clear:left;"></div>
 	</div>
    <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
