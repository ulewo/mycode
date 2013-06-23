<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改头像-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/jquery.Jcrop.css">
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.manage.public.css">
<style type="text/css">
#selected3 a{background:#ffffff;color:#333333;font-weight:bold;}
.saveset{margin-top:10px;}
.form_are{margin-top:10px;}
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
					<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt;修改个人信息
					<c:if test="${blogitem!=null}">
						&gt;&gt;<a href="">${blogitem.itemName}</a>
					</c:if>
			</div>
			<div>
				<div class="form_are">
					<iframe src="${realPath}/common/imgupload.jsp" id="iframupload" height="30" width="310" frameborder="0"></iframe>
				</div>
				<div id="imgarea">
					<div class="cutarea" id="picture_original">
						<img src="" id="myImg"/>
					</div>
					<div id="ferret" class="ferret">
						<div id="picture_200"></div>
					</div>
					<div style="clear:left;"></div>
				</div>
				<div class="saveset">
					<a href="javascript:void(0)" class="btn" id="saveBtn" style="float:left;margin-top:5px;">保存设置</a>
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>	
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/jquery.Jcrop.min.js"></script>
	<script type="text/javascript" src="${realPath}/js/jQuery.UtrialAvatarCutter.js"></script>
	<script type="text/javascript" src="${realPath}/js/user.manage.usericon.js"></script>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>