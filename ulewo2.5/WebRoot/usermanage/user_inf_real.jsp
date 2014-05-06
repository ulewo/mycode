<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/easyui_common.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${realPath}/js/user.manage.userinfo.js?version=2.5"></script>
<title>修改用户信息-有乐窝</title>
</head>
<body>
	<form id="user_form" class="easyform" style="width:60%">
		<table>
			<tr>
				<td class="rightclumn" width="100">全站唯一ID</td>
				<td width="200">${userVo.userId}</td>
			</tr>
			<tr>
				<td class="rightclumn">邮箱</td>
				<td>${userVo.email}</td>
			</tr>
			<tr>
				<td class="rightclumn">用户名</td>
				<td>${userVo.userName}</td>
			</tr>
			<tr>
				<td class="rightclumn">性别</td>
				<td>
				 		<label for="sexM"><input type="radio" id="sexM" name="sex" value="M" <c:if test="${userVo.sex=='M'}">checked</c:if>>男</label>
				 		<label for="sexF"><input type="radio" id="sexF" name="sex" value="F" <c:if test="${userVo.sex=='F'}">checked</c:if>>女</label>
		 		</td>
			</tr>
			<tr>
				<td class="rightclumn">年龄</td>
				<td><input class="textinput" type="text" name="age" value="${userVo.age}" id="age"/></td>
			</tr>
			<tr>
				<td class="rightclumn">居住地</td>
				<td><input class="textinput easyui-validatebox" type="text" name="address" value="${userVo.address}" id="address"/></td>
			</tr>
			<tr>
				<td class="rightclumn">职业</td>
				<td><input class="textinput" type="text" name="work" value="${userVo.work}"id="work" /></td>
			</tr>
			<tr>
				<td class="rightclumn">个性签名</td>
				<td>
			 			<textarea id="characters" name="characters" style="width:100%;height:50px;">${userVo.characters}</textarea>
		 		</td>
			</tr>
			<tr>
				<td></td>
				<td><a href="javascript:userInfo.save()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="saveBtn">保存</a></td>
			</tr>
		</table>
	</form>
</body>
</html>