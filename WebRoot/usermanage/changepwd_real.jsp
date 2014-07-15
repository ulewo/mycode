<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/easyui_common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${realPath}/js/user.manage.changepwd.js?version=2.5"></script>

<title>修改密码-有乐窝</title>
</head>
<body>
	<form id="pwd_form" class="easyform">
		<table>
			<tr>
				<td class="rightclumn" width="100">旧密码</td>
				<td width="200"><input class="textinput" type="password" name="oldpwd" id="oldpwd"/></td>
			</tr>
			<tr>
				<td class="rightclumn">新密码</td>
				<td><input class="textinput" type="password" name="newpwd" id="newpwd"/></td>
			</tr>
			<tr>
				<td class="rightclumn">再次输入新密码</td>
				<td><input class="textinput" type="password" name="anewpwd" id="anewpwd"/></td>
			</tr>
			<tr>
				<td></td>
				<td><a href="javascript:changePwd.saveInfo()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">修改密码</a></td>
			</tr>
		</table>
	</form>
</body>
</html>