<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>广场 --有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<link rel="stylesheet" href="${realPath}/css/square.css">
<style type="text/css">
.selected4{background:#1C91BE;}
</style>
</head>
<body>
	 <%@ include file="common/head.jsp" %>
  	<div class="main">
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
				  			<div class="single_con">${article.summary}</div>
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
				  			<div class="single_con">${article.summary}</div>
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
				  			<div class="single_con">${article.summary}</div>
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
				  			<div class="single_con">${article.summary}</div>
				  			<div class="readInfo"><span class="read_tit">回复</span><span class="read_count">(${article.reNumber})</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="read_tit">阅读</span><span class="read_count">(${article.readNumber})</span></div>
			  			</div>
			  			<div class="single_bottom"></div>
			  		</div>
			  	</c:forEach>
		  	</div>
		  	<div class="clear"></div>
	  	</div>
	  	<div class="pagination" style="margin-top:10px;">
			<p:pager url="" page="${page}" pageTotal = "${pageTotal }"></p:pager>
		</div>
  	</div>
  	<script type="text/javascript">
  		lazyLoadImage("main");
  	</script>
    <%@ include file="common/foot.jsp" %>
    <div class="righ_ad">
		<div><a href="javascript:$('.righ_ad').hide()">关闭</a></div>
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