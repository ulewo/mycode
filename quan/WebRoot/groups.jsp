<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ page import="com.lhl.entity.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>窝窝 --有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/u8.square.css">
	<style>
		.selected2{background:#1C91BE;}
	</style>
  </head>
  
  <body>
  <%@ include file="common/head.jsp" %>
  <div class="bodycon">
	  	<c:forEach var="group" items="${groupList}">
	  		<div class="group">
	  			<div class="group_icon">
	  				<a href="group/group.jspx?gid=${group.id}"><img src="upload/${group.groupIcon }" width="64" border="0"></a>
	  			</div>
	  			<div class="group_info">
	  				<div class="group_name"><a href="group/group.jspx?gid=${group.id}">${group.groupName }</a></div>
	  				<div class="group_author">创建者：<a href="user/userInfo.jspx?userId=${group.groupAuthor }">${group.authorName }</a>&nbsp;|&nbsp;成员数：${group.members }人&nbsp;|&nbsp;文章数：${group.topicCount }</div>
	  				<div class="group_desc">${group.groupDesc}</div>
	  			</div>
	  			<div style="clear:left;"></div>
	  		</div>
	  	</c:forEach>
	 <div style="height:30px;">
  	 <div  class="pagination" style="margin-top:20px;">
		<p:pager url="groups.jspx" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
  	 </div>
  	 </div>
  </div>
  <jsp:include page="common/foot.jsp"/>
  </body>
</html>
