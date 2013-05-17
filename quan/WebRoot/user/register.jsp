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
	<script type="text/javascript" src="../js/user.register.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/u8.square.css">
	<link rel="stylesheet" type="text/css" href="../css/user.register.css">
  </head>
  <body>
 <%@ include file="../common/head.jsp" %>
  <div class="bodycon">
  <div style="text-align:center;font-size:14px;color:red;">${message }</div>
	<div class="register">
		<form action="register.jspx" method="post" id="subform">
		<input type="hidden" id="ajaxReturn" value="N"/>
		<div class="input_area">
			<div class="tit">用户名</div>
			<div class="input_con">
				<input type="text" class="long_input" name="userName"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">常用邮箱</div>
			<div class="input_con">
				<input type="text" class="long_input" name="email"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">创建密码</div>
			<div class="input_con">
				<input type="password" class="long_input" name="passWord"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">确认密码</div>
			<div class="input_con">
				<input type="password" class="long_input" name="rePassWord"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">验证码</div>
			<div class="check_con">
				<input type="text" class="long_input" name="checkCode"/>
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
			<div class="bbtn1" id="subBtn">
				<a href="javascript:registerdo()">注册</a>
			</div>
		</div>
		</form>
	</div>
	</div>
	<jsp:include page="../common/foot.jsp"/>
	<script type="text/javascript">
		$(function(){
	  		document.onkeydown = function(e){    
	  		    var ev = document.all ? window.event : e;  
	  		    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	  		    	registerdo();
	  		    }  
	  		  }  
	  	});
	</script>
  </body>
</html>
