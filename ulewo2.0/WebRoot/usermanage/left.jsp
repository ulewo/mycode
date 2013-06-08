<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="manage_tit">
	<span class="manage_tit1"><img src="../upload/${user.userLittleIcon}" width="40"></span>
	<span class="manage_tit2">空间管理</span>
</div>
<div class="menue_p">
	<div class="menue_tit">
		个人信息管理
	</div>
	<ul class="menue">
		<li id="selected1"><a href="userinfo">修改个人资料</a></li>
		<li id="selected2"><a href="changepwd">修改登录密码</a></li>
		<li id="selected3"><a href="user_icon.jsp">修改头像</a></li>
	</ul>
	<div class="menue_tit">
		 博客管理
	</div>
	<ul class="menue">
		<li id="selected4"><a href="new_blog">发表博文</a></li>
		<li id="selected5"><a href="blog_item">分类管理</a></li>
		<li id="selected6"><a href="blog_reply">评论管理</a></li>
	</ul>
	
	<div class="menue_tit">
		 消息管理
	</div>
	<ul class="menue">
		<li id="selected7"><a href="notice.jspx">未处理的消息</a></li>
	</ul>
</div>
