<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String  realPath  =  "http://"  +  request.getServerName()  +  ":"  +  request.getServerPort()  +  request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script>
var realPath ="http://pic.ulewo.com/";
</script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/share.js"></script>
<link rel="stylesheet" type="text/css" href="css/common.css" />
<div class="head">
	<div class="head_top">
		<c:if test="${user!=null}">
			<span class="head_top_user">${user.userName}</span>
			<a href="user?uid=${user.uid}">个人中心</a>
			<a href="javascript:quit()">退出</a>
		</c:if>
		<c:if test="${user==null}">
			<a href="login.jsp">登录</a>
			<a href="register.jsp">注册</a>
		</c:if>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
</div>
<div class="index_menue">
	<div class="menue_con">
		<ul id="top_menue">
			<li id="top_menue_first"><a href="day" class="sel1">热门</a></li>
			<li><a href="new" class="sel2">最新</a></li>
			<li><a href="hot" class="sel3">热评</a></li>
			<li><a href="pic" class="sel4">有图有真相</a></li>
			<li><a href="addarticle.jsp" class="sel5">投稿</a></li>
			<li><a href="http://blog.ulewo.com" class="sel6">站长博客</a></li>
		</ul>
		<div class="search">
			<input type="text" name="searchKey" id="searchKey" onkeypress="subFormEnter()" value="${searchKey}">
			<input type="button" value="找你想要" onclick="subForm()">
		</div>
		<ul id="time_select">
			<li><a href="day">24小时内</a></li>
			<li><a href="week">一星期内</a></li>
			<li><a href="month">一个月内</a></li>
			<li><a href="year">一年内</a></li>
		</ul>
	</div>
	
</div>
<script>
	function quit(){
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : 'quit',// 请求的action路径
			success : function(data) {
				if (data.result == "success") {
					window.location.reload();
				} else {
					alert("系统异常稍后再试");
				}
			}
		});
	}
	function subForm(){
		var searchKey = $("#searchKey").val()
		if(searchKey!=""){
			searchKey = encodeURI(encodeURI(searchKey)); 
			document.location.href="search?searchKey="+searchKey;
		}
	}
	function subFormEnter(){
		var theEvent = window.event || e; 
		var code = theEvent.keyCode || theEvent.which; 
		if (code == 13&&$("#searchKey").val()!="") { 
			subForm();
		} 
	}
</script>
