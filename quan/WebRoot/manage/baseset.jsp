<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="../css/manage.group.css" type="text/css"  />
<title>Justlearning 学习，生活，娱乐</title>
<style>
#selected1 a{background:#FFFFFF;}
</style>
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript">
		KE.show({
			id : 'content',
			allowFileManager : true,
			resizeMode : 1,
			newlineTag : 'br',
			urlType : 'relative',
			afterCreate : function(id) {
				KE.event.ctrl(document, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
				KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
					KE.util.setData(id);
					document.forms['example'].submit();
				});
			}
		});
		function submitForm(){
			var groupName = $("#groupName").val();
			if(groupName.trim()==""){
				alert("群组名称不能为空");
				$("#groupName").focus();
				return;
			}else if(groupName.realLength()>20){
				alert("群组名称不能超过20");
				$("#groupName").focus();
				return;
			}else{
				groupName = groupName.replaceHtml();
				$("#groupName").val(groupName);
			}
			if(KE.util.isEmpty('content')){
				alert("群组简介不能为空");
				KE.util.focus('content')
				return;
			}
			$("#myform").submit();
		}
		
	</script>
</head>
<body>
<div class="maincon">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
			<form action="updateGroup.jspx" method="post" id="myform">
			<div class="form_name">
				<div class="form_name_tit">群名称：</div>
				<div class="form_name_input"><input type="text" value="${group.groupName }" name="groupName" id="groupName"/> &nbsp;&nbsp;<span style="color:red;">用户名长度1-20位字符，由中文、_、数字、字母组成</span></div>
			</div>
			<div class="form_desc">	
				<div class="form_desc_tit">群简介：</div>
				<div class="form_desc_area">
					<textarea id="content" name="groupDesc" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">${group.groupDesc }</textarea>
				</div>
				<div style="clear:left;"></div>
			</div>	
			<div class="form_join">
				<div class="form_join_tit">群权限：</div>
				<div class="form_join_input">
					<div><input type="radio" name="joinPerm" value="Y" <c:if test="${group.joinPerm=='Y'}">checked</c:if>>公开，允许任何人加入</div>
					<div><input type="radio" name="joinPerm" value="N" <c:if test="${group.joinPerm=='N'}">checked</c:if>>公开，需经我批准才能加入</div>
				</div>
			</div>
			<div class="form_post">
				<div class="form_post_tit">发帖权限：</div>
				<div class="form_post_input">
					<div><input type="radio" name="postPerm" value="A" <c:if test="${group.postPerm=='A'}">checked</c:if>>登陆用户可以发帖</div>
					<div><input type="radio" name="postPerm" value="M" <c:if test="${group.postPerm=='M'}">checked</c:if>>只有群成员可以发帖</div>
				</div>
			</div>
			<div class="form_sub">
				<input type="hidden" value="${group.id}" name="gid" >
				<div class="bbtn1"><a href="javascript:void(0)" onclick="submitForm()">保存设置</a></div>
			</div>
			</form>
	</div>
	<div class="clear"></div>
</div>
<div class="foot"></div>
</body>
</html>