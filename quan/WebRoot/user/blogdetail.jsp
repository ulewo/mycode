<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${blogArticle.title}--有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/user.userinfo.js"></script>
	<script type="text/javascript" src="../js/user.blogdetail.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
		#sel_left6 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left6 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
   <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
  	<div class="right">
  		<div class="navPath">
  			<a href="userInfo.jspx?userId=${userId}">空间</a>&nbsp;&gt;&gt;&nbsp;<a href="blog.jspx?userId=${userId}">博客</a>
  			&nbsp;&gt;&gt;&nbsp;<a href="blog.jspx?userId=${userId}&itemId=${blogItem.id}">${blogItem.itemName}</a>&nbsp;&gt;&gt;&nbsp;博客正文
  		</div>
  		
  		<div class="blog_title">${blogArticle.title}</div>
  		<div class="blogsta">
  			<div class="blogsta_info">发表于(${fn:substring(blogArticle.postTime,0,19)})， 已有<span>${blogArticle.readCount}</span>次阅读 ，共<span>${blogArticle.reCount}</span>个评论
  			</div>
  			<c:if test="${user!=null&&userId==user.userId}">
	  			<div class="blogsta_op">
	  				<a href="getEditinfo.jspx?id=${blogArticle.id}">编辑</a>&nbsp;|&nbsp;<a href="javascript:confirmDel()">删除</a>
	  			</div>
  			</c:if>
  			<div class="clear"></div>
  		</div>	
  		<div class="blogdetail">${blogArticle.content}</div>
  		 
  		 <div class="topblog">
		  		<div class="topblog_titcon" style="height:20px;"><span class="topblog_tit">评论</span></div>
		  		<div class="messagelist"  id="messagelist">
				  	
		  		</div>
		  	</div>
		  	<div>
		  		<form id="subform"> 
		  		<input type="hidden" name="userId" value="${userId }" id="userId">
		  		<div class="u_name">
		  			<c:if test="${user==null}">
		  				用户名：<input type="text" name="reUserName" id="name">
		  			</c:if>
		  		</div>
		  		<div class="content"><textarea rows="10" cols="80" name="content" id="content"></textarea></div>
		  		<c:if test="${user==null}">
			  		<div class="checkcode">
			  			<div class="tit">验证码：</div>
						<div class="check_con">
							<input type="text" class="long_input" name="checkCode" id="checkCode"/>
						</div>
						<div class="check_img">
							<a href="JavaScript:refreshcode();" onfocus="this.blur();"><img id="checkCodeImage" src="../common/image.jsp" border="0"/></a>
						</div>
						<div class="changecode">
							<a href="javascript:refreshcode()">换一张</a>
						</div>
			  		</div>
		  		</c:if>
		  		<div class="subbtn">
		  			<div class="bbtn1"><a href="javascript:subReply('${param.id}')" onfocus="this.blur()">发表留言</a></div>
		  			<div style="margin-left:20px;padding-top:8px;float:left;">最多输入500字符</div>
		  		</div>
		  		
		  		</form>
		  	</div>
  		 
 	</div>
 	<div style="clear:left;"></div>
 	</div>
    <jsp:include page="../common/foot.jsp"/>
    <script type="text/javascript">
    var userId = "${userId}"
    var sessionUserId ="${user.userId}";
    var title = "${blogArticle.title}";
    var blogauthor  =  "${blogArticle.userId}";
    $(function(){
    	loadReply("${param.id}");
    })
    function confirmDel(){
    	if(confirm("文章删除后无法回复，你确定要删除此文章？")){
    		document.location.href="deleteBlog.jspx?id=${blogArticle.id}";
    	}
    }
    window.onload = function(){
       var url = document.location.href;
       var at = url.lastIndexOf("#");
      if(at!=-1){
    	  location.href = url.substring(url.indexOf("#"));
      }
    }
    </script>
  </body>
</html>
