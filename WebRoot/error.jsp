<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>出错啦</title>
   <meta name="description" content="分享快乐，分享囧事，分享生活，一份快乐两个人分享,就会成为两份快乐；一份忧愁两个人分担，便会成为半份忧愁 --友吧中国">
	<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录 ,有图有真相— 友吧中国">
<style type="text/css">
	.error{text-align:center;font-size:16px;}
</style>
</head>
<body onload="loadTime()">
<div class="error">你访问的页面不存在，<span id="time"></span>秒后跳转到首页</div>
<script type="text/javascript">
var i=5;
function loadTime(){
	document.getElementById("time").innerText = i;
	if(i==0){
		document.location.href="http://www.ulewo.com";
	}else{
		setTimeout("loadTime()",1000);
	}
	i--;
}
</script>
</body>
</html>