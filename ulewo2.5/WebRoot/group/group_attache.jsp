<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName} 资源-有乐窝</title>
<meta name="description" content="${group.groupName} 资源">
<meta name="keywords" content="${group.groupName} 资源">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.index.css?version=2.5">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${group.gid}">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${group.gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${group.gid}/attachedFile" class="tag_select">资&nbsp;&nbsp;源</a></li>
				<li><a href="${realPath}/group/${group.gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<div id="article_item_list" class="article_item_list" style="margin-top:20px;">
			<c:forEach var="article" items="${result.list}">
				<div class="article_item">
					<div class="article_tit">
						<div class="article_title">
							<a href="${realPath}/group/${group.gid}/topic/${article.topicId}">${article.topicTitle}</a>
							<a href="${realPath}/group/${group.gid}/topic/${article.topicId}#attached" style="font-size:12px;color:red;margin-left:10px;">下载</a>
							<div class="clear"></div>
						</div>
						<div class="article_author">
							<span class="article_posttime">发表于 ${article.createTime}</span>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</c:forEach>
			</div>
			<c:if test="${empty result.list}">
					<div class="noinfo">没有任何附件！</div>
			</c:if>
			<div class="pagination">
				<p:pager url="${realPath}/group/${gid}/attachedFile" page="${articles.page}" pageTotal = "${articles.pageTotal }"></p:pager>
			</div>
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