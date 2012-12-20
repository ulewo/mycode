<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
</style>    
<div style="padding-top:10px;">
	<div>
		欢迎管理员
	</div>
</div>
<ul class="menue">
	<li class="blank" id="sel_left1"><a href="manage.jspx?gid=${gid }" id="selected1">基本资料</a></li>
	<li class="blank" id="sel_left2"><a href="manageMember.jspx?gid=${gid }" id="selected2">成员管理</a></li>
	<li class="blank" id="sel_left3"><a href="articleManage.jspx?gid=${gid}" id="selected3">帖子管理</a></li>
	<li class="blank" id="sel_left4"><a href="itemManage.jspx?gid=${gid}" id="selected4">分类管理</a></li>
	<!-- <li class="blank" id="sel_left5"><a href="Items.jspx?groupNumber=${groupNumber}" id="selected5">应用管理</a></li> -->
</ul>
