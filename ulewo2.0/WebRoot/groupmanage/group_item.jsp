<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝文章分类管理-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.item.css">
<style type="text/css">
#selected6 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="item_tit">
				<div class="item_name">分类名</div>
				<div class="item_range">排序</div>
				<div class="item_count">文章数</div>
				<div class="item_op">操作</div>
				<div class="clear"></div>
			</div>
			<c:forEach var="item" items="${imtes}">
				<div class="item_con">
					<div class="item_name">${item.itemName}</div>
					<div class="item_range">${item.itemCode}</div>
					<div class="item_count">${item.articleCount}</div>
					<div class="item_op">
						<a href="javascript:void(0)" class="edit_item" name="${item.id}">修改</a>&nbsp;&nbsp;
						<a href="javascript:void(0)" class="deleteItem" count ="${item.articleCount}" name="${item.id}">删除</a>
					</div>
					<div class="clear"></div>
				</div>
			</c:forEach>
				<div class="item_con">
					<form action="${realPath}/groupManage/saveItem.action" method="post" id="itemForm">
					<input type="hidden" name="id" id="itemId">
					<input type="hidden" name="gid" value="${gid}">
					<div class="item_name"><input type="text" id="item_name" name="itemName"></div>
					<div class="item_range"><input type="text" id="item_code" name="itemCode"></div>
					<div class="item_count">&nbsp;</div>
					<div class="item_op">
						<a href="javascript:void(0)" class="btn" id="save_btn">新增</a>
						<a href="javascript:void(0)" id="cancel_edit" class="btn">取消</a></div>
					<div class="clear"></div>
					</form>
				</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/group.manage.edititem.js"></script>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>