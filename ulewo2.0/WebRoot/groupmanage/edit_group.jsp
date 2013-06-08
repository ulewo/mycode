<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>发表博文-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/group.manage.editgroup.css">
<style type="text/css">
#selected1 a{background:#ffffff;color:#333333;font-weight:bold;}fff}
</style>
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
			<div class="form_tit">
				<span class="form_tit_t">窝窝名称</span>
				<span class="form_tit_x">（选填）</span>
			</div>
			<div class="form_editinput">
				<div class="form_title_input">
					<input type="text"/>
				</div>
				<div class="clear"></div>
			</div>
			<div class="form_tit">
				<span class="form_tit_t">简介</span>
				<span class="form_tit_x">（选填）</span>
			</div>
			<div class="form_editcontent" id="editor">
				<textarea></textarea>
			</div>
			<div class="form_tit">
				<div class="form_tit_radio">加入权限？</div>
				<div class="form_con_radio">
					<input type="radio"/>允许任何人加入
					<input type="radio"/>需经我审批才能加入
				</div>
				<div class="clear"></div>
			</div>
			<div class="form_tit">
				<div class="form_tit_radio">发帖权限？</div>
				<div class="form_con_radio">
					<input type="radio"/>允许任何人加入
					<input type="radio"/>需经我审批才能加入
				</div>
				<div class="clear"></div>
			</div>
			<div class="form_sub_btn">
				<a href="" class="btn">确认修改</a>
			</div>
		
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
<script type="text/javascript">
    
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