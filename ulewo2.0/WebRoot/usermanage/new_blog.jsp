<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发表博文-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.manage.newblog.css">
<style type="text/css">
#selected4 a{background:#ffffff;color:#333333;font-weight:bold;}fff}
</style>
<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
	//-->
</script>
<script type="text/javascript" src="${realPath}ueditor/editor_config_blog.js"></script>
<script type="text/javascript" src="${realPath}ueditor/editor.js"></script>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="right_top_m">
					<a href="">空间</a>&gt;&gt;<a href="blog">博客</a>
					<c:if test="${blogitem!=null}">
						&gt;&gt;<a href="">${blogitem.itemName}</a>
					</c:if>
			</div>
			<div class="form_sub_btn">
				<a href="" class="btn">发表博文</a>
			</div>
			<div class="form_tit">
				<span class="form_tit_t">标题</span>
				（<span class="form_tit_x">必填</span>）
			</div>
			<div class="form_editinput">
				<div class="form_title_input">
					<input type="text"/>
				</div>
				<div class="form_title_info">存放在</div>
				<div class="form_title_select">
					<select>
						<option>阿萨德发射点发</option>
						<option>阿萨德发射点发</option>
						<option>阿萨德发射点发</option>
					</select>
					<a href="">分类管理</a>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form_tit">
				<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
				（<span class="form_tit_x">选填</span>）
			</div>
			<div class="form_editinput">
			 	<div class="form_title_input"><input class="editinput" type="password" name="address" value="${userVo.address}"/></div>
			 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
			 	<div class="clear"></div>
			</div>
			<div class="form_tit">
				<span class="form_tit_t">内容</span>
				（<span class="form_tit_x">必填</span>）
			</div>
			<div class="form_editcontent" id="editor">
			
			</div>
			<div class="form_sub_btn">
				<a href="" class="btn">发表博文</a>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
<script type="text/javascript">
	var isHaveImg = false;
    var editor = new UE.ui.Editor();
    editor.render("editor");
    editor.ready(function(){
        editor.setContent("");
    });
    
    function submitForm(){
		var title = $("#title").val();
		if(title.trim()==""){
			alert("标题不能为空");
			return;
		}else{
			title = title.replaceHtml();
			$("#title").val(title);
		}
		
		var keyWord =  $("#keyWord").val();
		if(keyWord.trim()==""){
			alert("关键字不能为空");
			return;
		}else{
			keyWord = keyWord.replaceHtml();
			$("#keyWord").val(keyWord);
		}
		if(editor.getContentTxt().trim()==""){
			alert("内容不能为空");
			return ;
		}else{
			$("#content").val(editor.getContent());
		}
		$(".sendBtn").hide();
		$(".loading").show();
		$("#subForm").submit();
	}
    
    function initImg(imageUrls){
    }
</script>
</html>