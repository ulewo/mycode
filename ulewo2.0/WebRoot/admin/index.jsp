﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理文章-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/admin.index.css">
<style type="text/css">
#selected1 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<form id="myform" action="${realPath}/admin/weekHot.action" method="post">
				
			</form>
			<div class="articleop">
				<a href="javascript:void(0)" class="btn" id="deleteArticle">批量删除</a>
				<a href="javascript:void(0)" class="btn" id="weekHotBtn">生成每周热点</a>
				<input type="text" id="keyword">
				<a href="javascript:void(0)" class="btn" id="searchBtn">搜索</a>
			</div>
			<div class="article_list" id="article_list">
				
			</div>
			<div id="pager" class="pagination" style="margin-top:10px;"></div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript" src="${realPath}/js/admin.index.js"></script>
</body>
</html>