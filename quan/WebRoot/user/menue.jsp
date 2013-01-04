<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="menue">
	<ul>
		<li class="blank" id="sel_left1"><a href="userInfo.jspx?userId=${userId}" id="selected1">个人中心</a></li>
		<li class="blank" id="sel_left2"><a href="createdGroups.jspx?userId=${userId}" id="selected2">Ta创建的友吧</a></li>
		<li class="blank" id="sel_left3"><a href="joinedGroups.jspx?userId=${userId}" id="selected3">Ta加入的友吧</a></li>
		<li class="blank" id="sel_left4"><a href="postTopics.jspx?userId=${userId}" id="selected4">Ta发起的话题</a></li>
		<li class="blank" id="sel_left5"><a href="reTopics.jspx?userId=${userId}" id="selected5">Ta参与的话题</a></li>
		<li class="blank" id="sel_left6"><a href="blog.jspx?userId=${userId}" id="selected6">博客</a></li>
		<li class="blank" id="sel_left7"><a href="addBlog.jspx" id="selected7">发表博文</a></li>
	</ul>
</div>