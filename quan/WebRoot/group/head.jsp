<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<script type="text/javascript" src="../js/group.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.lhl.util.ConnManage" %>

<div>
	<img src="../upload/${group.groupHeadIcon}">
</div>
<div class="menue">
	<ul>
	<li><a href="group.jspx?gid=${gid }" <c:if test="${showType==1}">class="selected"</c:if>>圈子首页</a></li>
	<li><a href="topics.jspx?gid=${gid }" <c:if test="${showType==2}">class="selected"</c:if>><span>话&nbsp;&nbsp;题</span></a></li>
	<li><a href="members.jspx?gid=${gid }" <c:if test="${showType==3}">class="selected"</c:if>><span>成&nbsp;&nbsp;员</span></a></li>
	</ul>
</div>
