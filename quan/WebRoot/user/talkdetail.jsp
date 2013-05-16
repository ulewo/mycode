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
	<script type="text/javascript">
	  	var gloableParam={};
	  	gloableParam.userId = "${param.userId}";
 	 </script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<link rel="stylesheet" type="text/css" href="../css/moretalk.css">
	<script type="text/javascript" src="../js/user.talkdetail.js"></script>
	<script type="text/javascript" src="../js/talk.js"></script>
	<style type="text/css">
		#sel_left6 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left6 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <script type="text/javascript">
  	var GlobalParam = {};
  	GlobalParam.talkId="${param.talkId}";
  </script>
  <jsp:include page="../common/head.jsp"/>
   <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
  	<div class="right">
  		<div class="navPath">
  			<a href="userInfo.jspx?userId=${param.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;吐槽
  		</div>
  		<div>
  			<div class="talkitem">
  				<div class="itemicon">
  					<img src="../upload/${talk.userIcon}" width="37">
  				</div>
  				<div class="itemcon">
  					<span class="item_user"><a href="userInfo.jspx?userId=${talk.userId}">${talk.userName}</a></span>
  					<span class="item_content">：${talk.content}</span>
  					<c:if test="${talk.imgurl!=''&&talk.imgurl!=null}">
  						<div class="talkimg">
  							<img src="../upload/${talk.imgurl}">
  						</div>
  					</c:if>
  					<div>
  						<span class="detail_item_time">${talk.createTime}</span>
  						<span class="detail_item_recount">评论(${talk.reCount})</span>
  					</div>
  				</div>
  				<div class="clear"></div>
  			</div>
  			<div class="u_talk" id="u_talk_textarea_con">
  				<input type="hidden" id="hide_atuserId">
  				<input type="hidden" id="hide_atuserName">
	  			<div class="u_talk_textarea"><textarea id="talkcontent"></textarea></div>
	  			<div class="u_talk_sub">
	  				<div class="u_talk_subtn">
	  					<a href="javascript:void(0)" id="talkBtn">评&nbsp;&nbsp;论</a>
	  					<img src="../images/load.gif" id="talkload" style="display:none;">
	  				</div>
	  				<div class="clear"></div>
	  			</div>
	  		</div>
  		</div>
  		<div class="re_line">评论：</div>
  		<div id="talklist">
  		
  		</div>
  		
 	</div>
 	<div style="clear:left;"></div>
 	</div>
    <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
