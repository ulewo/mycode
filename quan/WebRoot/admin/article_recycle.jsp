<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>后台管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
  </head>
  <body>
  <jsp:include page="menue.jsp"></jsp:include>
  <div class="admin_main">
  <table>
  	<c:forEach var="article" items="${articleList}">
  		<tr>
  			<td>${article.title}</td>
  		</tr>
  	</c:forEach>
  	</table>
  </div>
  	<div class="pagination">
		<p:pager url="queryArticles.jspx" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
	</div>
  </body>
</html>
