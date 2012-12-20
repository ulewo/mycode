<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${userInfo.userName }的空间--友吧中国</title>
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
  <div class="bodycon">
  <jsp:include page="menue.jsp"></jsp:include>
  <div class="user_main">
	  <div class="left"><jsp:include page="user_left.jsp"></jsp:include></div>
	  <div class="right">
	  	<div class="baseinfo">
		  	性别：${userInfo.sex }<br>
		  	年龄：${userInfo.age }<br>
		  	个人签名:${userInfo.characters}<br>
		  	职业：${userInfo.work}<br>
		  	地址：${userInfo.address }<br>
		  	注册时间：${fn:substring(userInfo.registerTime,0,10) }<br>
	  	</div>
	  	<div>
	  		<form>
	  		<input type="hidden" name="userId" value="${userId }" id="userId">
	  		<div class="u_name">用户名：
	  			<c:if test="${user==null}">
	  				<input type="text" name="reUserName" id="name">
	  			</c:if>
	  			<c:if test="${user!=null}">
	  				<input type="text" name="reUserName" id="name" value="${user.userName}" disabled="disabled" > 
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
	  			<div class="bbtn1"><a href="javascript:submitForm()" onfocus="this.blur()">发表留言</a></div>
	  			<div style="margin-left:20px;padding-top:8px;float:left;">最多输入500字符</div>
	  		</div>
	  		
	  		</form>
	  	</div>
	  	<div class="messagetit">留言</div>
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
	  </div>
 	 <div style="clear:left;"></div>
  </div>
  </div>
   <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
