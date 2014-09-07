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
<script type="text/javascript" src="${realPath}/js/admin.spider.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false">
		<div class="searchDiv" style="padding-bottom:10px;">
				<form id="spiderform" method="post">
					<table>
						<tr>
							<td><label for="osc"><input id="osc" name="type" type="radio" value="osc">开源中国资讯</label></td>
							<td><label for="xwg"><input id="xwg" name="type" type="radio" value="xwg">新闻哥</label></td>
							<td><label for="cnblog"><input id="cnblog" name="type" type="radio" value="cnblog">博客园</label></td>
							<td><label for="qilu"><input id="qilu" name="type" type="radio" value="qilu">齐鲁网</label></td>
							<td><label for="gb"><input id="gb" name="type" type="radio" value="gb">逛吧</label></td>
						</tr>
						<tr>
							<td colspan="10"><a href="javascript:adminSpider.spider()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">开始抓取</a></td>
						</tr>
					</table>
				</form>
		</div>
	</div>
	<div data-options="region:'center'" data-options="border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'north',border:false">
				<div class="toolbar datagrid-toolbar" >
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td><a href="javascript:adminSpider.searchSpider()" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">查询</a></td>
							<td><div class="toolbar-line"></div></td>
							<td><a href="javascript:ulewo_common.clearForm('searchform')" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">清除</a></td>
							<td><div class="toolbar-line"></div></td>
							<td><a href="javascript:adminSpider.deleteMember()" class="easyui-linkbutton" data-options="iconCls:'icon-del',plain:true">删除文章</a></td>
							<td><div class="toolbar-line"></div></td>
							<td><a href="javascript:adminSpider.selectGroup()" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true">发布文章</a></td>
						</tr>
					</table>
				</div>
				<div>
					<form id="searchform" method="post">
						<table>
							<tr>
								<td>发布状态</td>
								<td>
									<select name="status" class="easyui-combobox">
										<option value="1">已经发布</option>
										<option value="0">未发布</option>
									</select>
								</td>
								<td>来源网站</td>
								<td>
									<select name="type" class="easyui-combobox">
										<option value="osc">开源中国</option>
										<option value="xwg">新闻哥</option>
										<option value="cnblog">博客园</option>
										<option value="qilu_new">齐鲁网-资讯快报</option>
										<option value="qilu_life">齐鲁网-生活百态</option>
										<option value="qilu_star">齐鲁网-八卦明星</option>
										<option value="gb_pic">逛吧-图片</option>
										<option value="gb_game">逛吧-游戏</option>
										<option value="gb_movie">逛吧-视频</option>
										<option value="gb_topic">逛吧-文章</option>
										<option value="gb_talk">逛吧-杂谈</option>
										<option value="gb_joke">逛吧-搞笑</option>
									</select>
								</td>
								<td>创建日期</td>
								<td><input class="easyui-datebox" data-options="editable:false" name="startTm" style="width:120px"></td>
								<td>至</td>
								<td><input class="easyui-datebox" data-options="editable:false" name="startEnd" style="width:120px"></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<div data-options="region:'center',border:false">
				<table id="datagrid" data-options="fit:true,pageSize:20,url:'getSpiderList.action',method:'get'" class="easyui-datagrid"
					title="成员列表" rownumbers="true" pagination="true">
					<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th  data-options="field:'title'">标题</th>
						<th  data-options="field:'type',formatter:adminSpider.typeFormate">来源网站</th>
						<th  data-options="field:'status',formatter:adminSpider.statusFormate">状态</th>
						<th  data-options="field:'createTime'">时间</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	
	<div div id="groupDialog"  class="easyui-dialog" title="选择窝窝"  
		    data-options="modal:true,resizable:false,draggable:true,collapsible:false,closed:true,
		    maximized:false,minimizable:false,maximizable:false" style="width:400px;height:150px; padding: 10px;">
		  <form id="categoryForm" method="post">
			<table>
				<tr style="height:50px;">
					<td>窝窝</td>
					<td><input class="easyui-combobox" name="gid" id="group" style="width:150px;" data-options="required:true"></td>
					<td>分类</td>
					<td><input class="easyui-combobox" name="categoryId" id="category" style="width:150px;" data-options="required:true"></td>
				</tr>
				<tr style="text-align:center;">
					<td colspan="4"><a href="javascript:adminSpider.sendTopic()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确定</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>