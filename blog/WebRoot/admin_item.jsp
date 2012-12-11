<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
<link rel="stylesheet" type="text/css" href="css/admin.index.css" />
<script charset="utf-8" src="js/jquery.min.js"></script>
<script charset="utf-8" src="js/admin.js"></script>
<style type="text/css">
a.lisel2{background:#E6E6E6;font-weight:bold;}
</style>
<script type="text/javascript">
$(function(){
	loadItem();
});
</script>
</head>
<body>
	<div class="head">博客后台管理</div>
	<div class="main">
		<div class="admin_left">
			<jsp:include page="admin_menue.jsp"></jsp:include>
		</div>
		<div class="admin_right">
			<div class="oparea">
				<a href="javascript:addItem()" class="btn">添加分类</a>
			</div>
			<div class="article_th">
				<div class="article_tit">栏目名称</div>
				<div class="article_time article_item">栏目编号</div>
				<div class="article_op">操作</div>
			</div>
			<div id="con"></div>
		</div>
		<div class="clear_float"></div>
	</div>
</body>
</html>