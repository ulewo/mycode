<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改窝窝信息-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.editgroup.css">
<style type="text/css">
#selected4 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<a href="javascript:history.back(-1)">返回</a>
			<form id="articleform">
				<div class="form_tit">
					<span class="form_tit_t">标题</span>
					（<span class="form_tit_x"><span>标题不能超过150字符</span> <b>必填</b></span>）
				</div>
				<div class="form_editinput">
					<div class="form_title_input">
						<input type="text" name="title" id="article_title" value="${article.title}"/>
					</div>
					<div class="form_title_info">发表在</div>
					<div class="form_title_select">
						<select id="article_item" name="itemId">
							<option value=""></option>
							<option value="0">全部分类</option>
							<c:forEach var="item" items="${list}">
								<option  <c:if test="${article.itemId==item.id}">selected="selected"</c:if> value="${item.id}">${item.itemName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="clear"></div>
				</div>
				<div class="form_tit">
					<span class="form_tit_t">好的关键字可以让别人更容易找到此篇文章</span>
					（<span class="form_tit_x"><b>选填</b></span>）
				</div>
				<div class="form_editinput">
				 	<div class="form_title_input"><input class="editinput" type="text" name="keyWord" id="article_keyword" value="${article.keyWord}"/></div>
				 	<div class="form_title_info_t">多个关键字用半角逗号隔开，最多3个</div>
				 	<div class="clear"></div>
				</div>
				<div class="form_tit">
					<span class="form_tit_t">内容</span>
					（<span class="form_tit_x"><b>必填</b></span>）
				</div>
				<div class="form_editcontent" id="editor">
				
				</div>
				<input type="hidden" name="id"  value="${article.id}"/>
				<input type="hidden" name="gid"  value="${article.gid}"/>
				<input type="hidden" id="content" name="content" value="">
				<input type="hidden" id="faceImg" name="image">
				<div class="form_sub_btn">
					<a href="javascript:void(0)" id="sub_article_btn" class="btn">修改帖子</a>
					<a href="javascript:history.back(-1)" class="btn cancelbtn">取消</a>
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
				</div>
				</form>	
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
	<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
	//-->
	</script>
	<script type="text/javascript" src="${realPath}/ueditor/editor_config.js"></script>
	<script type="text/javascript" src="${realPath}/ueditor/editor.js"></script>
	<script type="text/javascript" src="${realPath}/js/group.manage.editarticle.js"></script>
	<script type="text/javascript">
		var editor;
		$(function(){
			editor = new UE.ui.Editor();
			editor.render("editor");
			editor.ready(function(){
			    editor.setContent('${article.content}');
			});
			
			$("#sub_article_btn").bind("click",addArticle);
		})
	</script>
</body>
</html>