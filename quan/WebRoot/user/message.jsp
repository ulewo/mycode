<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${userInfo.userName }的空间--有乐窝</title>
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
		  	<div class="topblog">
		  		
		  		<div class="topblog_titcon"><span class="topblog_tit">留言板</span></div>
		  		<div class="messagelist"  id="messagelist">
		  		</div>
		  		<!-- 
		  		<div class="messagelist"  id="messagelist">
				  	<c:forEach var="message" items="${messageList}">
				  	<div class="main_message">
				  		<div><span class="message_name">
				  				<c:if test="${message.reUserId!=null&&message.reUserId!=''}">
									<a href="userInfo.jspx?userId=${message.reUserId}">${message.reUserName }</a>
								</c:if>
				  				<c:if test="${message.reUserId==null||message.reUserId==''}">
				  					${message.reUserName }
				  				</c:if>
				  			</span>&nbsp;&nbsp;&nbsp;&nbsp;发表于：${fn:substring(message.postTime,0,10)}
				  			</div>
				  		<div class="message_con">${message.message }</div>
				  	</div>	
				  	</c:forEach>
		  		</div>
		  		<c:if test="${empty messageList}" >
		  			<span class="nomessage">尚无任何留言</span>
		  		</c:if>
		  		 -->
		  	</div>
		  	<div  class="pagination" style="float:none;margin-top:10px;width:auto;display:none;">
			</div>
		  	<div class="subArea">
		  		<form id="subform">
		  		<input type="hidden" name="userId" value="${param.userId }" id="userId">
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
		  			<div class="bbtn1">
		  				<a href="javascript:submitForm()" onfocus="this.blur()" id="sendBtn">发表留言</a>
		  				<img src="../images/load.gif" id="loading" style="display:none">
		  			</div>
		  			<div style="margin-left:20px;padding-top:8px;float:left;">最多输入500字符</div>
		  		</div>
		  		
		  		</form>
		  	</div>
		</div>
	<div style="clear:left;"></div>
  </div>
   <jsp:include page="../common/foot.jsp"/>
  </body>
  <script type="text/javascript">
  	$(function(){
  		initMessage("${param.userId}");
  	});
  	var sessionUserId ="${user.userId}";
    window.onload = function(){
        var url = document.location.href;
        var at = url.lastIndexOf("#");
       if(at!=-1){
     	  location.href = url.substring(url.indexOf("#"));
       }
     }
  </script>
</html>
