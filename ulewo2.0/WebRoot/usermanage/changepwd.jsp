<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆有乐窝-有乐窝</title>
<link rel="stylesheet" type="text/css" href="../css/user.manage.userinfo.css">
<style type="text/css">
#selected2{background:#ffffff}
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right">
			<div class="right_top_m">
					<a href="">空间</a>&gt;&gt;<a href="blog">博客</a>
					<c:if test="${blogitem!=null}">
						&gt;&gt;<a href="">${blogitem.itemName}</a>
					</c:if>
			</div>
			<div class="info_item">
				<div class="info_item_tit">旧密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="address" value="${userVo.address}"/></div>
			 	<div class="info_item_info">请输入旧密码</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
				<div class="info_item_tit">新密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="address" value="${userVo.address}"/></div>
			 	<div class="info_item_info">密码长度6-16位字符，由数字、字母组成</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
				<div class="info_item_tit">再次输入新密码</div>
			 	<div class="info_item_text"><input class="editinput" type="password" name="address" value="${userVo.address}"/></div>
			 		<div class="info_item_info">请再次输入新密码</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit"></div>
			 	<div class="info_item_text">
			 		<a href="" class="btn">修改密码</a>
			 	</div>
			 	<div class="clear"></div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot.jsp" %>
</body>
</html>