<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>窝窝成员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
	<link rel="stylesheet"  href="../css/group.article.css" type="text/css"  />
	<style type="text/css">
		#sel_left2 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left2 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
<%@ include file="../common/head.jsp" %>
	<div class="bodycon">
	  <jsp:include page="head.jsp"/>
  		<div class="members">
		<c:forEach var="member" items="${memberList}">
	  		<div class="g_gcon">
	  			<div class="g_gon_img"><a href="../user/userInfo.jspx?userId=${member.userId}" target="_blank"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
	  			<div class="g_gon_info">
	  				<div><a href="../user/userInfo.jspx?userId=${member.userId}" target="_blank">${member.userName}</a></div>
	  				<div>发帖:${member.topicCount}</div>
	  				<div>加入时间:${member.joinTime}</div>
	  			</div>
	  		</div>
	  	</c:forEach>
	  	<div style="clear:left;"></div>
	  	</div>
	  	<div class="pagebttom">
		    <div  class="pagination">
				<p:pager url="members.jspx?gid=${gid}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
			</div>
		</div>
	</div>
	<jsp:include page="../common/foot.jsp"/>
  </body>
</html>
