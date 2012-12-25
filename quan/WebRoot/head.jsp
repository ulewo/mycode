<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link id="artDialog-skin" href="dialog/skins/default.css" rel="stylesheet" />
<script src="dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="dialog/plugins/iframeTools.js"></script>
<script src="js/u8.head.js"></script>
<script src="js/util.js"></script>
<div id="head">
	<div class="head_con">
		<div class="logo"><img src="images/logo.gif"></div>
		<ul class="left">
			<li><a href="index.jspx" class="selected1" onFocus="this.blur()">首&nbsp;页</a></li>
			<li><a  href="groups.jspx"  class="selected2" onFocus="this.blur()">友&nbsp;吧</a></li>
			<li><a href="square.jspx" class="selected3" onFocus="this.blur()">广&nbsp;场</a></li>
			<li><a href="http://blog.ulewo.com" class="selected4" onFocus="this.blur()">乐哈哈</a></li>
		</ul>
		<div class="search">
			<input type="text" class="inputTxtNew" id="searchInput" name="keyWord">
			<input type="button" class="inputBtn" value="搜索" onclick="search('index')">
		</div>
		<div class="user">
			<c:if test="${user!=null}">
				<ul id="userName">
					<li><a href="javascript:void(0)" title="${user.userName}">
						<script>
							document.write("${user.userName}".cutString(6));
						</script>
						</a>
						<ul>
							<li><a href="user/userInfo.jspx?userId=${user.userId}">个人中心</a></li>
							<li><a href="user/getInfo.jspx?userId=${user.userId}">设置</a></li>
							<li><a href="user/createdGroups.jspx?userId=${user.userId}">我的圈子</a></li>
							<li><a href="user/joinedGroups.jspx?userId=${user.userId}">加入的圈子</a></li>
							<li><a href="group/creategroup.jsp">创建圈子</a></li>
							<li><a href="javascript:void(0)" onclick="logout(1)">退出</a></li>
						</ul>
					</li>
				</ul>
				
			</c:if>
			<c:if test="${user==null}">
				<ul id="userName">
					<li><a href="javascript:void(0)">游客</a>
						<ul>
							<li><a href="javascript:void(0)" onclick="showLoginDilog('user/login.jsp')">登录</a></li>
							<li><a href="user/register.jsp">注册</a></li>
						</ul>
					</li>
				</ul>
			</c:if>	
		</div>
	</div>
</div>
