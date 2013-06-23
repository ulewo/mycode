<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝成员审批-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.manage.member.css">
<style type="text/css">
#selected8 a{background:#ffffff;color:#333333;font-weight:bold;}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="member_con">
			<c:forEach var="member" items="${result.list}">
				<div class="member_item">
						<div class="member_icon"><a href="${realPath}/user/${member.userId}"><img src="${member.userIcon}"></a></div>
						<div class="member_info">
							<div class="member_name"><a href="${realPath}/user/${member.userId}">${member.userName}</a></div>
							<div class="article_count">主题:<span>${member.topicCount}</span></div>
							<div class="jointime">加入时间:<span>${member.joinTime}</span></div>
						</div>
				</div>
			</c:forEach>
			<div class="clear"></div>
			</div>
			<div class="pagination">
				<p:pager url="${realPath}/groupManage/${gid}/manage/member" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>