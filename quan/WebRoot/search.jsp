<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ page import="com.lhl.entity.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	    String keyWord = String.valueOf(request.getAttribute("keyWord"));
	    keyWord = URLEncoder.encode(URLEncoder.encode(keyWord, "utf-8"), "utf-8");
		String url = "search.jspx?keyWord="+keyWord;
%>
<html>
  <head>
    <title>搜索 --有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/u8.square.css">
	<link rel="stylesheet" type="text/css" href="css/u8.search.css">
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script>
		function searchAll(){
			var keyword = $("#keyWord").val();
			if(keyword==""){
				art.dialog.tips("请输入关键字！");
				return;
			}
			keyword = encodeURI(encodeURI(keyword)); 
			document.location.href="search.jspx?keyWord="+keyword;
		}
		$(function(){
			$("#keyWord").bind("keydown",function(event){
				event = event||window.event;
				var code=event.keyCode;
				if(code==13){//如果是回车键
					searchAll();
				}
			});
		});
		
		
	</script>
  </head>
  <body>
   <jsp:include page="common/head.jsp"/>
  <div class="bodycon">
  	 	<div class="searchArea">
  	 		<div class="searchInput"><input type="text" name="keyWord" id="keyWord" class="keyWordInput" value="${keyWord}"></div>
  	 		<div class="searchBtnDiv"><a href="javascript:searchAll()" onFocus="this.blur()" class="searchBtn"><img src="images/searchbtn.gif" border="0"></a></div>
  	 	</div>
  	 	<div  class="pagination" style="margin-top:20px;height:20px;">
			<p:pager url="<%=url%>" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
  	 	</div>
  	 	<div style="clear:right;"></div>
  	 	<div class="searResult">
  	 		<c:set var="num" value="0"></c:set>
  	 		<c:forEach var="article" items="${articleList}">
				<div class="mainlist_con <c:if test='${num%2==0}'>mainlist_con_color</c:if>">
					<div class="mainlist_con_title"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
					<div class="mainlist_con_username"><a href="">${article.authorName}</a>&nbsp;&nbsp;发表于：${fn:substring(article.postTime,0,10)}</div>
					<div class="mainlist_con_summary">${article.summary}(${article.reNumber}评论)</div>
				</div>
				<c:set var="num" value="${num+1}" />
			</c:forEach>
  	 	</div>
  	 	<div  class="pagination" style="margin-top:20px;height:30px;">
			<p:pager url="<%=url%>" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
  	 	</div>
  	 	<div style="clear:right;"></div>
  </div>
  <jsp:include page="common/foot.jsp"/>
  </body>
</html>
