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
var groupManageMember={};
groupManageMember.gid = "${gid}";
</script>
<script type="text/javascript" src="${realPath}/js/group.manage.member.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:groupManageMember.searchMember()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:groupManageMember.deleteMember()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除成员</a></td>
				</tr>
			</table>
		</div>
			<div class="searchDiv">
				<form id="searchform" method="post">
					<table>
						<tr>
							<td>用户名</td>
							<td><input  class="easyui-validatebox"  name="userName" style="width:150px"></td>
							<td>加入日期</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
							<td>至</td>
							<td><input class="easyui-datebox" data-options="editable:false" name="startEnd" style="width:120px"></td>
						</tr>
					</table>
				</form>
			</div>
	</div>
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,pageSize:20,url:'../member?gid=${gid}',method:'get'" class="easyui-datagrid"
			title="成员列表" rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th  data-options="field:'userIcon',formatter:groupManageMember.userIconFormate">头像</th>
				<th  data-options="field:'userName'">用户名</th>
				<th  data-options="field:'joinTime'">加入时间</th>
				<th  data-options="field:'topicCount'">发帖</th>
			</tr>
			</thead>
		</table>
	</div>
</body>
</html>