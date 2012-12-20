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
  <jsp:include page="menue.jsp"></jsp:include>
  <div class="bodycon">
  <div class="admin_main">
  <div class="main_th">
  	<div class="main_title">标题</div>
  	<div class="main_time">发表时间</div>
  	<div class="main_option">操作</div>
  </div>
  	<c:forEach var="article" items="${articleList}">
  		<div class="main_tr">
  			<div class="main_title">
  				<c:if test="${article.image!=null&&article.image!=''}">
  					<img src="../images/image_s.gif">
  				</c:if>	
  				<a href="../group/post.jspx?id=${article.id}">${article.title}</a>&nbsp;&nbsp;&nbsp;<span>${article.reNumber}/${article.readNumber}</span>
	  				<c:if test="${article.subCode=='rollimage'}">
	  					<span style="color:red;">已推荐(滚动图片)</span>
	  				</c:if>	
	  				<c:if test="${article.subCode=='groupthing'}">
	  					<span style="color:red;">已推荐(推荐文章)</span>
	  				</c:if>	
	  				<c:if test="${article.subCode=='latest'}">
	  					<span style="color:red;">已推荐(最新文章)</span>
	  				</c:if>	
  				</div>
  			<div class="main_time">${fn:substring(article.postTime,0,16)}</div>
  			<div class="main_option"><a href="">删除</a>&nbsp;&nbsp;<a href="javascript:commend('${article.id}')">推荐</a></div>
  			<div style="clear:left;"></div>
  		</div>
  	</c:forEach>
  </div>
  	<div class="pagination">
		<p:pager url="queryArticles.jspx" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
	</div>
	</div>
  </body>
</html>
