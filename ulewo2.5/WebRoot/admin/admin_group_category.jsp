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
<script type="text/javascript" src="${realPath}/js/admin.group_category.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',border:false" style="width:500px;">
		 <table id="datagrid" data-options="fit:true,
		 pageSize:20,
		 toolbar:'#toolbar',
		 onDblClickRow:groupCategory.onClickRow,
		 checkOnSelect:false,
		 url:'loadGroupCategory.action?pid=0',
		 border:false" class="easyui-datagrid"
			title="分类模块列表" rownumbers="true" pagination="false">
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
							required:true
						}
					},align:'center'">排序</th>
				<th data-options="field:'link',align:'center',formatter:groupCategory.formatOp">操作</th>
			</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'center',border:true">
		 <table id="subdatagrid" data-options="fit:true,
		 pageSize:20,
		 toolbar:'#subtoolbar',
		 onClickRow:groupCategory.onClickSubRow,
		 checkOnSelect:false,
		 url:'',
		 border:false" class="easyui-datagrid"
			title="子模块列表" rownumbers="true" pagination="false">
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
							required:true
						}
					},align:'center'">排序</th>
			</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:groupCategory.newRow()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:groupCategory.deleteRow()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:groupCategory.save()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a></td>
				</tr>
		</table>
	</div>
	<div id="subtoolbar">
		<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:groupCategory.newSubRow()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">新增</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:groupCategory.deleteSubRow()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:groupCategory.saveSub()" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a></td>
				</tr>
		</table>
	</div>
</body>
</html>