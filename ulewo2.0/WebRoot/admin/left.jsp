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
	<span class="manage_tit1">有乐窝后台管理</span>
	<div class="clear"></div>
</div>
<div class="menue_p">
	<div class="menue_tit">
		文章管理
	</div>
	<ul class="menue">
		<li id="selected1"><a href="${realPath}/admin">所有文章</a></li>
	</ul>
	<div class="menue_tit">
		窝窝管理
	</div>
	<ul class="menue">
		<li id="selected2"><a href="${realPath}/admin/group">所有窝窝</a></li>
	</ul>
	<div class="menue_tit">
		成员管理
	</div>
	<ul class="menue">
		<li id="selected3"><a href="${realPath}/admin/user">窝窝成员</a></li>
	</ul>
</div>    