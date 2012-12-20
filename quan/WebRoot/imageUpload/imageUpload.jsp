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
		body {width:800px;margin:0px auto;text-align:left;}
		.fileUpload{width:100%;}
		#uploadTab tr{height:30px;}
	</style>
</head>
<c:if test="${message=='isBig'}">
<script type="text/javascript">
	alert("图片上传错误，图片大小不能超过1M");
</script>
</c:if>
<c:if test="${message=='error'}">
<script type="text/javascript">
	alert("图片上传错误");
</script>
</c:if>
<script type="text/javascript">
	$(function(){
		$("#subbutton").css("display","inline");
		$("#loadingimage").css("display","none");
		var imageName = document.getElementById("img").value;
		if(imageName!=""){
			parent.getImage(imageName);
		}
		
		$("#sub").click(function(){
			var imageInputs = $(".fileUpload");
			var num = 0;
			for(var i = 0;i<imageInputs.length;i++){
				var imageName = imageInputs.eq(i).val();
				if(imageName==""){
					alert("请选择第"+(i+1)+"张图片");
					return;
				}
				var type = imageName.substring(imageName.length-4,imageName.length);
				if(type!=".jpg"&&type!=".JPG"&&type!=".png"&&type!=".PNG"&&type!=".gif"&&type!=".GIF"&&type!=".bmp"&&type!=".BMP"){
					alert("请正确选择第"+(i+1)+"张图片,只能是.jpg,.png,.gif,.bmp");
					return false;
				}
			}
			$("#subbutton").css("display","none");
			$("#loadingimage").css("display","inline");
			$("#myform").submit();
		});
	});
	
	function addFileInput(){
		if($(".fileUpload").length<6){
			$("<tr><td><input type='file' name='upload' class='fileUpload'></td><td colspan='2'><a href='####' name='cancel' ><img src='../images/001_02.png' border='0'></a></td></tr>").appendTo($("#uploadTab"));
			bindListener();
		}else{
			alert("每次最多只能上传5张图片");
		}
		
	}
	
	function bindListener(){
		$("a[name=cancel]").unbind().click(function(){
			$(this).parent().parent().remove();
		})
	}
	
</script>
<body>
<input type="hidden" id="img" value="${resultFileName}" />
	<form action="imageUpload.jspx" enctype="multipart/form-data" method="post" style="margin:0px;padding:0px;" id="myform">
		<table cellpadding="0" cellspacing="0" width="40%" id="uploadTab">
			<tr>
				<td><input type="file" name="upload" class="fileUpload"></td>
				<td><a href="javascript:addFileInput()"><img src="../images/001_01.png" border="0" title="继续添加"></a></td>
				<td style="padding-left:10px;"><span id="subbutton" class="smallButon" ><a href="####"  id="sub" onFocus="this.blur()" >上传</a></span></td>
			</tr>
		</table>
		<span id="loadingimage"><img src="../images/loading.gif" width="20"></span>
	</form>
</body>
</html>