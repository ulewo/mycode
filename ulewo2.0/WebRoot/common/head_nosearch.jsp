<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div id="head">
	<div class="head_con">
		<div style="float:left;width:120px;"><a href="${realPath}"><img src="${realPath}/images/logo.png"></a></div>
		<ul class="left">
			<li><a href="${realPath}" class="selected1" onFocus="this.blur()">首&nbsp;页</a></li>
			<li><a  href="${realPath}/group/all"  class="selected2" onFocus="this.blur()">窝&nbsp;窝</a></li>
			<li><a  href="${realPath}/blog"  class="selected3" onFocus="this.blur()">博&nbsp;客</a></li>
			<li><a href="${realPath}/square" class="selected4" onFocus="this.blur()">广&nbsp;场</a></li>
		</ul>
		<div class="head_app"><a href="${realPath}/ulewoapp" target="_blank" title="android客户端"><img src="${realPath}/images/android.gif"></a></div>
		<ul class="head_right">
			<c:choose>
				<c:when test="${user==null}">
					<li><a href="javascript:goto_register()" onFocus="this.blur()" class="n_m">注册</a></li>
					<li><a href="javascript:goto_login()" onFocus="this.blur()" class="n_m">登录</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="javascript:logout()" onFocus="this.blur()" class="n_m">退出</a></li>
					<li><a href="javascript:void(0)" onFocus="this.blur()" class="h_m">窝窝</a></li>
					<li><a href="${realPath}/manage/notice" onFocus="this.blur()" class="h_m">消息<span id="notice"></span></a></li>
					<li><a href="${realPath}/user/${user.userId}" onFocus="this.blur()" class="h_m" title="${user.userName}">${user.userName}</a></li>
				</c:otherwise>
			</c:choose>
			</ul>
		<div class="head_box" id="head_box">
			
		</div>
	</div>
</div>
