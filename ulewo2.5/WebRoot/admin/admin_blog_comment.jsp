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
var adminBlogComment={};
adminBlogComment.gid = "${gid}";
</script>
<script type="text/javascript" src="${realPath}/js/admin.blog_comment.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:adminBlogComment.search()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
				</tr>
			</table>
		</div>
			<div class="searchDiv">
				<form id="searchform" method="post">
					<table>
						<tr>
							<td>标题</td>
							<td><input class="easyui-validatebox"  name="title" style="width:150px"></td>
							<td>发布日期</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
							<td>至</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startEnd" style="width:120px"></td>
						</tr>
					</table>
				</form>
			</div>
	</div>
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,pageSize:20,url:'blogs',method:'get'" class="easyui-datagrid"
			title="博客列表" rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th field="title">标题</th>
				<th field="commentCount">回复</th>
				<th field="readCount">阅读</th>
				<th field="userName" align="left">发布人</th>
				<th field="showCreateTime" align="center">发布时间</th>
			</tr>
			</thead>
		</table>
	</div>
	<div data-options="region:'south'" style="height:250px">
		 <table id="datagrid_comment" data-options="fit:true,pageSize:20,toolbar:'#comment_toolbar'" class="easyui-datagrid"
		    title="评论列表" rownumbers="true" pagination="true">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="simpleContent" width="900" >评论</th>
					<th field="userName" align="left">发布人</th>
					<th field="showCreateTime" align="center">发布时间</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="comment_toolbar" class="toolbar datagrid-toolbar">
		<a href="javascript:adminBlogComment.deleteComment()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除评论</a>
	</div>
</body>
</html>