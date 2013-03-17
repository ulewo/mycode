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
	<link rel="stylesheet" type="text/css" href="../css/admin.css">
	<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
	<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
	<script src="../dialog/plugins/iframeTools.js"></script>
	<script src="../js/admin.article.js"></script>
  </head>
  <body>
  <div class="admin_main">
  <jsp:include page="menue.jsp"></jsp:include>
  	<c:forEach var="imageFile" items="${list}">
  		<div class="imagecon">
  			<div class="imageicon"></div>
  			<div class="directory">${imageFile.fileName}</div>
  		</div>	
  	</c:forEach>
  </div>
  </body>
</html>
