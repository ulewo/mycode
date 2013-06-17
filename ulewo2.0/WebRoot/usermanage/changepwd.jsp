<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.manage.userinfo.css">
<style type="text/css">
#selected2 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="right_top_m">
					<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt;修改密码
					<c:if test="${blogitem!=null}">
						&gt;&gt;<a href="">${blogitem.itemName}</a>
					</c:if>
			</div>
			<form id="pwd_form">
			<div class="info_item">
				<div class="info_item_tit">旧密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="oldpwd" id="oldpwd"/></div>
			 	<div class="info_item_info">请输入旧密码</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
				<div class="info_item_tit">新密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="newpwd" id="newpwd"/></div>
			 	<div class="info_item_info">密码长度6-16位字符，由数字、字母组成</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
				<div class="info_item_tit">再次输入新密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="anewpwd" id="anewpwd"/></div>
			 		<div class="info_item_info">请再次输入新密码</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit"></div>
			 	<div class="info_item_text subbtn">
			 		<a href="javascript:void(0)" class="btn" id="saveBtn">修改密码</a>
			 		<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
			 		<div class="clear"></div>
			 	</div>
			 	<div class="clear"></div>
			</div>
			</form>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.manage.changepwd.js"></script>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>