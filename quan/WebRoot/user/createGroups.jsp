<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
		#sel_left2 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left2 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
 <%@ include file="../common/head.jsp" %>
<div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
	  <div class="right">
	  	  <div class="navPath"><a href="userInfo.jspx?userId=${userId}">空间</a>&nbsp;&gt;&gt;&nbsp;创建的窝窝</div>
	  	  <div class="user_main">
		  	<c:forEach var="group" items="${createGroups}">
		  		<div class="g_gcon">
		  			<div class="g_gon_img"><a href="../group/group.jspx?gid=${group.id}"><img src="../upload/${group.groupIcon}" width="60" border="0"></a></div>
		  			<div class="g_gon_info">
		  				<div><a href="../group/group.jspx?gid=${group.id}">${group.groupName}</a></div>
		  				<div>成员:${group.members}</div>
		  				<div>人气:${group.visitCount}</div>
		  			</div>
		  		</div>
		  	</c:forEach>
  		   	<div style="clear:left;"></div>
  		  </div>
	  </div>
	  <div class="clear"></div>
</div>
 <jsp:include page="../common/foot.jsp"/>
</body>
</html>
