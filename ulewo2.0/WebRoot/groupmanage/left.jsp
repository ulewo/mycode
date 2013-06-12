<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript">
<!--
global.gid="${gid}";
//-->
</script>
<div class="manage_tit">
	<span class="manage_tit1">窝窝管理</span>
	<span class="manage_tit2"><a href="${realPath}/group/${gid}">返回窝窝</a></span>
</div>
<div class="menue_p">
	<div class="menue_tit">
		基本设置
	</div>
	<ul class="menue">
		<li id="selected1"><a href="${realPath}/groupManage/${gid}/manage">窝窝设置</a></li>
		<li id="selected2"><a href="changepwd">修改头像</a></li>
		<li id="selected3"><a href="${realPath}/groupManage/${gid}/manage/group_notice">公告管理</a></li>
	</ul>
	<div class="menue_tit">
		 文章管理
	</div>
	<ul class="menue">
		<li id="selected4"><a href="">文章管理</a></li>
		<li id="selected5"><a href="manage/group_reply">评论管理</a></li>
		<li id="selected6"><a href="${realPath}/groupManage/${gid}/manage/group_item">分类管理</a></li>
	</ul>
	
	<div class="menue_tit">
		成员管理
	</div>
	<ul class="menue">
		<li id="selected7"><a href="notice.jspx">窝窝成员</a></li>
		<li id="selected8"><a href="notice.jspx">成员审批</a></li>
	</ul>
</div>    
