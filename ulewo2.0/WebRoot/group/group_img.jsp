<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName} 图片-有乐窝</title>
<meta name="description" content="${group.groupName}">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.img.css">
<script type="text/javascript">
	$(function(){
		lazyLoadImage("part");
	})
</script>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${gid}">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${gid}/img" class="tag_select">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<div>
		  	<div class="part">
			  	<c:forEach var="article" items="${square1}">
			  		<div class="single">
			  			<div class="single_top"></div>
			  			<div class="single_center">
				  			<div class="single_tit"><a href="${realPath}/group/${article.gid}/topic/${article.id}" target="_blank">${article.title}</a></div>
				  			<c:if test="${article.image!=null&&article.image!=''}">
				  				<div class="single_img"><img src="${article.image}"/></div>
				  			</c:if>
				  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">(${article.reNumber})</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
			  			</div>
			  			<div class="single_bottom"></div>
			  		</div>
			  	</c:forEach>
		  	</div>
		  	<div class="part">
			  	<c:forEach var="article" items="${square2}">
			  		<div class="single">
			  			<div class="single_top"></div>
			  			<div class="single_center">
				  			<div class="single_tit"><a href="${realPath}/group/${article.gid}/topic/${article.id}" target="_blank">${article.title}</a></div>
				  			<c:if test="${article.image!=null&&article.image!=''}">
				  				<div class="single_img"><img src="${article.image}"/></div>
				  			</c:if>
				  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">(${article.reNumber})</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
			  			</div>
			  			<div class="single_bottom"></div>
			  		</div>
			  	</c:forEach>
		  	</div>
		  	<div class="part">
			  	<c:forEach var="article" items="${square3}">
			  		<div class="single">
			  			<div class="single_top"></div>
			  			<div class="single_center">
				  			<div class="single_tit"><a href="${realPath}/group/${article.gid}/topic/${article.id}" target="_blank">${article.title}</a></div>
				  			<c:if test="${article.image!=null&&article.image!=''}">
				  				<div class="single_img"><img src="${article.image}"/></div>
				  			</c:if>
				  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">(${article.reNumber})</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
			  			</div>
			  			<div class="single_bottom"></div>
			  		</div>
			  	</c:forEach>
		  	</div>
		  	<div class="part">
			  	<c:forEach var="article" items="${square4}">
			  		<div class="single">
			  			<div class="single_top"></div>
			  			<div class="single_center">
				  			<div class="single_tit"><a href="${realPath}/group/${article.gid}/topic/${article.id}" target="_blank">${article.title}</a></div>
				  			<c:if test="${article.image!=null&&article.image!=''}">
				  				<div class="single_img"><img src="${article.image}"/></div>
				  			</c:if>
				  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">(${article.reNumber})</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
			  			</div>
			  			<div class="single_bottom"></div>
			  		</div>
			  	</c:forEach>
		  	</div>
		  	<div class="clear"></div>
	  	</div>
		</div>
	  	<div class="pagination" style="margin-top:10px;">
			<p:pager url="" page="${page}" pageTotal = "${pageTotal }"></p:pager>
		</div>
		<%@ include file="../common/foot.jsp" %>
	</div>
</body>
</html>