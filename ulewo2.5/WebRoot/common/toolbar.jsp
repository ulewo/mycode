﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"  href="${realPath}/css/toolbar.css?vsersion=2.5" type="text/css"  />
<script src="${realPath}/js/toolbar.js?vsersion=2.5"></script>
<div class="tool-bar" id="tool-bar">
	<c:if test="${user!=null}">
		<div class="tool-bar-item tool-user-info">
			<img src="${realPath}/upload/${user.userIcon}">
		</div>
	</c:if>
	<c:if test="${user==null}">
		<div class="tool-bar-item tool-user">
			<div class="tool-icon user-icon"></div>
			<div class="item-title">用户</div>
		</div>
	</c:if>
	<div class="tool-bar-item tool-post-topic" id="tool-post-topic">
		<div class="tool-icon topic-icon"></div>
		<div class="item-title">发帖</div>
	</div>
	<div class="tool-bar-item tool-group">
		<div class="tool-icon group-icon"></div>
		<div class="item-title">窝窝</div>
	</div>
	<div class="tool-bar-item tool-notice">
		<div class="tool-icon notice-icon"></div>
		<div class="item-title">消息</div>
		<div class="notice-info-count">20</div>
	</div>
	<div id="gototop" class="tool-bar-item">
		<div class="tool-icon gototop-icon" onclick="window.scrollTo(0,0);"></div>
	</div>
</div>
<div class="tool-bar-con" id="tool-bar-con">

</div>
