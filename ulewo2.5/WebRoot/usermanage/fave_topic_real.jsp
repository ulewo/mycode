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
var topicmanage={};
topicmanage.gid = "${param.gid}";
</script>
<script type="text/javascript" src="${realPath}/js/user.manage.fave.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:userFave.search('A')" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:userFave.deleteFave()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
				</tr>
			</table>	
		</div>
		<div class="searchDiv">
			<form id="searchform" method="post">
				<table>
					<tr>
						<td>标题</td>
						<td><input  class="easyui-validatebox"  name="title" style="width:250px"></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		 <table id="datagrid" data-options="fit:true,pageSize:20,url:'${realPath}/manage/queryFavoriteArticle.action?type=A',method:'get'" class="easyui-datagrid"
			title="文章列表" rownumbers="true" pagination="true">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th field="title" data-options="formatter:userFave.formatTopicTitle">标题</th>
			</tr>
			</thead>
		</table>
	</div>
</body>
</html>