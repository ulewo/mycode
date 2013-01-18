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
	<script type="text/javascript" src="../js/user.findpassword.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.register.css">
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
  	<div class="bodycon">
	<div class="register">
		<div id="loginerror"></div>
		<form action="showFetch.jspx" method="post" id="subform">
		<input type="hidden" id="ajaxReturn" value="N"/>
		<div class="input_area">
			<div class="tit">注册邮箱</div>
			<div class="input_con">
				<input type="text" class="long_input" name="email" style="color:#494949" id="email" value="${email}"/>
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
			<div class="bbtn1" id="subBtn">
				<a href="javascript:nextSetp()">下一步</a>
			</div>
		</div>
		</form>
	</div>
	</div>
	 <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
