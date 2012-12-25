<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/register.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/top.js"></script>
<link rel="stylesheet"  href="css/top.css" type="text/css"  />
<div id='userContent'>
	<div style="text-align:right;"><a href="javascript:hidenUserCon()" style="width:10px;float:right;"><img src="images/tabs_close.gif" border="0"/></a></div>
	<div class="uinfoTit">个人信息</div>
	<div style="height:20px;padding-top:5px;"><a href="userspace/userInfor.jspx?userId=${user.userId}">信息中心</a></div>
	<div class="uinfoTit">我的群组</div>
	<div id="umyGroup"></div>
	<div class="uinfoTit">参与的群组</div>
	<div id="ujoinGroup"></div>
</div>
<div id="topcon">
	<div style="text-align:left;width:1000px;margin:0 auto;padding-top:10px;padding-left:10px;">
		<div class="topConleft">
			<ul>
				<li>
				<c:if test="${user!=null}">
					<div class="welcom">您好</div><div class="userDiv" id="userDiv"><a href="javascript:showInfor('${user.userId}')">${user.userName}</a></div><div class="userCancel">！<a href="javascript:loginout('${user.userId}')">退出</a></div>
				</c:if>
				<c:if test="${user==null}">
					您好，欢迎来有乐窝!&nbsp;<a href="javascript:login();">登录</a>&nbsp;或&nbsp;<a href="register.jsp">注册</a>
				</c:if>
				</li>
			</ul>
		</div>
		<div class="topConRight">
			<ul>
				<li><a href="index.jspx">首页</a></li>
				<li>/</li>
				<li><a href="group/groups.jspx">圈子</a></li>
				<li>/</li>
				<li><a href="group/allArticles.jspx">话题</a></li>
				<li>/</li>
				<li><a href="javascript:createGroup('${user.userId}')" style="color:red;">创建圈子</a></li>
			</ul>
		</div>
		
	</div>
</div>

<div id="login">
	<div class="logincon">
		<div class="close"><a href="javascript:closeWind()"><img src="images/tabs_close.gif" border="0"></a></div>
		<div class="logintitle">用户登录</div>
		<div>
			<div class="new_construct">
				<div class="tit">登录邮箱</div>
					<div class="inputare"><div><input type="text" name="email"  id="email"></div></div>
			</div>
			<div class="new_construct">
				<div class="tit">密码</div>
				<div class="inputare"><div><input type="password" name="password" id="password"></div></div>
			</div>
				
			<div class="new_construct">
				<div class="tit">验证码</div>
				<div class="inputare">
					<div style="float:left;width:90px;"><input type="text" style="width:80px;" id="checkCode"></div>
					<div style="float:left;width:80px;padding-top:3px;"><a href="JavaScript:refreshImage();" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" border="0"/></a></div>
				</div>
			</div>
			<div class="new_constructx" style="margin-top:30px;">
					<div class="smallButon" style="float:left;width:100px;margin-left:100px;"><a href="javascript:loginDo()" id="subbut" onFocus="this.blur()" >登 陆</a></div>
					<div class="smallButon" style="float:left;width:100px;"><a href="javascript:document.location.href = 'register.jsp'" onFocus="this.blur()" >注 册</a></div>
			</div>
		</div>
	</div>
</div>
