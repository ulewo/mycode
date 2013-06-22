<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>吐槽-有乐窝</title>
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
		  				<div class="left_img_item"><a href="${realPath}/user/${friend.friendId}" title="${friend.friendName}"><img src="${realPath}/upload/${friend.friendIcon}" width="40"></a></div>
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
		  				<div class="left_img_item"><a href="${realPath}/user/${friend.friendId}" title="${friend.friendName}"><img src="${realPath}/upload/${friend.friendIcon}" width="40"></a></div>
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
	  				<div class="left_img_item"><a href="${realPath}/group/${group.id}" title="${group.groupName}"><img src="${realPath}/upload/${group.groupIcon}" width="40"></a></div>
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
	  				<div class="left_img_item"><a href="${realPath}/group/${group.id}" title="${group.groupName}"><img src="${realPath}/upload/${group.groupIcon}" width="40"></a></div>
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
		  		<div class="right_top_m">
					<a href="${realPath}/user/${userId}">空间</a>&gt;&gt;<a href="${realPath}/user/${userId}/talk">吐槽</a>
				</div>
				<div id="talklist">
  				</div>
		  		<a href="javascript:void(0)" id="loadmorebtn">加载更多</a>
				<div id="loading" ><img src="${realPath}/images/load.gif" ></div>
		</div>
	<div style="clear:left;"></div>
  </div>
  <script type="text/javascript">
  var userId="${userId}";
  </script>
  <script type="text/javascript" src="${realPath}/js/talk.js"></script>
  <script type="text/javascript" src="${realPath}/js/user.talk.js"></script>
  <%@ include file="../common/foot.jsp" %>
</body>
</html>