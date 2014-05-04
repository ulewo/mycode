<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝头像管理-有乐窝</title>
<%@ include file="../common/easyui_common.jsp" %>
<link rel="stylesheet" type="text/css" href="${realPath}/css/jquery.Jcrop.css?version=2.5">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.public.css?version=2.5">
<script type="text/javascript">
var groupManageIcon = {};
groupManageIcon.gid  = "${group.gid}";
</script>
</head>
<body>
	<div  style="margin-left:10px;">
		<div class="form_are">
			<div style="float:left;width:60px;"><img src="${realPath}/upload/${group.groupIcon}?<%=new Date()%>" width="50"> </div>
			<div style="float:left;width:330px;">
				<iframe src="${realPath}/common/iconUpload.jsp" id="iframupload" height="30" width="310" frameborder="0"></iframe>
			</div>
			<div style="float:left;width:250px;padding-top:10px;color:#999999">图片大小不能超过1M,尺寸不能超过1024</div>
			<div class="clear"></div>
		</div>
		<div id="imgarea" style="margin-top:5px;">
			<div class="cutarea" id="picture_original">
				<img src="" id="myImg"/>
			</div>
			<div id="ferret" class="ferret" style="border:1px solid #D4D4D4">
				<div id="picture_200"></div>
			</div>
			<div style="clear:left;"></div>
		</div>
		<div class="saveset" style="text-align:left;margin-top:5px;">
			<a href="javascript:saveImg()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存头像</a>
		</div>
	</div>
	<script type="text/javascript" src="${realPath}/js/jquery.Jcrop.min.js"></script>
	<script type="text/javascript" src="${realPath}/js/jQuery.UtrialAvatarCutter.js"></script>
	<script type="text/javascript" src="${realPath}/js/group.manage.groupicon.js"></script>
</body>
</html>