<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div style="padding-top:10px;">
	<div class="manage_back"><a href="../group/group.jspx?gid=${gid}">&lt;&lt;返回窝窝首页</a></div>
	<div class="manage_tit">
		<span class="manage_tit1"><img src="../upload/${user.userLittleIcon}" width="40"></span>
		<span class="manage_tit2"><a href="../user/userInfo.jspx?userId=${user.userId}">${user.userName}</a></span>
	</div>
</div>
<div class="menue_tit">
	窝窝管理
</div>
<ul class="menue">
	<li id="selected1"><a href="manage.jspx?gid=${gid }">窝窝设置</a></li>
	<li id="selected2"><a href="getLogo.jspx?gid=${gid}">窝窝头像设置</a></li>
	<li id="selected3"><a href="getHeadImag.jspx?gid=${gid }">窝窝logo设置</a></li>
</ul>
<div class="menue_tit">
	 文章管理
</div>
<ul class="menue">
	<li id="selected4"><a href="articleManage.jspx?gid=${gid}">文章列表</a></li>
	<li id="selected5"><a href="itemManage.jspx?gid=${gid}">分类管理</a></li>
</ul>
<div class="menue_tit">
	成员管理
</div>
<ul class="menue">
	<li id="selected6"><a href="manageMember.jspx?gid=${gid }">窝窝成员</a></li>
	<li id="selected7"><a href="applyMember.jspx?gid=${gid}">成员审批</a></li>
</ul>
