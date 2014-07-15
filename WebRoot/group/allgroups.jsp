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
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.allgroup.css?version=2.5">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
			<div class="group_category">
				<c:forEach var="category" items="${groupCateGroy}" begin="0" end="5">
				<a href="${realPath}/group/all?pCategroyId=${category.groupCategoryId}" class="p_cate">${category.name}</a>
				<div class="category">
						<c:forEach var="sub" items="${category.children}" begin="0" end="3">
							<a href="${realPath}/group/all?categroyId=${sub.groupCategoryId}" <c:if test="${categroyId==sub.groupCategoryId}">class="cate_select"</c:if>>${sub.name}</a>
						</c:forEach>
				</div>
				</c:forEach>
			</div>
			<div class="group_wowo">
				<c:forEach var="group" items="${result.list}">
					<div class="group_item">
						<div class="group_icon">
							<img  src="${realPath}/upload/${group.groupIcon}" width="50">
						</div>
						<div class="group_con">
							<div class="group_tit">
								<div class="group_title"><a href="${group.gid}">${group.groupName}</a></div>
								<!-- <div class="group_op"><a href="" class="btn">+加入窝窝</a></div> -->
								<div class="clear"></div>
							</div>
							<div class="group_info">
								创建者：<a href="${realPath}/user/${group.groupUserId}">${group.authorName}</a>&nbsp;|&nbsp;
								成员数：<span class="group_blue">${group.members}</span>&nbsp;|&nbsp;
								文章数：<span class="group_blue">${group.topicCount}</span>
							</div>
							<div class="group_content">${group.groupDesc}</div>
						</div>
						<div class="clear"></div>
					</div>
				</c:forEach>
				<c:if test="${empty result.list}">
					<div class="noinfo">没有找到相关窝窝</div>
					<div style="text-align:center;margin-top:10px;"><a href="javascript:createWoWo()">立刻创建窝窝，成为窝主?</a></div>
				</c:if>
				<c:set var="url" value="${realPath}/group/all"></c:set>
				<c:if test="${categroyId!=null}">
					<c:set var="url" value="${realPath}/group/all?categroyId=${categroyId}"></c:set>
				</c:if>
				<c:if test="${pCategroyId!=null}">
					<c:set var="url" value="${realPath}/group/all?pCategroyId=${pCategroyId}"></c:set>
				</c:if>
				<div class="pagination">
					<p:pager url="${url}" page="${result.page.page}" pageTotal = "${result.page.pageTotal }"></p:pager>
				</div>
			</div>
			<div class="clear"></div>
	</div>
	<%@ include file="../common/foot.jsp" %>
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