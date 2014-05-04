<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName} 成员-有乐窝</title>
<meta name="description" content="${group.groupName}">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.member.css?version=2.5">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${group.gid}">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${group.gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${group.gid}/attachedFile">资&nbsp;&nbsp;源</a></li>
				<li><a href="${realPath}/group/${group.gid}/member" class="tag_select">成&nbsp;&nbsp;员</a></li>
			</ul>
			<div class="member_con">
				<c:forEach var="member" items="${result.list}">
					<div class="member_item">
						<div class="member_icon"><a href="${realPath}/user/${member.userId}"><img src="${realPath}/upload/${member.userIcon}"></a></div>
						<div class="member_info">
							<div class="member_name"><a href="${realPath}/user/${member.userId}">${member.userName}</a></div>
							<div class="article_count">主题:<span>${member.topicCount}</span></div>
							<div class="jointime">加入时间:<span>${member.showJoinTime}</span></div>
						</div>
					</div>
				</c:forEach>
				<div class="clear"></div>
			</div>
			<div class="pagination">
				<p:pager url="${realPath}/group/${group.gid}/member" page="${result.page.page}" pageTotal = "${result.page.pageTotal }"></p:pager>
			</div>
		</div>
		<%@ include file="../common/foot.jsp" %>
	</div>
	<div class="righ_ad">
		<div><a href="javascript:void(0)" onclick="$('.righ_ad').hide()">关闭</a></div>
		<div>
		<script type="text/javascript">
			/*右侧对联，创建于2013-7-10*/
			var cpro_id = "u1317726";
			</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  		</div>
  	</div>
</body>
</html>