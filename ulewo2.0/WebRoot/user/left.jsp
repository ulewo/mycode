<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="leftmain">
	<div class="avatar_con">
		<div>
			<div class="user_avatar"><a href="userInfo.jspx?userId=${uid}"><img src="/../images/default.gif" id="imgcon" width="60px;" height="60px;"></a></div>
			<div class="user_edit">
				<a href="userInfo.jspx?userId=${uid}" class="username" id="user_name">${userVo.userName}</a>
				<div class="user_edit_info">
					<span><img src="${realPath}/images/men.png"></span>
					<c:if test="${user.userId==userVo.userId}">
						<a href="" class="edit_info">修改资料</a>
						<a href="" class="edit_icon">更换头像</a>
					</c:if>
					<c:if test="${user.userId!=userVo.userId}">
						<a href="javascript:void(0)" class="edit_info" id="focus_user">关注此人</a>
					</c:if>
					<div class="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="user_other_info">
			<span>关注(0)</span>
			<span>粉丝(0)</span>
			<span>积分(0)</span>
		</div>
	</div>
	<div class="resume" id="resume">
	</div>
	<div class="opts">
		<a href="addBlog.jspx?userId=${userId}" class="blog">
			<span class="blog_icon"></span>
			<span class="blog_tit">发表博文</span>
		</a>
		<a href="/manage/userinfo" class="manage">
			空间管理
		</a>
		<div class="clear"></div>
		<a href="message.jsp?userId=${uid}" class="sendMsg">
			发送留言
		</a>
	</div>
	<script type="text/javascript">
		var userId = "${userId}"||"${param.userId}";
	</script>
</div>