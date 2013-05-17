<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ page import="com.lhl.entity.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>友吧广场--有乐窝</title>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/u8.square.css">
	<style>
		.selected3{background:#1C91BE;}
	</style>
  </head>
  
  <body>
   <%@ include file="common/head.jsp" %>
  <div class="bodycon">
  	<div style="margin-top:10px;padding-right:8px;height:25px;">
  		<div  class="pagination">
			<p:pager url="square.jspx" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
 		</div>
  	</div>
  	<div>
  	<div class="part">
	  	<c:forEach var="article" items="${square1}">
	  		<div class="single">
	  			<div class="single_top"></div>
	  			<div class="single_center">
		  			<div class="single_tit"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
		  			<c:if test="${article.image!=null&&article.image!=''}">
		  				<div class="single_img"><img src="upload/${article.image}"/></div>
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
		  			<div class="single_tit"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
		  			<c:if test="${article.image!=null&&article.image!=''}">
		  				<div class="single_img"><img src="upload/${article.image}"/></div>
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
		  			<div class="single_tit"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
		  			<c:if test="${article.image!=null&&article.image!=''}">
		  				<div class="single_img"><img src="upload/${article.image}"/></div>
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
		  			<div class="single_tit"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
		  			<c:if test="${article.image!=null&&article.image!=''}">
		  				<div class="single_img"><img src="upload/${article.image}"/></div>
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
  	<div  class="pagination" style="margin-top:10px;padding-right:8px;">
		<p:pager url="square.jspx" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
 	</div>
  </div>
   <jsp:include page="common/foot.jsp"/>
  </body>
</html>
