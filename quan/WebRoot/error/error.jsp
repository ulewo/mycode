<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"  href="../css/group.css" type="text/css"  />
<title>出错啦!  -有乐窝</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
<body>
  <jsp:include page="../common/head.jsp"/>
  <div class="bodycon">
  		<div>
  			<div class="error_img"><img src="../images/error.jpg"></div>
  			<div class="error_info">
  				<span class="tit">出现此页面可能是由于以下原因引起的：</span>
				<span>1、登录超时</span>
				<span>2、你访问的内容被删除</span>
				<span>3、你没有权限进行此操作</span>
				<span>4、服务器异常</span>
				<span><a href="../index.jspx">返回首页</a></span>
  			</div>
  			<div class="clear"></div>
  		</div>
  </div>
  <jsp:include page="../common/foot.jsp"/>
</body>
</html>