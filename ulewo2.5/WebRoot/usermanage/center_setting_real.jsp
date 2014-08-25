<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/easyui_common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${realPath}/js/user.manage.theme.js?version=2.5"></script>
<title>修改用户信息-有乐窝</title>
</head>
<body>
	<form id="user_form" class="easyform" style="width:90%">
		<table>
			<tr>
				<td class="rightclumn" width="10%">屏幕宽度</td>
				<td width="45%" align="center"><label for="style01" style="width:100%"><input type="radio" id="style01" name="theme" <c:if test="${theme==0}">checked="checked"</c:if>  value="0"/>标准版</label></td>
				<td width="45%" align="center"><label for="style02" style="width:100%"><input type="radio" id="style02" name="theme" <c:if test="${theme==1}">checked="checked"</c:if> value="1"/>宽屏版</label></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td valign="top"><a href="${realPath}/images/style01.png" target="_blank"><img width="100%" src="${realPath}/images/style01.png"></a></td>
				<td valign="top"><a href="${realPath}/images/style02.png" target="_blank"><img width="100%" src="${realPath}/images/style02.png"></a></td>
			</tr>
		</table>
	</form>
</body>
</html>