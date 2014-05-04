<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<script type="text/javascript">
<!--
global.gid = "${gid}";
//-->
</script>
<div class="group_info">
	<div class="group_info_left">
		<div>
			<div class="group_icon"><img src="${realPath}/upload/${group.groupIcon}"></div>
		<div class="group_info_con">
			<div class="group_title">
				${group.groupName}
				<c:if test="${user.userId==group.groupUserId}">
					<a href="${realPath}/groupmanage/${group.gid}" class="btn" target="_blank">管理窝窝</a>
				</c:if>
			</div>
			<div class="group_author">
				<span class="group_info_tit" style="padding-left:0px;">管理员:</span><a href="${realPath}/user/${group.groupUserId}">${group.authorName}</a> 
				<span class="group_info_tit">成员:</span><span id="memberCount">${group.members}</span>
				<span class="group_info_tit">创建时间:</span>${group.showCreateTime}
			</div>
			<div class="group_url">
				<div class="group_url_con">http://ulewo.com/group/${group.gid}</div>
				<c:if test="${user.userId!=group.groupUserId}">
					<div class="group_joinstatus" id="group_joinstatus">
							<c:if test="${group.memberStatus==''}"><a href="javascript:void(0)" class="btn" id="joinBtn">+立即加入</a></c:if>
							<c:if test="${group.memberStatus=='Y'}"><span class="joined" id="joined">已加入|<a href="javascript:void(0)" id="existBtn">退出</a></span></c:if>
							<c:if test="${group.memberStatus=='N'}"><span class="apply">已申请加入,等待管理员的审核</span></c:if>
					</div>
				</c:if>
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<div class="group_desc">${group.groupDesc}</div>
	</div>
	<div class="group_info_notice">
		<div class="right_tit">公告</div>
		<div class="group_notice">
			<c:if test="${group.groupNotice==null||group.groupNotice==''}">
				<div class="group_notice_noinfo">
					暂无公告
				</div>
			</c:if>
			${group.groupNotice}
		</div>
	</div>
	<div class="clear"></div>
	<script type="text/javascript" src="${realPath}/js/group.public.js"></script>
</div>
		