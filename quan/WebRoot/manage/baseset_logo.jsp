<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<style>
#selected2 a{background:#FFFFFF;}
</style>
<link rel="stylesheet"  href="../css/manage.group.css" type="text/css"  />
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/manage.bassetlogo.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/plugins/iframeTools.js"></script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="maincon">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
			<input type="hidden" name="groupicon" id="groupicon" value="${group.groupIcon}">
			<div class="logo_con" >
				<div class="logo_con_img"><img src="../upload/${group.groupIcon}" id="imgcon" width="60"></div>
				<div id="logo_con_info"></div>
			</div>
			<div class="tit_con"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" checked="checked" id="cus_img"></div><div class="tit_con_tit">自定义头像:</div></div>
			<div class="form_are">
					<iframe src="../imageUpload/avatarUpload.jsp" id="iframupload" height="30" width="310" frameborder="0"></iframe>
			</div>
			<div class="tit_con"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" id="sys_img"></div><div class="tit_con_tit">推荐头像:</div></div>
			<div class="sys_logo" id="sys_logo">
				
			</div>
			<div class="saveset">
				<div class="bbtn1" id="save"><a href="javascript:saveAvatar('${gid}')">保存设置</a></div>
				<div id="load"><img src="../images/loading.gif"></div>
			</div>
	</div>
	<div class="clear"></div>
</div>	
	<jsp:include page="../common/foot.jsp"></jsp:include>
</body>
</html>