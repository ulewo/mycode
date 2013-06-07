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
#selected1{background:#ffffff}
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
				<div class="info_item_tit">全站唯一ID</div>
			 	<div class="info_item_text info_item_text_t">${userVo.userId}</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">邮箱</div>
			 	<div class="info_item_text info_item_text_t">${userVo.email}</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">用户名</div>
			 	<div class="info_item_text info_item_text_t">${userVo.userName}</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">性别</div>
			 	<div class="info_item_text info_item_text_t">
			 		<input type="radio" name="sex" value="M" <c:if test="${userVo.sex=='M'}">checked</c:if>>男
			 		<input type="radio" name="sex" value="F" <c:if test="${userVo.sex=='F'}">checked</c:if>>女
			 	</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">年龄</div>
			 	<div class="info_item_text"><input class="editinput" type="text" name="age" value="${userVo.age}"/></div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">居住地</div>
			 	<div class="info_item_text"><input class="editinput" type="text" name="address" value="${userVo.address}"/></div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">职业</div>
			 	<div class="info_item_text"><input class="editinput" type="text" name="work" value="${userVo.work}"/></div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit">个性签名<br>不超过100字</div>
			 	<div class="info_item_text">
			 		<textarea>${userVo.characters}</textarea>
			 	</div>
			 	<div class="clear"></div>
			</div>
			<div class="info_item">
			 	<div class="info_item_tit"></div>
			 	<div class="info_item_text">
			 		<a href="" class="btn">保存修改</a>
			 	</div>
			 	<div class="clear"></div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<%@ include file="../common/foot.jsp" %>
</body>
</html>