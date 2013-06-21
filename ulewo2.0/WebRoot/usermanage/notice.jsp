<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.manage.public.css">
<style type="text/css">
#selected7 a{background:#ffffff;color:#333333;font-weight:bold;}
.notice_list{padding:8px;height:15px;}
.notice_list a{color:#4466BB;font-weight:bold;}
.notice_list div{display:inline-block;float:left;}
.notice_list span{display:inline-block;float:right;padding-right:10px;color:#666666}
.selectAll{height:30px;margin-left:8px;margin-top:10px;}
.select{float:left;width:18px;padding-top:3px;}
.selecttit{float:left;width:30px;margin-left:5px;padding-top:5px;}
.selectbtn{float:left;}
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
					<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt; 消息管理
			</div>
			<div class="selectAll">
				<div class="select"><input type="checkbox" onclick="selectAll(this)" id="checkAll"></div>
				<div class="selecttit">全选</div>
				<div class="selectbtn bbtn1" ><a href="javascript:readNotic()" class="btn">标记为已读</a></div>
			</div>
		<form action="${realPath}/manage/readNotice" method="post" id="myform">
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
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.manage.userinfo.js"></script>
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
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>