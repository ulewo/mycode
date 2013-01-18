<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<style>
	
</style>
<script type="text/javascript" charset="utf-8" src="../js/user.itemmanage.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<link rel="stylesheet"  href="../css/manage.item.css" type="text/css"  />
<link rel="stylesheet"  href="../css/user.public.css" type="text/css"  />
<style type="text/css">
#selected5 a{background:#FFFFFF;}
.atr .aop a{color:#ffffff}
.atr .aop a:hover{color:#ffffff}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="main">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
		<div class="navPath"><a href="userInfo.jspx?userId=${user.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;修改头像</div>
		<div class="ath">
			<div class="atit">分类名称</div>
			<div class="aorder">排&nbsp;序</div>
		</div>
		<c:forEach var="item" items="${itemList}">
			<form action="" method="post">
			<input type="hidden" name="id" value="${item.id}">
			<div class="atr atr2">
				<div class="atit"><input type="text" value="${item.itemName}" name="itemName" style="border:0px" readonly="readonly"></div>
				<div class="aorder"><input type="text" value="${item.itemRang}" name="itemRang" style="border:0px"  readonly="readonly"></div>
				<div class="aop mbt">
					<a href="javascript:void(0)" onclick="editeItem(this)">修改</a>
					<a href="javascript:void(0)" onclick="deleteItem(this)">删除</a>
				</div>
			</div>
			</form>
		</c:forEach>
		<div class="addmore">
			<div class="bbtn1"><a href="javascript:void(0)" onclick="addMore(this,'${gid}')">添加分类</a></div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div class="foot"></div>
</body>
</html>