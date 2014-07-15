﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>有乐窝土豪排行榜TOP50-有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<link rel="stylesheet" type="text/css" href="${realPath}/css/index.css?version=2.5">
<script type="text/javascript" src="${realPath}/js/usermark.js?version=2.5"></script>
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="remaktit">有乐窝土豪排行榜TOP50</div>
		<div id="remarkist">
		</div>
		<div id="loading" ><img src="${realPath}/images/load.gif" ></div>
  </div>
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
   <script type="text/javascript">
	$(function(){loadAllUser();});
  </script>
</body>
</html>