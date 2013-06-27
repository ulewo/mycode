<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/login.css">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="left">
			<div style="font-weight:bold;font-size:16px;padding-left:50px;color:#666666;height:30px;">重置密码1-2</div>
			<div class="login_p" style="margin-top:1px;">
				<div class="login_tit">新密码：</div>
				<div class="login_input"><input type="password" id="newpwd"></div>
				<div class="login_info">密码长度6-16位字符，由数字、字母组成</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">重复新密码：</div>
				<div class="login_input"><input type="password" id="renewpwd"></div>
				<div class="login_info">再次输入新密码</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">验证码：</div>
				<div class="login_input login_code"><input type="text" class="code" id="code"></div>
				<div class="login_code_img">
					<a href="javaScript:refreshImage()" onfocus="this.blur();"><img id="codeImage" src="${realPath}/common/image.jsp" height="23" border="0"/></a>
				</div>
				<div class="login_code_refresh"><a href="javaScript:refreshImage()" class="login_code_refresh_link">看不清？</a></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_submit">
					<a href="javascript:void(0)" class="btn" id="rest_btn_2">重置密码</a>
					<!-- <a href="" class="foregetpsw">忘记密码？</a> -->
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
				</div>
			</div>
		</div>
		<div class="right">
			
		</div>
		<div class="clear"></div>
	</div>
	<script type="text/javascript">
		var account = "${account}";
		var activationCode = "${code}";
	</script>
	<script type="text/javascript" src="${realPath}/js/user.restpwd2.js"></script>
	<%@ include file="common/foot.jsp" %>
</body>
</html>