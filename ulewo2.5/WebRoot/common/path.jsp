<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String port = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
String realPath = "http://" + request.getServerName() + port + request.getContextPath(); 
%>
<c:set var="realPath" value="<%=realPath %>"/>
<script type="text/javascript">
var global={};
global.realPath="${realPath}";
global.userId="${user.userId}";
</script>
<script src="${realPath}/js/jquery.min.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/jquery.lazyload.js?version=2.6"></script>
<script src="${realPath}/js/util.js?version=2.5"></script>
<script src="${realPath}/js/common.js?version=2.5"></script>
<script src="${realPath}/js/emotion.data.js?version=2.5"></script>
<link rel="shortcut icon" type="image/x-icon" href="${realPath}/images/favicon.ico?version=2.5">
<script type="text/javascript" src="${realPath}/js/common.head.js?version=2.7"></script>
<script type="text/javascript" src="${realPath}/js/ulewo.common.js?version=2.5"></script>
<script>
$(function(){
	$("img").lazyload({ 
		placeholder: global.realPath+"/images/grey.gif", 
		effect:"fadeIn", 
		failurelimit: 10 
	})
});
</script>

