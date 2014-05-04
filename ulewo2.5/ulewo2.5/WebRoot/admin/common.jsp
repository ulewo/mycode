<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String port = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
String realPath = "http://" + request.getServerName() + port + request.getContextPath(); 
%>
<c:set var="realPath" value="<%=realPath %>"/>
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/icon.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
<style>
<!--
body{margin:0px;padding:0px;}
-->
</style>
<script type="text/javascript">
var global={};
global.realPath="${realPath}";
global.userId="${user.userId}";
</script>