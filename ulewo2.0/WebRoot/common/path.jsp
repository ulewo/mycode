<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String realPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"; 
%>
<c:set var="realPath" value="<%=realPath %>"/>
<c:set var="myPath" value="http://my.ulewo.com/"/>
<c:set var="groupPath" value="http://group.ulewo.com/"/>
<script src="${realPath}js/util.js"></script>
<script src="${realPath}js/jquery.min.js"></script>
<script type="text/javascript">
var global={};
global.realPath="{${realPath}}";
</script>
