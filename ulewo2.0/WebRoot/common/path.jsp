<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String port = request.getServerPort() == 80 ? "" : ":"+request.getServerPort();
String realPath = "http://" + request.getServerName() + port + request.getContextPath(); 
%>
<c:set var="realPath" value="<%=realPath %>"/>
<script src="${realPath}/js/jquery.min.js"></script>
<script src="${realPath}/js/util.js"></script>
<script src="${realPath}/js/common.js"></script>
<script src="${realPath}/js/emotion.data.js"></script>
<script type="text/javascript" src="http://cbjs.baidu.com/js/m.js"></script>
<link rel="shortcut icon" type="image/x-icon" href="${realPath}/images/favicon.ico">
<script type="text/javascript" src="${realPath}/js/common.head.js"></script>
<script type="text/javascript">
var global={};
global.realPath="${realPath}";
global.userId="${user.userId}";
</script>
