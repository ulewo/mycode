<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/blog.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="blogtit">最新博客</div>
		<div id="bloglist">
			<c:forEach var="blog" items="${result.list}">
			<div class="blogitem">
				<div class="user_icon"><a href="${realPath}/user/${blog.userId}"><img src="${realPath}/upload/${blog.userIcon}"></a></div>
				<div class="blog_con">
					<div class="blog_title"><a href="${realPath}/user/${blog.userId}/blog/${blog.id}">${blog.title}</a></div>
					<div class="blog_summary">${blog.summary}</div>
					<div class="user_time">
						<span>${blog.userName}</span>
						<span>${blog.postTime}</span>
						<span>阅读(${blog.readCount})</span>
						<span>回复(${blog.reCount})</span>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			</c:forEach>
		</div>
		<div class="pagination">
			<p:pager url="" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
		</div>
  </div>
  <%@ include file="common/foot.jsp" %>
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