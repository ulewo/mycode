<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet"  href="../css/public.css" type="text/css"  />
	<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
	<style>
		body {width:200px;margin:0px;text-align:left;padding:0px;}
		form{margin:0px;padding:0px;}
	</style>
	<script type="text/javascript">
		var imageUrl = "${savePath}";
		var message = "${message}";
		var result = "${result}";
		$(function(){
			if(result=="fail"){
				alert(message);
				return;
			}
			if(imageUrl!=""){
				parent.showImg(imageUrl);
			}
		});
		function uploadImg(){
			var imageName = $("#upload").val();
			var type = imageName.substring(imageName.length-4,imageName.length);
			if(imageName==""){
				alert("请选择文件");
				return false;
			}else if(type!=".jpg"&&type!=".JPG"&&type!=".png"&&type!=".PNG"&&type!=".gif"&&type!=".GIF"&&type!=".bmp"&&type!=".BMP"){
				alert("请选择图片文件格式");
				return false;
			}else{
				$("#loadingimage").show();
				$("#myform").submit();
			}
		}
	</script>
</head>
<body>
<form action="${realPath}/iconUpload.action" enctype="multipart/form-data" method="post" style="margin:0px;padding:0px;" id="myform">
	<table width="100%" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;">
		<tr>
			<td><input type="file" name="file" class="fileUpload" style="width:150px" id="upload" onchange="uploadImg()"></td>
			<td><span id="loadingimage" style="display:none;"><img src="${realPath}/images/load.gif" style="float:left;"></span></td>
		</tr>
	</table>
</form>
</body>
</html>