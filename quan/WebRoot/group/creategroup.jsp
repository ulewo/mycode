<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<c:if test="${user==null}">
<script>
	document.location.href = "../index.jspx";
</script>	
</c:if>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "/quan/ueditor/";
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="../css/usergroup.css" type="text/css"  />
<title>Justlearning 学习，生活，娱乐</title>
<style>
	#se3{color:white;background:#8AC4FC;}
	#seleft2{background:#E8EDF1;color:red;}
</style>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor.js"></script>
<script>
<%
	String message = request.getParameter("message");
	if(message!=null&&!"".equals(message)){
	message = URLDecoder.decode(message,"GBK");
 %>
		alert("<%=message%>");
<%
	}
 %>
 </script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
		<div class="right">
			<div>
				<form action="createGroup.jspx" method="post" id="myform">
					<input type="hidden" id="content" name="groupDesc">
					<div class="partCon">
						<div class="formTile">群名称：</div>
						<div class="formCon" style="width:600px;"><input class="inputstyle" type="text" name="groupName" id="groupName"/> &nbsp;&nbsp;&nbsp;<span style="color:red;">长度不能超过20，一个中文占2字符，数字，字母占1个字符。</span> </div>
					</div>	
					<div>
						<div class="formTile">群简介：</div>
						<div id="editor" style="text-align:left;" class="formCon">
						</div>
						<div style="clear:left;"></div>
					</div>
					<div style="margin-top:10px;">
						<div><img src="" id="headiconImage" style="display:none;"></div>
					</div>	
					<div class="partCon">
						<div class="formTile" style="padding-top:8px;">群顶部图片：</div>
						<div class="formCon"><iframe src="../imageUpload/headIconupload.jsp" height="30" frameborder="0"></iframe></div>
					</div>
					<input type="hidden" name="groupHeadIcon" value="" id="headicon">
					<div style="padding-left:120px;text-align:left;"><img src="" id="iconImage" style="display:none;"></div>
					<div class="partCon" style="text-align:left;">
						<div class="formTile" style="padding-top:8px;">群图标：</div>
						<div class="formCon">
							<div style="width:200px;"><iframe src="../imageUpload/avatarUpload.jsp" height="30" frameborder="0"></iframe></div>
						</div>	
						<input type="hidden" name="groupIcon" value="" id="icon">
					</div>
					
					<div class="partCon">
						<div class="formTile">群权限：</div>
						<div class="formCon">
							<input type="radio" name="joinPerm" value ="O" checked />公开，允许任何人加入
							<input type="radio" name="joinPerm" value ="C" />不公开，需经我批准才能加入
						</div>
					</div>
					<div class="partCon">
						<div class="formTile">发帖权限：</div>
						<div class="formCon">
							<input type="radio" name="postPerm" value="A" checked />登陆用户可以发帖
							<input type="radio" name="postPerm" value="M" />只有群成员可以发帖
						</div>
					</div>
					<div style="width:100px;text-align:center;margin:0px auto">	
						<div class="bbtn1" >
								<a href="javascript:submitForm()" onFocus="this.blur()" >创建群组</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>	
<jsp:include page="../common/foot.jsp"/>
<script type="text/javascript">
window.UEDITOR_CONFIG.initialFrameWidth = 700;
var editor = new UE.ui.Editor();
editor.render("editor");
editor.ready(function(){
    editor.setContent('${group.groupDesc}');
});
function getImage(imageName){
	$("#iconImage").css("display","inline");
	$("#icon").val(imageName);
	$("#iconImage").attr("src","../upload/"+imageName);
}
function getheadIconImage(imageName){
	$("#headiconImage").css("display","inline");
	$("#headicon").val(imageName);
	$("#headiconImage").attr("src","../upload/"+imageName);
    getImageDimension("../upload/"+imageName,showImageDimensions);  
}
//通过图片路径获取图片大小
function getImageDimension (imgURL,loadHandler) {  
  var img = new Image();  
  img.onload = loadHandler;  
  img.src = imgURL;  
}  
function showImageDimensions () {  
	if(this.width>800) {  
	  $("#headiconImage").attr("width","800");
	}else{
	  $("#headiconImage").attr("width",this.width);
	}
} 

function submitForm(){
	var groupName = $("#groupName").val();
	if(groupName.trim()==""){
		alert("群组名称不能为空");
		$("#groupName").focus();
		return;
	}else if(groupName.realLength()>20){
		alert("群组名称不能超过10");
		$("#groupName").focus();
		return;
	}else{
		groupName = groupName.replaceHtml();
		$("#groupName").val(groupName);
	}
	if(editor.getContent()==""){
		alert("内容不能为空");
		return ;
	}else{
		$("#content").val(editor.getContent());
	}
	
	$("#myform").submit();
}
</script>
</body>
</html>