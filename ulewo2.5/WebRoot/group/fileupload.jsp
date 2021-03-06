<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<style>
		body {width:200px;margin:0px;text-align:left;padding:0px;}
		form{margin:0px;padding:0px;}
	</style>
	<script type="text/javascript">
		var result = "${result}";
		var savePath = "${savePath}";
		var message = "${message}";
		$(function(){
			if(result=="fail"){
				alert(message);
				return;
			}
			if(savePath!=""){
				parent.showFile(savePath);
			}
		});
		function uploadImg(){
			var imageName = $("#upload").val();
			var type = imageName.substring(imageName.length-4,imageName.length);
			if(imageName==""){
				alert("请选择文件");
				return false;
			}else if(type!=".rar"&&type!=".RAR"&&type!=".ZIP"&&type!=".zip"){
				alert("请选择压缩文件");
				return false;
			}else{
				var fileName = imageName.substring(imageName.lastIndexOf("\\")+1);
				parent.setFileName(fileName);
				$("#loadingimage").show();
				$("#myform").submit();
			}
		}
	</script>
</head>
<body>
<form action="${realPath}/group/fileupload" enctype="multipart/form-data" method="post" style="margin:0px;padding:0px;" id="myform">
	<table width="100%" cellpadding="0" cellspacing="0" style="margin:0px;padding:0px;">
		<tr>
			<td><input type="file" name="file" class="fileUpload" style="width:150px" id="upload" onchange="uploadImg()"></td>
			<td><span id="loadingimage" style="display:none;"><img src="${realPath}/images/load.gif" style="float:left;"></span></td>
		</tr>
	</table>
</form>
</body>
</html>