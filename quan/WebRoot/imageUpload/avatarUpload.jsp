<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet"  href="../css/public.css" type="text/css"  />
	<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
	<style>
		body {width:300px;margin:0px auto;text-align:left;}
	</style>
</head>
<script type="text/javascript">
	$(function(){
		$("#subbutton").css("display","inline");
		$("#loadingimage").css("display","none");
		var imageName = document.getElementById("img").value;
		if(imageName!=""){
			parent.getImage(imageName);
		}
		
		$("#sub").click(function(){
			
			var imageName = $("#upload").val();
			var type = imageName.substring(imageName.length-4,imageName.length);
			if(imageName==""){
				alert("请选择文件");
				return false;
			}else if(type!=".jpg"&&type!=".JPG"&&type!=".png"&&type!=".PNG"&&type!=".gif"&&type!=".GIF"&&type!=".bmp"&&type!=".BMP"){
				alert("请选择图片文件格式");
				return false;
			}else{
				$("#subbutton").css("display","none");
				$("#loadingimage").css("display","inline");
				$("#myform").submit();
			}
		});
	});
</script>
<body onload="getImage()">
<input type="hidden" id="img" value="${uploadFileName}" />
	<form action="avatarUpload.jspx" enctype="multipart/form-data" method="post" style="margin:0px;padding:0px;" id="myform">
		<table cellpadding="0" cellspacing="0" width="100%">
			<tr>
			<td><input type="file" name="upload" id="upload" style="width:200px;"></td>
			<td><div id="subbutton" class="sbtn"><a href="####"  id="sub" onFocus="this.blur()" >上传</a></div></td>
			<td><span id="loadingimage"><img src="../images/loading.gif" width="20"></span></td>
			</tr>
		</table>
	</form>
</body>
</html>