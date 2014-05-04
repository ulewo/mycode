﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
var topicmanage={};
topicmanage.gid ="${gid}";
</script>
<script type="text/javascript" src="${realPath}/js/group.manage.topic.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:topicmanage.search()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.edit()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.topTopic()" class="easyui-linkbutton" data-options="iconCls:'icon-top',plain:true">置顶</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.topTopicCancel()" class="easyui-linkbutton" data-options="iconCls:'icon-top-no',plain:true">取消置顶</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.essenceTopic()" class="easyui-linkbutton" data-options="iconCls:'icon-good',plain:true">精华</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.essenceTopicCancel()" class="easyui-linkbutton" data-options="iconCls:'icon-good-no',plain:true">取消精华</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:topicmanage.deleteTopic()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
				</tr>
			</table>
		</div>
			<div class="searchDiv">
				<form id="searchform" method="post">
					<table>
						<tr>
							<td>标题</td>
							<td><input  class="easyui-validatebox"  name="title" style="width:150px"></td>
							<td>发布日期</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
							<td>至</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startEnd" style="width:120px"></td>
							<td>所属板块</td>
							<td><input id="category" name="cateId"  class="easyui-combobox" data-options="editable:false"></td>
						</tr>
					</table>
				</form>
			</div>
	</div>
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,pageSize:20,url:'../topics?gid=${param.gid}',method:'get'" class="easyui-datagrid"
			title="文章列表" rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="title" data-options="formatter:topicmanage.formatTitle">标题</th>
				<th field="categoryName">所属板块</th>
				<th field="commentCount">回复</th>
				<th field="readCount">阅读</th>
				<th field="userName" align="left">发布人</th>
				<th field="showCreateTime" align="center">发布时间</th>
			</tr>
			</thead>
		</table>
	</div>
	<div div id="editDialog"  class="easyui-dialog" title="修改"  
		    data-options="modal:true,resizable:false,draggable:false,collapsible:false,closed:true,
		    maximized:true,minimizable:false,maximizable:false">
		<iframe id="editFrame" frameborder="0" width="100%" height="100%"></iframe>
	</div>
</body>
</html>