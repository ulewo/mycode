﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="../css/group.css" type="text/css"  />
<title>Justlearning 学习，生活，娱乐</title>
	<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/addarticle.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
</head>
<body>
	<div><img src="../images/error.gif"></div>
	<div>
		<c:if test="${errMsg!=null}">
			${errMsg}
		</c:if>
		<c:if test="${errMsg==null}">
			session失效或者未登陆
		</c:if>
	</div>
	<div><a href="../index.jspx">返回首页</a></div>
</body>
</html>