<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小友吧 大世界 小智慧 大财富 --你乐我</title>
<meta name="description" content="你乐我 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小友吧 大世界 小智慧 大财富 — 你乐我">
</head>
<body>
<jsp:include page="admin_menue.jsp"></jsp:include>
<div class="body_con">
	<div class="pageArea"></div>
	<div id="con"></div>
	<div class="pageArea"></div>
</div>
<script type="text/javascript" src="js/admin.public.js"></script>
<script type="text/javascript" src="js/admin.rearticle.js"></script>
<script type="text/javascript">
$(function() {
	reArticleList(1);
})
</script>
</body>
</html>