<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="shortcut icon" type="image/x-icon" href="${realPath}images/favicon.ico">
<script type="text/javascript" src="${realPath}js/common.head.js"></script>

<div class="head_top">
	<div class="head_logo">
		<a href="${realPath}index.jspx" onfocus="this.blur()"><img src="${realPath}images/logo.png" border="0" width="200"></a>
	</div>
	<div class="android_logo"><a href="${realPath}ulewoapp.jsp"><img src="${realPath}images/andriod_logo.png" border="0"></a></div>
	<div class="head_user">
		<c:choose>
			<c:when test="${user==null}">
				<div class="head_s_p"><a href="javascript:createWoWo()">创建窝窝</a></div><div class="head_s_p">|</div>
				<div class="head_s_p"><a href="javascript:register()">注册</a></div><div class="head_s_p">|</div>
				<div class="head_s_p"><a href="javascript:login()">登录</a></div><div class="head_s_p">|</div>
				<div class="head_s_p welcom">游客，欢迎您</div>
			</c:when>
			<c:otherwise>
				<div class="head_s_p"><a href="javascript:void(0)" onclick="logout()">退出</a></div><div class="head_s_p">|</div>
				<div class="head_s_p"><a href="javascript:createWoWo()">创建窝窝</a></div ><div class="head_s_p">|</div>
				<div class="head_s_p myspace"><a href="${realPath}user/userInfo.jspx?userId=${user.userId}" id="myspace">我的空间</a></div>
				<div class="head_s_p welcom">
					<span class="welcom_name" title="${user.userName}">
						<script type="text/javascript">
						 var username = "${user.userName}";
						 document.write(username.cutString(8));
						</script>
					</span>，欢迎您</div>
			</c:otherwise>
		</c:choose>
		<div class="clear"></div>
	</div>
	<div class="head_s_p tool_panle" id="other_info">
		<ul>
			<li><a href="${realPath}user/userInfo.jspx?userId=${user.userId}">个人中心</a></li>
			<li><a href="${realPath}user/addBlog.jspx?userId=${user.userId}">发表博文</a></li>
			<li><a href="${realPath}user/createdGroups.jspx?userId=${user.userId}">我的窝窝</a></li>
			<li><a href="${realPath}user/joinedGroups.jspx?userId=${user.userId}">加入的窝窝</a></li>
			<li><a href="${realPath}user/postTopics.jspx?userId=${user.userId}">发表的文章</a></li>
			<li><a href="${realPath}user/reTopics.jspx?userId=${user.userId}">回复的文章</a></li>
		</ul>
	</div>
	<div class="notice_panle" id="notice_panle">
		<div class="tip-arrow"></div>
		<div class="tip_con"></div>
	</div>
</div>
<div id="head">
	<div class="head_con">
		<ul class="left">
			<li><a href="${realPath}index.jspx" class="selected1" onFocus="this.blur()">首&nbsp;页</a></li>
			<li><a  href="${realPath}groups.jspx"  class="selected2" onFocus="this.blur()">窝&nbsp;窝</a></li>
			<li><a href="${realPath}square.jspx" class="selected3" onFocus="this.blur()">广&nbsp;场</a></li>
		</ul>
		<div class="head_search">
			<input type="button" class="head_search_btn" value="搜索一下" onclick="search()">
			<input type="text" class="head_search_input" id="searchInput" name="keyWord">
			<div class="clear"></div>
		</div>
	</div>
</div>
