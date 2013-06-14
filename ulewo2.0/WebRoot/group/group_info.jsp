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
				<c:if test="${user.userId==group.groupAuthor}">
					<a href="${realpath}/groupManage/${gid}/manage" class="btn">管理窝窝</a>
				</c:if>
			</div>
			<div class="group_author">
				<span class="group_info_tit" style="padding-left:0px;">管理员:</span><a href="${realPath}/user/${group.groupAuthor}">${group.authorName}</a> 
				<span class="group_info_tit">成员:</span>${group.members} 
				<span class="group_info_tit">创建时间:</span>${group.createTime}
			</div>
			<div class="group_url">http://ulewo.com/group/${group.id}&nbsp;&nbsp;<a href="" class="btn">+立即加入</a></div>
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
</div>
		