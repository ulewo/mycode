<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝-有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.allgroup.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<c:forEach var="group" items="${result.list}">
			<div class="group_item">
				<div class="group_icon">
					<img  src="${realPath}/upload/${group.groupIcon}" width="50">
				</div>
				<div class="group_con">
					<div class="group_tit">
						<div class="group_title"><a href="${group.id}">${group.groupName}</a></div>
						<!-- <div class="group_op"><a href="" class="btn">+加入窝窝</a></div> -->
						<div class="clear"></div>
					</div>
					<div class="group_info">
						创建者：<a href="${realPath}/user/${group.groupAuthor}">${group.authorName}</a>&nbsp;|&nbsp;
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
		<%@ include file="../common/foot.jsp" %>
	</div>
	<div class="righ_ad">
		<div><a href="javascript:void(0)" onclick="$('.righ_ad').hide()">关闭</a></div>
		<div>
		<script type="text/javascript">
			/*160*600，创建于2013-7-10*/
			var cpro_id = "u1317726";
			</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  		</div>
  </div>
</body>
</html>