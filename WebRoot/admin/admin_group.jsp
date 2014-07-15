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
<script type="text/javascript" src="${realPath}/js/admin.group.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:adminGroup.searchGroup()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:adminGroup.deleteGroup()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除窝窝</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:adminGroup.essenceGroup()" class="easyui-linkbutton" data-options="iconCls:'icon-good',plain:true">推荐</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:adminGroup.changeCategroy()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改分类</a></td>
				</tr>
			</table>
		</div>
			<div class="searchDiv">
				<form id="searchform" method="post">
					<table>
						<tr>
							<td>窝窝名称</td>
							<td><input  class="easyui-validatebox"  name="groupName" style="width:150px"></td>
							<td>创建日期</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
							<td>至</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startEnd" style="width:120px"></td>
							<td>是否推荐</td>
						</tr>
					</table>
				</form>
			</div>
	</div>
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,pageSize:20,url:'groups',method:'get',onDblClickRow:adminGroup.onClickRow," class="easyui-datagrid"
			title="窝窝列表" rownumbers="true" pagination="true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th  data-options="field:'userIcon',formatter:adminGroup.userIconFormate">LOGO</th>
					<th  data-options="field:'groupName'">窝窝名称</th>
					<th  data-options="field:'createTime'">加入时间</th>
					<th  data-options="field:'topicCount'">帖子数</th>
					<th  data-options="field:'commendGrade',sortable:true,width:80,
					 editor:{
							type:'validatebox'
						}">推荐等级</th>
					<th  data-options="field:'pCategoryName'">一级分类</th>
					<th  data-options="field:'categoryName'">二分类</th>
				</tr>
			</thead>
		</table>
	</div>
	
	<div div id="categoryDialog"  class="easyui-dialog" title="选择分类"  
		    data-options="modal:true,resizable:false,draggable:true,collapsible:false,closed:true,
		    maximized:false,minimizable:false,maximizable:false" style="width:300px;height:150px; padding: 10px;">
		<form id="categoryForm" method="post">
			<div  id="categroy">
				<select id="cate" name="pCategroyId" ></select>
				<select id="sub" name="categroyId"></select>
			</div>
			<div style="margin-top: 10px;text-align:center"><a href="javascript:adminGroup.updateGroupCategory()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a></div>
		</form>
	</div>
</body>
</html>