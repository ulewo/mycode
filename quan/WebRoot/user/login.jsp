<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript" src="../js/user.login.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.register.css">
	<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
	<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
	<script src="../dialog/plugins/iframeTools.js"></script>
  </head>
  <body>
  <div style="text-align:center;font-size:14px;color:red;">${message }</div>
	<div class="register">
		<div id="loginerror"></div>
		<form action="register.jspx" method="post" id="subform">
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
				<a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="../common/image.jsp" border="0"/></a>
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
		<div class="input_area">
			<div class="tit"></div>
			<div class="bbtn1" id="subBtn">
				<a href="javascript:login()">登录</a>
			</div>
			<div class="findpsw" style="width:200px;text-align:left;"><a href="javascript:gotofindpaw()">忘记密码?</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:goToRegister()">帐号注册</a></div>
		</div>
		</form>
	</div>
  </body>
  <script type="text/javascript">
  	function gotofindpaw(){
  		parent.document.location.href="findpassword.jsp";
  	}
  	function goToRegister(){
  		parent.document.location.href="register.jsp";
  	}
  </script>
</html>
