<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>加入有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/register.css?version=2.5">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="left">
			<form id="registerForm">
			<div class="register_p">
				<div class="register_tit">账号：</div>
				<div class="register_input"><input type="text" name="userName" id="userName"></div>
				<div class="register_info">用户名长度1-20位字符，由中文、数字、_ 、数字、字母组成</div>
				<div class="clear"></div>
			</div>
			<div class="register_p">
				<div class="register_tit">用常用邮箱：</div>
				<div class="register_input"><input type="text" name="email" id="email"></div>
				<div class="register_info">用于取回密码，请填写正确的常用邮箱</div>
				<div class="clear"></div>
			</div>
			<div class="register_p">
				<div class="register_tit">登陆密码：</div>
				<div class="register_input"><input type="password" name="pwd" id="pwd"></div>
				<div class="register_info">密码长度6-16位字符，由数字、字母组成</div>
				<div class="clear"></div>
			</div>
			<div class="register_p">
				<div class="register_tit">登陆密码：</div>
				<div class="register_input"><input type="password" name="repwd" id="repwd"></div>
				<div class="register_info">请再次输入密码</div>
				<div class="clear"></div>
			</div>
			<div class="register_p">
				<div class="register_tit">验证码：</div>
				<div class="register_input register_code"><input type="text" class="code" name="code" id="code"></div>
				<div class="register_code_img">
					<a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" height="23" border="0"/></a>
				</div>
				<div class="register_code_refresh"><a href="javascript:refreshImage()" class="register_code_refresh_link">看不清？</a></div>
				<div class="register_info">请输入图片中的字符</div>
				<div class="clear"></div>
			</div>
			<div class="register_p">
				<div class="register_tit"></div>
				<div class="register_submit">
					<a href="javascript:void(0)" class="btn" id="registerBtn">注册新用户</a>
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
				</div>
			</div>
			</form>
		</div>
		<div class="right">
			<div class="register_register">
				已有账号？<a href="${realPath}/login">直接登陆</a>	
			</div>
			<div class="register_right_tit">
				为什么要注册？
			</div>
			<ul>
				<li>1、发布帖子、吐槽和博客</li>
				<li>2、参与帖子、吐槽、博客的讨论和评论</li>
				<li>3、和别人分享生活中的点滴</li>
				<li>4、随时得到有乐窝最新的更新信息</li>
				<li>5、认识更多窝窝好友</li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.register.js?version=2.5"></script>
	<%@ include file="common/foot.jsp" %>
</body>
</html>