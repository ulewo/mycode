<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>给我留言</title>
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" type="text/css" href="css/note.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/note.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<style type="text/css">
.menue div a.note{background-position:0px -194px;}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
		<div class="main">
				<div class="pageArea" style="margin-top:0px;padding-top:20px;padding-right:20px;"></div>
				<div id="note_list"></div>
				<div class="pageArea" style="margin-top:0px;padding-top:20px;padding-right:20px;"></div>
				<div class="rearticle_form">
					<input type="text" id="userName" value="姓名" style="color:#666666"><br>
					<textarea rows="5" cols="20" id="content" style="color:#666666">请输入你想说的</textarea><br>
					<div id="subCon" class="subcon">
						<a href="javascript:void(0)" id='subBtn' class="colorbtn" onclick="addNote()">ok,发送</a>
						<img src='images/loading.gif' id="load" style="display:none;">
					</div>
				</div>
				
		</div>
		<div class="foot"></div>
</body>
<script type="text/javascript">
	$(function(){
		loadNotes(1);
		$("#userName").bind("blur",setNameInfo).bind("focus",clearNameInfo);
		$("#content").bind("blur",setContentInfo).bind("focus",clearContentInfo);
	});
</script>
</html>