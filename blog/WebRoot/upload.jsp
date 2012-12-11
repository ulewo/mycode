<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<style>
	body{margin:0px;padding:0px;width:150px;height:20px;}
	form{margin:0px;padding:0px;}
</style>
</head>
<body>
	<form action="imageUpload" enctype="multipart/form-data" method="post"  id="myform">
			<input type="file" id="upload" name="file" onchange="subForm()"  /> 
	</form>
	<script>
	$(function(){
		if("${result}"=="success"){
			parent.initImg("${imgUrl}");
		}
	})
	function subForm() {
		document.getElementById("myform").submit();
	}
	</script>
</body>
</html>
