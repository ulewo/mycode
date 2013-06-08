<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人博客-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}css/group.allgroup.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<c:forEach var="group" items="${result.list}">
			<div class="group_item">
				<div class="group_icon">
					<img  src="../${group.groupIcon}">
				</div>
				<div class="group_con">
					<div class="group_tit">
						<div class="group_title"><a href="${group.id}">${group.groupName}</a></div>
						<div class="group_op"><a href="" class="btn">+加入窝窝</a></div>
						<div class="clear"></div>
					</div>
					<div class="group_info">
						创建者：<a href="user/userInfo.jspx?userId=10001">${group.authorName}</a>&nbsp;|&nbsp;
						成员数：<span class="group_blue">${group.members}</span>&nbsp;|&nbsp;
						文章数：<span class="group_blue">${group.topicCount}</span>
					</div>
					<div class="group_content">${group.groupDesc}</div>
				</div>
				<div class="clear"></div>
			</div>
		</c:forEach>
		<div class="pagination">
			<p:pager url="" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
		</div>
	</div>
</body>
</html>