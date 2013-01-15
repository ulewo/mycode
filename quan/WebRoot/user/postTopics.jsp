<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
		#sel_left4 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left4 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
  <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
	  <div class="right">
	  	   <div class="navPath"><a href="userInfo.jspx?userId=${userId}">空间</a>&nbsp;&gt;&gt;&nbsp;发表的文章</div>
	  	  <div class="user_main">
		  	<div class="ath">
				<div class="atit">话题</div>
				<div class="aauthor">群组</div>
				<div class="atime">发表</div>
			</div>
			<c:forEach var="article" items="${articleList}">
				<div class="atr">
					<div class="atit">
						<c:if test="${article.grade==1}"><div class="atit_img"><img src="../images/ico-top.gif"></div></c:if>
						<c:if test="${article.essence=='Y'}"><div class="atit_img2"><img src="../images/ico-ess.gif"></div></c:if>
						<div class="atit_tit">
						<c:if test="${article.itemName!=null}">【<a href="../group/topics.jspx?gid=${article.gid}&itemId=${article.itemId }">${article.itemName }</a>】</c:if>
						<c:if test="${article.itemName==null}">【<a href="../group/topics.jspx?gid=${article.gid}">全部话题</a>】</c:if>
						<a href="../group/post.jspx?id=${article.id}" ${article.titleStyle}>${article.title }</a>
						&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#999999">${article.reNumber }/${article.readNumber}</span>
						</div>
					</div>
					<div class="aauthor">
						<a href="../group/group.jspx?gid=${article.gid}">${article.groupName}</a>
					</div>
					<div class="atime">
						<span class="timestyle">${fn:substring(article.postTime,0,16)}</span>
					</div>
				</div>
			</c:forEach>
			<div  class="pagination">
				<p:pager url="postTopics.jspx?userId=${userId}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
			</div>
  		   	<div style="clear:left;"></div>
  		  </div>
	  </div>
	  <div class="clear"></div>
	 </div>
 <jsp:include page="../common/foot.jsp"/>  
  </body>
</html>
