<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>加入你乐我</title>
	<meta name="description" content="分享快乐，分享囧事，分享生活，一份快乐两个人分享,就会成为两份快乐；一份忧愁两个人分担，便会成为半份忧愁 --你乐我">
	<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录 ,有图有真相— 你乐我">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/user.register.js"></script>
	<link rel="stylesheet" type="text/css" href="css/user.register.css">
	<link rel="stylesheet" type="text/css" href="css/index.css" />
	<c:if test="${param.message!=null}">
		<script>
			alert(decodeURI("${param.message}"));
		</script>
	</c:if>
  </head>
  <body>
  <jsp:include page="menue.jsp"></jsp:include>
	<div class="body_con">
		<div class="left">
			<div class="register" style="padding-top:10px;">
				<form action="register" method="post" id="subform">
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
						<a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" border="0"/></a>
					</div>
					<div class="changecode">
						<a href="javascript:refreshImage()">换一张</a>
					</div>
				</div>
				<div id="subBtn">
					<a href="javascript:register()" class="subtn">注册</a>
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
