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
	<script type="text/javascript" src="../js/util.js"></script>
	<script type="text/javascript" src="../js/user.userinfo.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/user.register.css">
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<style type="text/css">
	#selected2 a{background:#FFFFFF;}
	</style>
  </head>
  <body>
 <%@ include file="../common/head.jsp" %>
   <div class="main">
		<form method="post" id="subform">
		<input type="hidden" name="account" value="${account}">
		<input type="hidden" name="code" value="${code}">
		<div class="input_area">
			<div class="tit">新密码</div>
			<div class="input_con">
				<input type="password" class="long_input" name="newPwd" value="" id="newPwd"/>
			</div>
		</div>
		<div class="input_area">
			<div class="tit">确认密码</div>
			<div class="input_con">
				<input type="password" class="long_input" value="" name="rePassWord" id="rePassWord"/>
			</div>
		</div>
		<div class="input_area" style="text-align:left;padding-left:120px;">
	  		<div><input type="button" class="button" id="subBtn" value="修改密码" onclick="resetpassword()"><img id="loadImg" style="display:none;" src="../images/loading.gif" width="20"></div>
	  		<div id="resultInfo" style="display:none;"></div>
		</div>
		</form>
  </div>
   <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
