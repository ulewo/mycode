<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}css/group.index.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="group_info">
			<div class="group_info_left">
				<div>
					<div class="group_icon"><img src="../upload/${group.groupIcon}"></div>
					<div class="group_info_con">
						<div class="group_title">${group.groupName}</div>
						<div class="group_author">
							<span class="group_info_tit" style="padding-left:0px;">管理员:</span><a href="">${group.authorName}</a> 
							<span class="group_info_tit">成员:</span>${group.members} 
							<span class="group_info_tit">创建时间:</span>${group.createTime}
						</div>
						<div class="group_url">http://group.ulewo.com/${group.id}&nbsp;&nbsp;<a href="" class="btn">+立即加入</a></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="group_desc">${group.groupDesc}</div>
			</div>
			<div class="group_info_notice">
				<div class="right_tit">公告</div>
				<div class="group_notic">暂无公告。</div>
			</div>
			<div class="clear"></div>
		</div>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}${gid}">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}${gid}/img" class="tag_select">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}${gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
		</div>
		<%@ include file="../common/foot_manage.jsp" %>
	</div>
</body>
</html>