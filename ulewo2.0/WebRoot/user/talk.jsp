<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户中心-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.usercenter.css">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
	  <div class="left">
	  	<%@ include file="left.jsp" %>
	  	<div class="left_item">
	  		<div class="left_item_tit">关注</div>
	  		<div class="left_img_p">
	  			<div>
		  			<c:forEach var="friend" items="${focusList}">
		  				<div class="left_img_item"><a href="${realPath}/user/${friend.friendId}" title="${friend.friendName}"><img src="${friend.friendIcon}" width="40"></a></div>
		  			</c:forEach>
		  			<c:if test="${empty focusList}">
		  				<div class="left_noinfo">尚未关注其他人</div>
		  			</c:if>	
		  			<div class="clear"></div>
	  			</div>
	  			<!-- 
	  			<div class="left_img_p_more"><a href="">显示所有关注(${userVo.focusCount})</a></div>
	  			 -->
	  		</div>
	  	</div>
	  	<div class="left_item">
	  		<div class="left_item_tit">粉丝</div>
	  		<div class="left_img_p">
	  			<div>
		  			<c:forEach var="friend" items="${fansList}">
		  				<div class="left_img_item"><a href="${realPath}/user/${friend.friendId}" title="${friend.friendName}"><img src="${friend.friendIcon}" width="40"></a></div>
		  			</c:forEach>
		  			<c:if test="${empty fansList}">
		  				<div class="left_noinfo">尚无粉丝，精彩分享才能吸引关注</div>
		  			</c:if>
		  			<div class="clear"></div>
	  			</div>
	  			<!-- 
	  			<div class="left_img_p_more"><a href="">显示所有粉丝(${userVo.fansCount})</a></div>
	  			 -->
	  		</div>
	  	</div>
	  	<div class="left_item">
	  		<div class="left_item_tit">Ta创建的窝窝</div>
	  		<div class="left_img_p">
	  			<div>
	  			<c:forEach var="group" items="${createdGroups}">
	  				<div class="left_img_item"><a href="${realPath}/group/${group.id}" title="${group.groupName}"><img src="${group.groupIcon}" width="40"></a></div>
	  			</c:forEach>
	  			<c:if test="${empty createdGroups}">
		  			<div class="left_noinfo">没有创建任何窝窝</div>
		  		</c:if>
	  			<div class="clear"></div>
	  			</div>
	  		</div>
	  	</div>
	  	<div class="left_item">
	  		<div class="left_item_tit">Ta加入的窝窝</div>
	  		<div class="left_img_p">
	  			<div>
	  			<c:forEach var="group" items="${joinedGroups}">
	  				<div class="left_img_item"><a href="${realPath}/group/${group.id}" title="${group.groupName}"><img src="${group.groupIcon}" width="40"></a></div>
	  			</c:forEach>
	  			<c:if test="${empty joinedGroups}">
		  			<div class="left_noinfo">没有加入任何窝窝</div>
		  		</c:if>
	  			<div class="clear"></div>
	  			</div>
	  		</div>
	  	</div>
	  	
	  </div>
	  <div class="right">
		  	<div class="topblog">
		  		<div class="topblog_titcon">
		  			<span class="topblog_tit">最新吐槽</span>
		  			<span class="topblog_link"><a href="${realPath}/user/${userVo.userId}/blog">更多吐槽&gt;&gt;</a></span>
		  			<div class="clear"></div>
		  		</div>
				<div id="talklist">
		  			<c:forEach var="talk" items="${talkList}">
			  			<div class="talkitem">
			  				<div class="itemicon">
			  					<img src="${talk.userIcon}" width="37">
			  				</div>
			  				<div class="itemcon">
			  					<span class="item_user">
			  						<a href="${realPath}/user/${talk.userId}">${talk.userName}</a>
			  					</span>
			  					<span class="item_content">：${talk.content}</span>
			  					<span class="item_time">${talk.content}<a href="http://ulewo.cloudfoundry.com:80/user/talkDetail.jspx?userId=10001&amp;talkId=107">(${talk.reCount}评)</a>
			  					</span>
			  				</div>
			  				<div class="clear"></div>
			  			</div>
		  			</c:forEach>
		  			<c:if test="${empty bloglist}">
		  				<div class="left_noinfo">没有发现吐槽</div>
		  			</c:if>
				</div>
		  	</div>
		</div>
	<div style="clear:left;"></div>
  </div>
  <script type="text/javascript" src="${realPath}/js/talk.js"></script>
  <script type="text/javascript" src="${realPath}/js/user.home.js"></script>
  <%@ include file="../common/foot.jsp" %>
</body>
</html>