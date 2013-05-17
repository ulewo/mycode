<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ page import="com.lhl.entity.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>搜索 --有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="css/moretalk.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/talk.js"></script>
	<script type="text/javascript" src="js/moretalk.js"></script>
	<script type="text/javascript" src="js/emotion.data.js"></script>
	<style type="text/css">
	.main .itemcon{width:950px}
	</style>
  </head>
  <body>
   <%@ include file="common/head.jsp" %>
  <div class="main">
		<div class="talktit">大家都在吐槽什么？</div>
		<div id="talklist"></div>
		<a href="javascript:void(0)" id="loadmorebtn">加载更多</a>
		<div id="loading"><img src="images/load.gif"></div>
  </div>
  <jsp:include page="common/foot.jsp"/>
  </body>
</html>
