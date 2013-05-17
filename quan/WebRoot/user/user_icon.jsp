<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
<LINK href="../css/jquery.Jcrop.css" type="text/css" rel="Stylesheet" media="screen">
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/userspace.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/plugins/iframeTools.js"></script>

<script type="text/javascript" src="../js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="../js/jQuery.UtrialAvatarCutter.js"></script>

<style type="text/css">
	#selected3 a{background:#FFFFFF;}
</style>
<script type="text/javascript">
var cutter = new jQuery.UtrialAvatarCutter(
		{
			//主图片所在容器ID
			content : "picture_original",
			
			//缩略图配置,ID:所在容器ID;width,height:缩略图大小
			purviews : [{id:"picture_200",width:80,height:80}],
			
			//选择器默认大小
			selector : {width:80,height:80}
		}
	);
</script>
</head>
<body>
<%@ include file="../common/head.jsp" %>
<div class="main">
	   <div class="left">
	  	<jsp:include page="menue.jsp"></jsp:include>
	   </div>
	   <div class="right">
		  	<div class="navPath"><a href="userInfo.jspx?userId=${user.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;修改头像</div>
			<!-- <div>
				<div class="tit_con_user" style="cursor:pointer;"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" checked="checked" id="cus_img"></div><div class="tit_con_tit">自定义头像</div></div>
				<div class="tit_con_user" style="margin-top:10px;cursor:pointer;"><div class="tit_con_radio"><input type="radio" name="iconType" class="iconType" id="sys_img"></div><div class="tit_con_tit">推荐头像</div></div>
				<div style="clear:left;"></div>
			</div>
			 -->
			<div class="form_are">
				<iframe src="../imageUpload/userAvatarUpload.jsp" id="iframupload" height="30" width="310" frameborder="0"></iframe>
			</div>
			<div>
				<div class="cutarea" id="picture_original">
					<img src="" id="myImg"/>
				</div>
				<div id="ferret" class="ferret">
					<div id="picture_200"></div>
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
   <jsp:include page="../common/foot.jsp"/>
</body>
</html>