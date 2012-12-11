<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title>登陆你乐我</title>
	<meta name="description" content="分享快乐，分享囧事，分享生活，一份快乐两个人分享,就会成为两份快乐；一份忧愁两个人分担，便会成为半份忧愁 --你乐我">
	<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录 ,有图有真相— 你乐我">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/user.login.js"></script>
	<link rel="stylesheet" type="text/css" href="css/user.register.css">
	<link rel="stylesheet" type="text/css" href="css/common.css" />
  </head>
  <body>
  <jsp:include page="menue.jsp"></jsp:include>
  <div class="body_con">
	<div class="left">
		<div style="text-align:center;font-size:14px;color:red;">${message }</div>
	<div class="register">
		<div id="loginerror"></div>
		<form action="login" method="post" id="subform">
		<input type="hidden" id="ajaxReturn" value="N"/>
		<div class="input_area">
			<div class="tit">帐号</div>
			<div class="input_con">
				<input type="text" class="long_input" name="userName" value="用户名/邮箱" id="userName"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">密码</div>
			<div class="input_con">
				<input type="password" class="long_input" value="" name="passWord" id="passWord"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">验证码</div>
			<div class="check_con">
				<input type="text" class="long_input" name="checkCode" id="checkCode"/>
			</div>
			<div class="checkcode">
				<a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" border="0"/></a>
			</div>
			<div class="changecode">
				<a href="javascript:refreshImage()">换一张</a>
			</div>
		</div>
		<div class="input_area">
			<div class="tit"></div>
			<div class="input_con" style="height:25px;border:0px;">
				<div style="width:20px;float:left;"><input type="checkbox" class="long_input" id="autologin"/></div>
				<div style="width:100px;float:left;padding-top:5px;padding-left:5px;cursor:pointer;"  id="logincheck">自动登录</div>
			</div>
		</div>
		<div id="subBtn">
			<a href="javascript:login()" class="subtn">登录</a>
		</div>
		</form>
	</div>
	</div>
	<div class="right">
		<jsp:include page="right.jsp"></jsp:include>		
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
  </body>
</html>
