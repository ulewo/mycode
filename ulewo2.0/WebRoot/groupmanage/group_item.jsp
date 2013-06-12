<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.groupitem.css">
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
					<div class="item_name"><input type="text" value="${item.itemName}" readonly="readonly"></div>
					<div class="item_range"><input type="text" value="${item.itemCode}" readonly="readonly"></div>
					<div class="item_count">${item.articleCount}</div>
					<div class="item_op"><a href="">修改</a>&nbsp;&nbsp;<a href="">删除</a></div>
					<div class="clear"></div>
				</div>
			</c:forEach>
			<div class="add_item"><a href="" class="btn">新增分类</a></div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>