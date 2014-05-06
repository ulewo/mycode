<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理窝窝-有乐窝</title>
<%@ include file="../common/easyui_common.jsp" %>
<script type="text/javascript" src="${realPath}/js/group.manage.editgroup.js?version=2.5"></script>
</head>
<body>
	<form id="group_form" class="easyform" style="width:60%">
		<input type="hidden" value="${group.gid}" name="gid">
		<table width="100%">
			<tr>
				<td colspan="2">窝窝名称</td>
			</tr>
			<tr>
				<td colspan="2"><input type="text" class="textinput easyui-validatebox" id="group_name" name="groupName" value="${group.groupName}"   style="width:100%"></td>
			</tr>
			<tr>
				<td colspan="2">简介</td>
			</tr>
			<tr>
				<td colspan="2"><textarea style="width:100%;height:100px;" id="group_desc" name="groupDesc">${group.groupDesc}</textarea></td>
			</tr>
			<tr>
				<td width="55">加入权限</td>
				<td>
					<label for="joinpremy"><input type="radio" value="Y" name="joinPerm" id="joinpremy" <c:if test="${group.joinPerm=='Y'}">checked="checked"</c:if> >允许任何人加入</label>
					<label for="joinpremn"><input type="radio" value="N" name="joinPerm" id="joinpremn" <c:if test="${group.joinPerm=='N'}">checked="checked"</c:if>>需经我审批才能加入</label>
				</td>
			</tr>
			<tr>
				<td>发帖权限</td>
				<td>
					<label for="postperma"><input type="radio" value="A" name="postPerm" id="postperma" <c:if test="${group.postPerm=='A'}">checked="checked"</c:if>>任何人可以发帖</label>
					<label for="postpermy"><input type="radio" value="Y" name="postPerm" id="postpermy" <c:if test="${group.postPerm=='Y'}">checked="checked"</c:if>>窝窝成员才能发帖</label>
				</td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:groupManageBaseInfo.editGroup()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="saveBtn">保存</a></td>
			</tr>
		</table>
	</form>
</body>
</html>