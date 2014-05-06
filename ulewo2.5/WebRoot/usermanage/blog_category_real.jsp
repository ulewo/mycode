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
var blogCategory={};
blogCategory.gid = "${param.gid}";
</script>
<script type="text/javascript" src="${realPath}/js/user.manage.blog_category.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,
		 pageSize:20,
		 toolbar:'#toolbar',
		 onClickRow:blogCategory.onClickRow,
		 checkOnSelect:false,
		 url:'../loadCategory.action',
		 border:false" class="easyui-datagrid"
			title="分类模块列表" rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',
				 editor:{
						type:'validatebox',
						options:{
							required:true
						}
					},
					width:150">分类名</th>
				<th data-options="field:'rang',editor:{
					type:'numberbox',
						options:{
							required:true,
							min:1,
							max:9
						}
					},align:'center'">排序</th>
				<th data-options="field:'blogCount',align:'center'">文章数</th>
			</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:blogCategory.newRow()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:blogCategory.deleteRow()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:blogCategory.save()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a></td>
				</tr>
		</table>
	</div>
</body>
</html>