<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = location.pathname.substr(0, location.pathname.lastIndexOf('/')) + '/'+"ueditor/";
	//-->
</script>
<link rel="stylesheet" type="text/css" href="css/admin.index.css" />
<style type="text/css">
a.lisel1{background:#E6E6E6;font-weight:bold;}
</style>
<script charset="utf-8" src="js/admin.js"></script>
<script charset="utf-8" src="js/jquery.min.js"></script>
<script type="text/javascript" src="ueditor/editor_config.js"></script>
<script type="text/javascript" src="ueditor/editor.js"></script>
<link rel="stylesheet" href="ueditor/themes/default/css/ueditor.css" />
</head>
<body>
	<div class="head">博客后台管理</div>
	<div class="main">
		<div class="admin_left">
			<jsp:include page="admin_menue.jsp"></jsp:include>
		</div>
		<div class="admin_right">
			<form action="adminArticle?method=admin_save" method="post" id="myform">
				<input type="hidden" value="${param.id}" name="id">
				<div class="addtr">
					<div class="addtit">标题：</div>
					<div class="addinput"><input type="text" name="title" id="title"></div>
				</div>
				<div class="addtr">
					<div class="addtit">分类：</div>
					<div id="item" class="addinput"></div>
				</div>
				<div class="addtr">
					<div class="addtit">关键字:</div>
					<div class="addinput"><input type="text" name="tag" id="tag"></div>
				</div>
				<div id="editor" style="text-align:left;">
				</div>
				<!-- 
				<div class="addtr">
					<div class="addtit">附件：</div>
					<div class="addinput"><iframe src="upload.jsp" width="300" height="25" frameborder="0" scrolling="no"></iframe></div>
				</div>
				 -->
				<div id="images" style="display:none;">
					<div id="imagecon"></div>
					<div style="clear:left;"></div>
				</div>
				<input type="hidden" id="content" name="content">
				<div>
					<input type="button" value="发布" onclick="subForm();">
				</div>
			</form>
		</div>
		<div class="clear_float"></div>
	</div>
</body>
<script>
        var id="${param.id}";
        var editor = new UE.ui.Editor();
        editor.render("editor");
        editor.ready(function(){
            editor.setContent("");
        })
        $(function(){
        	loadInfo(id);
        });
        
       function subForm(){
    	   if($("#title").val()==""){
    		   alert("标题不能为空");
    		   return;
    	   }
    	   if($("#tag").val()==""){
    		   alert("关键字不能为空");
    		   return;
    	   }
    	   if(editor.getContent()==""){
    		   alert("内容不能为空");
    		   return;
    	   }
    	  $("#content").val(editor.getContent());
    	  $("#myform").submit();
       }
</script>
</html>