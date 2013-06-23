<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝公告管理-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.editgroup.css">
<style type="text/css">
#selected3 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<form id="group_info">
			<div class="form_tit">
				<span class="form_tit_t">公告</span>
				<span class="form_tit_x">（公告不能超过500字符）</span>
			</div>
			<div class="form_editcontent" id="editor">
				<textarea name="groupNotice" id="group_notice">${group.groupNotice}</textarea>
			</div>
			<div class="form_sub_btn">
				<a href="javascript:void(0)" class="btn" id="editBtn">确认修改</a>
				<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
			</div>
			</form>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript" src="${realPath}/js/group.manage.editnotice.js"></script>
</body>
</html>