<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="left">
			<div class="login_p">
				<div class="login_tit">账号：</div>
				<div class="login_input"><input type="text"></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">登陆密码：</div>
				<div class="login_input"><input type="text"></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">验证码：</div>
				<div class="login_input login_code"><input type="text" class="code"></div>
				<div class="login_code_img">
					<a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" height="23" border="0"/></a>
				</div>
				<div class="login_code_refresh"><a href="" class="login_code_refresh_link">看不清？</a></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_check"><input type="checkbox"/></div>
				<div class="login_remember">
					记住我的登陆信息
					<span class="login_remember_info">
						(请勿在公用电脑或者网吧内使用此项)
					</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_submit">
					<a href="javascript:void(0)" class="btn">现在登陆</a>
					<a href="" class="foregetpsw">忘记密码？</a>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="login_register">
				没有账号？<a href="">注册新会员</a>	
			</div>
			<div class="login_right_tit">
				登陆后可以？
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
</body>
</html>