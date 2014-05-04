<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文章管理</title>
<%@ include file="common.jsp" %>
<script type="text/javascript" src="../js/admin.article.js"></script>
</head>
<body>
<form id="searchForm" action="">
	<table>
		<tr>
			<td>标题:</td>
			<td><input class="easyui-validatebox" style="width:100px" name="title"/></td>
			<td>发帖人:</td>
			<td><input class="easyui-validatebox" style="width:100px" name="userId"/></td>
			<td>发帖时间:</td>
			<td><input class="easyui-datebox" style="width:100px" name="userId"/></td>
			<td>-</td>
			<td><input class="easyui-datebox" style="width:100px" name="userId"/></td>
			<td><a id="searchBtn" href="javascript:billiminstock.searchArea();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
			<td><a id="clearBtn"  href="javascript:billiminstock.searchLocClear();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">清除</a></td>
		</tr>
		<tr>
			<td><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="article.deleteArticle()" >删除</a></td>
			<td colspan="9"><a href="javascript:void(0)" id="${item.id}" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="${item.action}" >推荐</a></td>
		</tr>
	</table>
</form>
<table id="dataGrid">
</table>
</body>
</html>