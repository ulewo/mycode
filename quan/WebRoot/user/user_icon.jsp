<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友吧中国</title>
<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
   <link rel="stylesheet" type="text/css" href="../imgcutcss/imgareaselect-default.css" />
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/manage.bassetlogo.js"></script>
<script type="text/javascript" src="../js/userspace.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="../imgcutscripts/jquery.imgareaselect.pack.js"></script>
<style type="text/css">
	#sel_left1 a{background:url(../images/bg.gif) 0px -85px;}
	#sel_left1 a:hover{text-decoration:none;}
	a.sel2{background:#5A5A5A;color:white;font-weight:bold;}
	.sys_logo{display:none;}
</style>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
	<jsp:include page="menue.jsp"></jsp:include>
	<div class="user_main">
	<div class="left">
		<jsp:include page="user_left.jsp"></jsp:include>
	</div>
	<div class="right">
			<input type="hidden" id="x1" name="x1" value="" />
			<input type="hidden" id="y1" name="y1" value="" />
			<input type="hidden" id="w" name="y2" value="" />
			<input type="hidden" id="h" name="y2" value="" />
			<input type="hidden" name="groupicon" id="groupicon" value="${user.userLittleIcon}">
			<!-- <div>
				<div class="tit_con_user" style="cursor:pointer;"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" checked="checked" id="cus_img"></div><div class="tit_con_tit">自定义头像</div></div>
				<div class="tit_con_user" style="margin-top:10px;cursor:pointer;"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" id="sys_img"></div><div class="tit_con_tit">推荐头像</div></div>
				<div style="clear:left;"></div>
			</div>
			 -->
			<div class="form_are">
				<iframe src="../imageUpload/userAvatarUpload.jsp" id="iframupload" height="30" width="310" frameborder="0"></iframe>
			</div>
			<div class="imgCut" id="imgCut">
				<div class="cutarea">
					<img id="photo" src="../upload/useravatar.jpg">
				</div>
				<div id="ferret" class="ferret">
					<div id="preview"><img src="../upload/useravatar.jpg" style="position: relative;" /></div>
				</div>
				<div style="clear:left;"></div>
			</div>
			
			<div class="sys_logo" id="sys_logo">
				
			</div>
			<div class="saveset">
				<div class="bbtn1" id="save"><a href="javascript:saveUserIcon('${userId}',0)">保存设置</a></div>
				<div id="load"><img src="../images/loading.gif"></div>
			</div>
	</div>
	<div class="clear"></div>
	</div>
</div>	
<div class="foot"></div>
</body>
</html>