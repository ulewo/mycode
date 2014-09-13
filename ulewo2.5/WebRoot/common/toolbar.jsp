﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"  href="${realPath}/css/toolbar.css?vsersion=2.6" type="text/css"  />
<script src="${realPath}/js/toolbar.js?vsersion=2.6"></script>
<div class="tool-bar" id="tool-bar">
	<c:if test="${user!=null}">
		<div class="tool-bar-item tool-user-info" id="tool-user" style="height:50px;background:url(${realPath}/upload/${user.userIcon})">
		</div>
	</c:if>
	<c:if test="${user==null}">
		<div class="tool-bar-item tool-user" id="tool-user">
			<div class="tool-icon user-icon"></div>
			<div class="item-title">用户</div>
		</div>
	</c:if>
	<div class="tool-bar-item tool-post-topic" id="tool-post-topic">
		<div class="tool-icon topic-icon"></div>
		<div class="item-title">发帖</div>
	</div>
	<div class="tool-bar-item tool-group" id="tool-group">
		<div class="tool-icon group-icon"></div>
		<div class="item-title">窝窝</div>
	</div>
	<div class="tool-bar-item tool-notice" id="tool-notice">
		<div class="tool-icon notice-icon"></div>
		<div class="item-title">消息</div>
		<div class="notice-info-count" id="notice-info-count"></div>
	</div>
	<div id="gototop" class="tool-bar-item" onclick="window.scrollTo(0,0);">
		<div class="tool-icon gototop-icon"></div>
	</div>
</div>
<div style="position:fixed;right:5px;z-index:99;bottom:50px;width:90px;">
		<img src="${realPath}/images/qrcode.png" noLazyload="true">
		<div style="color:#494949;margin-top:10px;">扫描下载有乐窝客户端</div>
</div>
<div class="tool-bar-con" id="tool-bar-con">
	<c:if test="${user!=null}">
		<div class="tool-bar-con-sub" id="tool-user-con"></div>
		<div class="tool-bar-con-sub" id="tool-post-topic-con">
			<iframe width="850" height="800" frameborder="0" id="fastpost"></iframe>
		</div>
		<div class="tool-bar-con-sub" id="tool-group-con"></div>
		<div class="tool-bar-con-sub" id="tool-notice-con"></div>
		<div id="bar-con-loading"><img src="${realPath}/images/load.gif">正在加载信息......</div>
		<div id="bar-con-close"><a href="javascript:void(0)"></a></div>
	</c:if>
	<c:if test="${user==null}">
		<div style="padding:10px;">
				<a href="javascript:goto_login()" class='bigbtn'>登陆</a>
				<a href="javascript:goto_register()" class='bigbtn'>注册</a>
		</div>
	</c:if>
</div>
