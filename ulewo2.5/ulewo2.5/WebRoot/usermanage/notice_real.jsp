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
<style type="text/css">
.noread{color:#494949;font-weight:bolder;font-family:"lucida Grande",Verdana;color:#000000}
.read{color:#000000;font-family:"lucida Grande",Verdana;}
span.notice_tit{font-weight:bolder;color:red;}
</style>
<script type="text/javascript" src="${realPath}/js/user.manage.notice.js?version=2.5"></script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false">
					<div class="toolbar datagrid-toolbar">
						<table cellpadding="0" cellspacing="0">
							<tr>
								<td><a href="javascript:notice.search()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
								<td><div class="toolbar-line"></div></td>
								<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true">清除</a></td>
								<td><div class="toolbar-line"></div></td>
								<td><a href="javascript:notice.signNoticeRead()" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">标记为已读</a></td>
								<td><div class="toolbar-line"></div></td>
								<td><a href="javascript:notice.deleteNotice()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除</a></td>
							</tr>
						</table>	
					</div>
					<div class="searchDiv">
						<form id="searchform" method="post">
							<table>
								<tr>
									<td>发布日期</td>
									<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
									<td>至</td>
									<td><input class="easyui-datebox" data-options="editable:false" name="endTm" style="width:120px"></td>
								</tr>
							</table>
						</form>
					</div>
				</div>
				<div data-options="region:'center',border:false">
					 <table id="datagrid" data-options="fit:true,pageSize:20,url:'../notice.action',method:'get',border:false,checkOnSelect:false" class="easyui-datagrid"
						title="消息列表" rownumbers="true" pagination="true">
						<thead>
						<tr>
							<th data-options="field:'ck',checkbox:true"></th>
							<th field="title" data-options="formatter:notice.formatTitle,width:500">消息</th>
							<th field="createTime" align="center">发布时间</th>
							<th field="status" align="center" data-options="formatter:notice.formatStatus">状态</th>
						</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
</body>
</html>