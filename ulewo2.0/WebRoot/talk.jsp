<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>吐槽-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/moretalk.css">
<script type="text/javascript" src="${realPath}/js/talk.js"></script>
<script type="text/javascript" src="${realPath}/js/moretalk.js"></script>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="talktit">大家都在吐槽什么？</div>
		<div id="talklist">
		</div>
  		<a href="javascript:void(0)" id="loadmorebtn">加载更多</a>
		<div id="loading" ><img src="${realPath}/images/load.gif" ></div>
  </div>
  <%@ include file="../common/foot.jsp" %>
</body>
</html>