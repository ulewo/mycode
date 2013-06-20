<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>创建窝窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.editgroup.css">
<style type="text/css">
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
			<div style="border:1px solid #ABADB3;margin-top:10px;padding:10px;">
			<div style="font-size:16px;font-weight:bold;">创建窝窝</div>
				<form id="group_info">
				<div class="form_tit">
					<span class="form_tit_t">窝窝名称</span>
					<span class="form_tit_x">（名称不能超过50字符）</span>
				</div>
				<div class="form_editinput">
					<div class="form_title_input">
						<input type="text" name="groupName" id="group_name" value="${group.groupName}"/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form_tit">
					<span class="form_tit_t">简介</span>
					<span class="form_tit_x">（简介不能超过500字符）</span>
				</div>
				<div class="form_editcontent" id="editor">
					<textarea name="groupDesc" id="group_desc">${group.groupDesc}</textarea>
				</div>
				<div class="form_tit">
					<div class="form_tit_radio">加入权限？</div>
					<div class="form_con_radio">
						<input type="radio" name="joinPerm" value="Y"  checked="checked"/>允许任何人加入
						<input type="radio" name="joinPerm" value="N" />需经我审批才能加入
					</div>
					<div class="clear"></div>
				</div>
				<div class="form_tit">
				<div class="form_tit_radio">发帖权限？</div>
				<div class="form_con_radio">
					<input type="radio" name="postPerm" value="A" checked="checked" />任何人可以发帖
					<input type="radio" name="postPerm" value="M" />窝窝成员才能发帖
				</div>
				<div class="clear"></div>
			</div>
			<div class="form_sub_btn">
				<a href="javascript:void(0)" class="btn" id="editBtn">创建窝窝</a>
				<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
			</div>
			</form>
			</div>
		</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript" src="${realPath}/js/group.manage.creategroup.js"></script>
</body>
</html>