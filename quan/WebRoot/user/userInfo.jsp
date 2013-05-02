<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${userVo.userName }的空间--有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
	<script type="text/javascript" src="../js/user.userinfo.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
	<script src="../dialog/plugins/iframeTools.js"></script>
	<style type="text/css">
	#sel_left1 a{background:url(../images/bg.gif) 0px -85px;}
	#sel_left1 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
  <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
	  <div class="right">
		  	<div class="baseinfoCon">
			  	<span class="base_tit">加入时间：</span><span class="base_info">${userVo.registerTime}</span><br>
			  	<span class="base_tit">最近登录：</span><span class="base_info">${userVo.previsitTime}</span><br>
			  	<span class="base_tit">性别：</span><span class="base_info">
			  		<c:choose>
                   		<c:when test="${userVo.sex =='M' }">男</c:when>
                   		<c:when test="${userVo.sex =='F' }">女</c:when>
                   		<c:otherwise>未知</c:otherwise>
                  	</c:choose>
			  	</span><br>
			  	<span class="base_tit">职业：</span><span class="base_info">
			  		<c:choose>
                   		<c:when test="${!empty userVo.work}">${userVo.work}</c:when>
                   		<c:otherwise>未知</c:otherwise>
                  	</c:choose>	
			  	</span><br>
			  	<span class="base_tit">地址：</span><span class="base_info">
			  		<c:choose>
                   		<c:when test="${!empty userVo.address}">${userVo.address}</c:when>
                   		<c:otherwise>未知</c:otherwise>
                  	</c:choose>
			  		</span><br>
			  	<span class="base_tit">积分：</span><span class="base_info">${userVo.mark}</span><br>
		  	</div>
		  	<div class="topblog">
		  		<div class="topblog_titcon"><span class="topblog_tit">最新博文</span><span class="topblog_link"><a href="blog.jspx?userId=${userId}" target="_blank">进入博客</a></span></div>
		  		<c:forEach var="blog" items="${blogList}">
		  			<div class="blog_link">
		  				<a href="blogdetail.jspx?id=${blog.id}" target="_blank">${blog.title}</a><span>${blog.reCount}/${blog.readCount}</span>
		  			</div>
		  		</c:forEach>
		  		<c:if test="${empty blogList}">
		  			<span>尚无发表博文</span>
		  		</c:if>
		  	</div>
		  	<div class="topblog">
		  		<div class="topblog_titcon"><span class="topblog_tit">最新留言</span><span class="topblog_link"><a href="message.jsp?userId=${userId}" target="_blank">进入留言板</a></span></div>
		  		<div class="messagelist"  id="messagelist">
			  	<c:forEach var="message" items="${messageList}">
			  	<div class="main_message">
			  		<div class="re_icon">
				  		<c:if test="${message.reUserId!=null&&message.reUserId!=''}">
							<img src="../upload/${message.reUserIcon }" width="37" style="border:1px solid #9B9B9B">
						</c:if>
		  				<c:if test="${message.reUserId==null||message.reUserId==''}">
		  					<img src="../upload/default.gif" width="37" style="border:1px solid #9B9B9B">
		  				</c:if>
			  		</div>
			  		<div class="re_info">
			  			<div class="note_name_time">
			  				<span class="message_name">
				  				<c:if test="${message.reUserId!=null&&message.reUserId!=''}">
									<a href="userInfo.jspx?userId=${message.reUserId}">${message.reUserName }</a>
								</c:if>
				  				<c:if test="${message.reUserId==null||message.reUserId==''}">
				  					${message.reUserName }
				  				</c:if>
			  				</span>
			  				<c:if test="${message.atUserId!=''}">
			  					<span style="color:#008000">回复</span>
			  					<span class="message_name">
									<a href="userInfo.jspx?userId=${message.atUserId}">${message.atUserName }</a>
			  					</span>
			  				</c:if>
			  				<span class="note_time nofirst">发表于：${message.postTime}</span>
			  			</div>
			  			<div class="re_content">${message.message }</div>
			  		</div>
			  		<div class="clear"></div>
			  	</div>	
			  	</c:forEach>
		  	</div>
		  		<c:if test="${empty messageList}">
		  			<span>尚无任何留言</span>
		  		</c:if>
		  	</div>
		</div>
	<div style="clear:left;"></div>
  </div>
   <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
