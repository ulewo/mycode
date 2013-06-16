<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<script type="text/javascript" src="js/jquery.min.js"></script>
<!-- <script type="text/javascript" src="js/index.js"></script> -->
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/talk.js"></script>
<script type="text/javascript" src="js/emotion.data.js"></script>
<link rel="stylesheet" type="text/css" href="css/public.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/index_new.css">
<link rel="stylesheet" type="text/css" href="css/square.css">
</head>
<body>
	 <%@ include file="common/head.jsp" %>
  	<div class="main" id="container">
  		<c:forEach var="article" items="${list}">
	  		<div class="single">
	  			<div class="single_top"></div>
	  			<div class="single_center">
		  			<div class="single_tit"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
		  			<c:if test="${article.image!=null&&article.image!=''}">
		  				<div class="single_img"><img src="upload/${article.image}"/></div>
		  			</c:if>
		  			<div class="single_con">${article.summary}</div>
		  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">()</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
	  			</div>
	  			<div class="single_bottom"></div>
	  		</div>
  		</c:forEach>
  		<div class="clear"></div>
  	</div>
  	 <jsp:include page="common/foot.jsp"/>
</body>
</html>