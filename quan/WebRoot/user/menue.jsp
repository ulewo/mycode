<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="manage_left_head">
	<span class="manage_tit1"><img src="../upload/${user.userLittleIcon}" width="40"></span>
	<span class="manage_tit2"><a href="../user/userInfo.jspx?userId=${user.userId}">${user.userName}</a></span>
</div>
<div class="menue_p">
	<div class="menue_tit">
		个人信息管理
	</div>
	<ul class="menue">
		<li id="selected1"><a href="manage.jspx">编辑个人资料</a></li>
		<li id="selected2"><a href="repassword.jsp">修改登录密码</a></li>
		<li id="selected3"><a href="user_icon.jsp">修改头像</a></li>
	</ul>
	<div class="menue_tit">
		 博客管理
	</div>
	<ul class="menue">
		<li id="selected4"><a href="addBlog.jspx">发表博文</a></li>
		<li id="selected5"><a href="manageItem.jspx">分类管理</a></li>
	</ul>
	
	<div class="menue_tit">
		 消息管理
	</div>
	<ul class="menue">
		<li id="selected6"><a href="notice.jspx">未处理的消息</a></li>
	</ul>
</div>