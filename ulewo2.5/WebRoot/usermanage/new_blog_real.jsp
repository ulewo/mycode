<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理文章-有乐窝</title>
<%@ include file="../common/easyui_common.jsp" %>
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>
<script type="text/javascript" src="${realPath}/ueditor/ueditor.config.js?version=2.5.2"></script>
<script type="text/javascript" src="${realPath}/ueditor/ueditor.all.real.js?version=2.5.3"></script>
<script type="text/javascript">
var topicmanage={};
topicmanage.gid = "${param.gid}";
</script>
<script type="text/javascript" src="${realPath}/js/user.manage.saveblog.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:userManageBlog.saveBlog()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:parent.closeWindow()" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true">取消</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<form id="saveForm" method="post">
		<table width="98%">
			<tr>
				<td style="width:50px;">标题：</td> 
				<td><input type="text" class="easyui-validatebox" name="title" id="title"  style="width:300px;" ></td>
			</tr>
			<tr>
				<td>分类：</td>
				<td>
					<select  class="easyui-combobox" name="categoryId" id="categoryId" style="width:300px;" data-options="editable:false">   
						<option  value="0" >全部分类</option> 
						<c:forEach var="item" items="${categorys}" >
						 	<option  value="${item.categoryId}" >${item.name}</option> 
						</c:forEach>
					</select> 
				</td>
			</tr>
			<tr>
				<td>关键字：</td>
				<td><input type="text" class="easyui-validatebox" id="keyWord" name="keyWord" style="width:300px;"></td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea name="content" id="editor"></textarea>
					<script type="text/javascript">
					var editor = UE.getEditor('editor');
					</script>
				</td>
			</tr>
		</table>
		</form>
	</div>
</body>
</html>