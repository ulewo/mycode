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
#selected6 a{background:#FFFFFF;}
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
		<div class="navPath"><a href="userInfo.jspx?userId=${user.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;消息</div>
		<c:choose>
			<c:when test="${empty list}">
				<div>没有未处理的消息。</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="notice" items="${list}">
					<div class="notice_list">
						<div><a href="noticeDetail.jspx?id=${notice.id}">${notice.content}</a></div>
						<span>${fn:substring(notice.postTime,0,16)}</span>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		
	</div>
	<div class="clear"></div>
</div>
 <jsp:include page="../common/foot.jsp"/>
</body>
</html>