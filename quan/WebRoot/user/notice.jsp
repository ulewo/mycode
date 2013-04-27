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
		<div class="selectAll">
			<div class="select"><input type="checkbox" onclick="selectAll(this)" id="checkAll"></div>
			<div class="selecttit">全选</div>
			<div class="selectbtn bbtn1" ><a href="javascript:readNotic()" >标记为已读</a></div>
		</div>
		<form action="readNotice.jspx" method="post" id="myform">
		<c:choose>
			<c:when test="${empty list}">
				<div>没有未处理的消息。</div>
			</c:when>
			<c:otherwise>
				<c:forEach var="notice" items="${list}">
					<div class="notice_list">
						<div><input type="checkbox" name="ids" value="${notice.id}" class="check"></div>
						<div><a href="noticeDetail.jspx?id=${notice.id}">${notice.content}</a></div>
						<span>${notice.postTime}</span>
					</div>
				</c:forEach>
			</c:otherwise>
		</c:choose>
		</form>
	</div>
	<div class="clear"></div>
</div>
 <jsp:include page="../common/foot.jsp"/>
 <script type="text/javascript">
 	$(function(){
 		$(".check").bind("click",function(){
 			var checkeds = $("input[name='ids']:checkbox:checked");
 			if(checkeds.length==$(".check").length){
 				$("#checkAll").attr("checked",true);
 			}else{
 				$("#checkAll").attr("checked",false);
 			}
 		});
 	});
 	function readNotic(){
 		var checkeds = $("input[name='ids']:checkbox:checked");
 		if(checkeds.length==0){
 			alert("请选择要标记的消息");
 			return;
 		}
 		$("#myform").submit();
 	}
 	
 	function selectAll(obj){
 		var check = $(obj).attr("checked");
 		if(check=="checked"){
 			$("input[name='ids']").attr("checked",true); 
 		}else{
 			$("input[name='ids']").attr("checked",false); 
 		}
 	}
 </script>
</body>
</html>