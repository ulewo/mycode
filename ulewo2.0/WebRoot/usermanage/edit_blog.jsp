<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新博文-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.manage.newblog.css">
<style type="text/css">
</style>
<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
	//-->
</script>
<script type="text/javascript" src="${realPath}/ueditor/editor_config_blog.js"></script>
<script type="text/javascript" src="${realPath}/ueditor/editor.js"></script>
<script type="text/javascript" src="${realPath}/js/user.manage.newblog.js"></script>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="right_top_m">
					<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt;<a href="${realPath}/user/${user.userId}/blog">博客</a>
			</div>
			<form id="blogform">
				<div class="form_tit">
					<span class="form_tit_t">标题</span>
					（<span class="form_tit_x">必填</span>）
				</div>
				<div class="form_editinput">
					<div class="form_title_input">
						<input type="text" name="title" id="title" value="${blog.title}"/>
					</div>
					<div class="form_title_info">存放在</div>
					<div class="form_title_select">
						<select name="itemId" id="itemId">
							<option value=""></option>
							<option value="0">全部分类</option>
							<c:forEach var="item" items="${itemList}">
								<option  <c:if test="${blog.itemId==item.id}">selected="selected"</c:if> value="${item.id}">${item.itemName}</option>
							</c:forEach>
						</select>
						<a href="${realPath}/manage/blog_item">分类管理</a>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form_tit">
					<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
					（<span class="form_tit_x">选填</span>）
				</div>
				<div class="form_editinput">
				 	<div class="form_title_input"><input class="editinput" type="text" name="keyword" id="keyword" value="${blog.keyWord}"/></div>
				 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
				 	<div class="clear"></div>
				</div>
				<div class="form_tit">
					<span class="form_tit_t">内容</span>
					（<span class="form_tit_x">必填</span>）
				</div>
				<div class="form_editcontent" id="editor">
				
				</div>
				<input type="hidden" id="content" name="content">
				<div class="form_sub_btn">
					<a href="javascript:void(0)" class="btn" id="saveBtn">发表博文</a>
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
			 		<div class="clear"></div>
				</div>
				<input type="hidden" name="id" value="${blog.id}">
			</form>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript">
		var editor;
		editor = new UE.ui.Editor();
		editor.render("editor");
		editor.ready(function(){
	    editor.setContent('${blog.content}');
	});
	</script>
</body>
</html>