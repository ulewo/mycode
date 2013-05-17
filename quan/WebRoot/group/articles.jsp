<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<meta name="description" content="${group.groupName}-有乐窝">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet"  href="../css/group.article.css" type="text/css"  />
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/group.index.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/artDialog.ext.js"></script>
</head>
<body>
<%@ include file="../common/head.jsp" %>
<div class="bodycon">
	<jsp:include page="head.jsp"/>
	<div class="itemcon">
		<div class="itemcon_item"><jsp:include page="items.jsp"/></div>
		<div class="itemcon_btn">
			<div class="bbtn2"><a href="javascript:addGroup()">+加入圈子</a></div>
			<div class="bbtn1"><a href="javascript:addArticle()">◎发表话题</a></div>
		</div>
	</div>
	<div class="ordercon">
		<div class="ordercon_order">
			排序：全部&nbsp;|&nbsp;精华&nbsp;|&nbsp;置顶&nbsp;|&nbsp;按时间&nbsp;|&nbsp;按人气
		</div>
		<div class="ordercon_page">
			 <div  class="pagination">
				<p:pager url="topics.jspx?gid=${gid}&itemId=${itemId}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
			</div>
		</div>
	</div>
	<div class="ath">
		<div class="atit">话题</div>
		<div class="aauthor">发表</div>
		<div class="aauthor">最后回应</div>
	</div>
	<c:forEach var="article" items="${articleList}">
		<div class="atr">
			<div class="atit">
				<c:if test="${article.grade==2}"><div class="atit_img"><img src="../images/ico-top.gif"></div></c:if>
				<c:if test="${article.essence=='Y'}"><div class="atit_img2"><img src="../images/ico-ess.gif"></div></c:if>
				<div class="atit_tit">
				<c:if test="${article.itemName!=null}">【<a href="topics.jspx?gid=${group.id}&itemId=${article.itemId }">${article.itemName }</a>】</c:if>
				<c:if test="${article.itemName==null}">【<a href="topics.jspx?gid=${group.id}">全部话题</a>】</c:if>
				<a href="post.jspx?id=${article.id}" ${article.titleStyle} target="_blank">${article.title }</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#999999">${article.reNumber }/${article.readNumber}</span>
				</div>
			</div>
			<div class="aauthor">
				<a href="../user/userInfo.jspx?userId=${article.authorId}" target="_blank">${article.authorName}</a><br>
				<span class="timestyle">${article.postTime}</span>
			</div>
			<div class="aauthor">
				<c:if test="${article.lastReAuthorId!=null}">
					<a href="../user/userInfo.jspx?userId=${article.lastReAuthorId}" target="_blank">${article.lastReAuthorName}</a><br>
				</c:if>
				<c:if test="${article.lastReAuthorId==null}">
					${article.lastReAuthorName }<br>
				</c:if>
				<span class="timestyle">${article.postTime}</span>
			</div>
			<div class="clear"></div>
		</div>
	</c:forEach>
	<div class="pagebttom">
	    <div  class="pagination">
			<p:pager url="topics.jspx?gid=${gid}&itemId=${itemId}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
		</div>
	</div>
</div>	
<jsp:include page="../common/foot.jsp"/>
<%
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"; 
%>
<script type="text/javascript">
	initGroupParam("<%=realPath1%>","${user.userId}","${gid}");
</script>
</body>
</html>