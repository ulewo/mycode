<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<meta name="description" content="${group.groupName}">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.index.css?version=2.5">
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>
<script type="text/javascript" src="${realPath}/ueditor/ueditor.config.js?version=2.5.1"></script>
<script type="text/javascript" src="${realPath}/ueditor/ueditor.all.js?version=2.5.1"></script>
<script type="text/javascript" src="${realPath}/ueditor/ueditor.parse.js?version=2.5.1"></script>
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="form_editcontent" id="editor">

		</div>
	</div>
	<%@ include file="common/foot.jsp" %>
	<script type="text/javascript">
	var editor = UE.getEditor('editor');
	</script>
</body>
</html>