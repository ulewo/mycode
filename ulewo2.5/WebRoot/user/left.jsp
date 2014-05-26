<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<div class="leftmain">
	<div class="avatar_con">
		<div>
			<div class="user_avatar"><a href="${realPath}/user/${userVo.userId}"><img src="${realPath}/upload/${userVo.userIcon}" id="imgcon" width="60px;" height="60px;"></a></div>
			<div class="user_edit">
				<a href="${realPath}/user/${userVo.userId}" class="username" id="user_name">${userVo.userName}</a>
				<div class="user_edit_info" id="user_edit_info">
					<span>
						<c:if test="${userVo.sex=='M'}">
							<img src="${realPath}/images/men.png">
						</c:if>	
						<c:if test="${userVo.sex=='F'}">
							<img src="${realPath}/images/women.png">
						</c:if>	
					</span>
					<c:if test="${user.userId==userVo.userId}">
						<a href="${realPath}/manage/main#userInfo" class="edit_info">修改资料</a>
						<a href="${realPath}/manage/main#userIcon" class="edit_icon">更换头像</a>
					</c:if>
					<c:if test="${user.userId!=userVo.userId&&!userVo.haveFocus}">
						<a href="javascript:focusUser()" class="edit_info" id="focus_user">关注此人</a>
					</c:if>
					<c:if test="${userVo.haveFocus}">
						<span class="havefocus" id="havefocus">已关注</span>
						<a href="javascript:cancelFocus()" class="edit_info" id="cancel_focus">取消</a>
					</c:if>
					<div class="clear" id="clear"></div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="user_other_info">
			<span>关注(${userVo.focusCount})</span>
			<span>粉丝(${userVo.fansCount})</span>
			<span>积分(<span id="mark">${userVo.mark}</span>)</span>
		</div>
	</div>
	<div class="resume" id="resume">
		${userVo.characters}
		<c:if test="${userVo.characters==null||userVo.characters==''}">
			这个人很懒，啥也没留下
		</c:if>
	</div>
	<div class="opts">
		<c:if test="${user.userId==userVo.userId}">
			<a href="${realPath}/manage/main#new_blog" target="_blank" class="blog">
				<span class="blog_icon"></span>
				<span class="blog_tit">发表博文</span>
			</a>
			<a href="${realPath}/manage/main" target="_blank" class="manage">
				空间管理
			</a>
			<div class="clear"></div>
		</c:if>
	</div>
	<script type="text/javascript">
		var userId = "${userVo.userId}";
		var sessionUser = "${user}";
	</script>
	<script type="text/javascript">
		function focusUser(){
			if(sessionUser==""){
				alert("请先登录");
				return;
			}
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data : {
					"friendUserId":userId
				},
				url : global.realPath+"/user/focusFriend.action",// 请求的action路径
				success : function(data) {
					if(data.result=="200"){
						$("#focus_user").remove();
						$("#clear").before($("<span class='havefocus' id='havefocus'>已关注</span><a href='javascript:cancelFocus()' class='edit_info' id='cancel_focus'>取消</a>"));
					}else{
						alert(data.msg);
					}
				}
			});
		}
		
		function cancelFocus(){
			if(sessionUser==""){
				alert("请先登录");
				return;
			}
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data : {
					"friendUserId":userId
				},
				url : global.realPath+"/user/cancelFocus.action",// 请求的action路径
				success : function(data) {
					if(data.result=="200"){
						$("#cancel_focus").remove();
						$("#havefocus").remove();
						$("#clear").before($("<a href='javascript:focusUser()' class='edit_info' id='focus_user'>关注此人</a>"));
					}else{
						alert(data.msg);
					}
				}
			});
		}
	</script>
</div>