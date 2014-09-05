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
var adminTopicHot={};
adminTopicHot.gid ="${gid}";
</script>
<script type="text/javascript" src="${realPath}/js/admin.topic_hot.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/ZeroClipboard.js?version=2.5"></script>

</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="toolbar datagrid-toolbar">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><a href="javascript:adminTopicHot.search()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
					<td><div class="toolbar-line"></div></td>
					<td><a href="javascript:adminTopicHot.createHot()" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">生成每日热点</a></td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'center',border:false">
	     <div class="easyui-layout" data-options="fit:true">
		     <div data-options="region:'west',border:false" style="width:650px">
		     	 <table id="datagrid" data-options="fit:true,pageSize:20,url:'topics',method:'get'" class="easyui-datagrid"
					title="文章列表" rownumbers="true" pagination="true">
					<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="title" data-options="formatter:adminTopicHot.formatTitle">标题</th>
						<th field="commentCount">回复</th>
						<th field="readCount">阅读</th>
						<th field="userName" align="left">发布人</th>
						<th field="showCreateTime" align="center">发布时间</th>
					</tr>
					</thead>
				</table>
		     </div>
		     <div data-options="region:'center',border:false">
		     	<table id="datagrid_hot" data-options="fit:true,pageSize:20,method:'get'" class="easyui-datagrid"
					title="热点文章" rownumbers="true" pagination="false">
					<thead>
					<tr>
						<th field="title" data-options="formatter:adminTopicHot.formatTitle">标题</th>
					</tr>
					</thead>
				</table>
		     </div>
	     </div>
	</div>
	
	<div div id="hotDialog"  class="easyui-dialog" title="热点文章"  
		    data-options="modal:true,resizable:false,draggable:false,collapsible:false,closed:true,
		    maximized:true,minimizable:false,maximizable:false">
		    <div id="hotTopic" style="margin:40px 20px;"></div>
	</div>
</body>
</html>