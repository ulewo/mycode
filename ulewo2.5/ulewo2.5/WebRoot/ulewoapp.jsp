<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝android客户端</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<script type="text/javascript" src="js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/app.css?version=2.5">
</head>
<body>
	 <%@ include file="common/head.jsp" %>
  	<div class="main">
		<div class="myinfo">
			<div class="info">
                <div class="title">有乐窝Android手机客户端</div>
                <div class="desc">最新版本: </div><div class="text">V1.0&nbsp;&nbsp;发布日期: 2013.05.01 &nbsp;&nbsp;软件大小: 341kb</div>
                <div class="desc">版本说明: </div><div class="text">1.实现基本功能模块文章浏览&nbsp;&nbsp;2.回复窝窝文章&nbsp;&nbsp;3.博客文章浏览&nbsp;&nbsp;4.回复博客</div> 
                <div class="desc">配置要求: </div><div class="text">320x480以上分辨率;支持Android2.2以上系统版本</div>
			</div>
			<div class="dlbutton"><a href="${realPath}/downloadApp"><img src="${realPath}/images/downappbtn.png" border="0"></a></div>
			<div class="code"><img src="${realPath}/images/qrcode.png" border="0"></div>
			<div class="clear"></div>
		</div>
		<div class="android_pic">
  			<img src="${realPath}/images/android_01.png">
  			<img src="${realPath}/images/android_02.png">
  			<img src="${realPath}/images/android_03.png">
  			<img src="${realPath}/images/android_04.png">
  			<img src="${realPath}/images/android_05.png">
  			<img src="${realPath}/images/android_06.png">
  			<img src="${realPath}/images/android_07.png">
  			<img src="${realPath}/images/android_08.png">
  			<div class="clear"></div>
  		</div>
  	</div>
  	 <%@ include file="common/foot.jsp" %>
</body>
</html>