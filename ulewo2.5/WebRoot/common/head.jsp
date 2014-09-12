<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
global.type = "${type}";
</script>
<div id="top">
	<div class="top_center">
		<div class="top_mobile"><a href="${realPath}/ulewoapp">手机窝窝</a></div>
		<div class="top_user_con">
			<c:choose>
				<c:when test="${user==null}">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<td><a href="javascript:goto_login()">登录</a></td>
							<td><div class="top_line"></div></td>
							<td><a href="javascript:goto_register()">注册</a></td>
						</tr>
					</table>
				</c:when>
				<c:otherwise>
				<table cellpadding="0" cellspacing="0" class="top_user_table">
						<tr>
							<td style="padding-top:2px;padding-right:8px">${user.userName}，欢迎回来</td>
							<td style="padding:0px 2px;"><img src="${realPath}/upload/${user.userIcon}" width="20"></td>
							<td><div class="top_line"></div></td>
							<td><a href="javascript:logout()" onFocus="this.blur()" class="n_m">退出</a></td>
						</tr>
				</table>
				</c:otherwise>
			</c:choose>
			<div class="head_box" id="head_box">
			
			</div>
		</div>
	</div>
</div>
<div id="top_search">
	<div class="top_search_center" style="position:relative;">
		<div class="top_logo">
				<a href="http://www.ulewo.com"><img alt="www.ulewo.com" src="${realPath}/images/logo.png" noLazyload="true"></a>
		</div>
		<div class="top_search_con" style="float:right;margin-right:8px;">
			<div class="search_con">
				<input type="text" id="searchInput" name="keyWord" value="${keyWord}">
				<a href="javascript:search()">搜&nbsp;索</a>
			</div>
			<div class="hotsearch">
				<span>热门搜索：</span>
				<a href="javascript:search('CSS视频')">跟我一起学CSS</a>
				<a href="javascript:search('java该如何自学')">java该如何自学</a>
				<a href="javascript:search('java编程规范')">java编程规范</a>
				<a href="javascript:search('eclipse的使用')">eclipse的使用</a>
				<a href="javascript:search('java')">java</a>
				<a href="javascript:search('自学')">自学</a>
				<a href="javascript:search('源码')">源码</a>
				<a href="javascript:search('视频')">视频</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>
<div id="head">
	<div class="head_con">
		<ul class="left">
			<li><a href="${realPath}" class="selected1" onFocus="this.blur()">首&nbsp;页</a></li>
			<li><a  href="${realPath}/group/all"  class="selected2" onFocus="this.blur()">窝&nbsp;窝</a></li>
			<li><a  href="${realPath}/blog"  class="selected3" onFocus="this.blur()">博&nbsp;客</a></li>
			<li><a href="${realPath}/square" class="selected4" onFocus="this.blur()">广&nbsp;场</a></li>
			<li><a href="${realPath}/resource" class="selected5" onFocus="this.blur()">资&nbsp;源</a></li>
		</ul>
	</div>
</div>
