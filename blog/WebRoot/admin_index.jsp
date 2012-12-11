<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理</title>
<link rel="stylesheet" type="text/css" href="css/admin.index.css" />
<script charset="utf-8" src="js/jquery.min.js"></script>
<script charset="utf-8" src="js/admin.public.js"></script>
<script charset="utf-8" src="js/admin.js"></script>
<style type="text/css">
a.lisel1{background:#E6E6E6;font-weight:bold;}
</style>
<script type="text/javascript">
$(function(){
	loadArticle(1);
	initItem();
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
				<a href="admin_add.jsp" class="btn">发布博文</a>
				<div class="index_select" id="item"></div>
				<a href="javascript:search()" class="btn">查询</a>
			</div>
			<div class="pageArea" style="margin-top:0px;padding-right:10px;"></div>
			<div class="article_th">
				<div class="article_tit">标题</div>
				<div class="article_time">发布时间</div>
				<div class="article_re">点击/回复</div>
				<div class="article_op">操作</div>
			</div>
			<div id="con"></div>
			<div class="pageArea" style="margin-top:0px;padding-right:10px;"></div>
		</div>
		<div class="clear_float"></div>
	</div>
</body>
</html>