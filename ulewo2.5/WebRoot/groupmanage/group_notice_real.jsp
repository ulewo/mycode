<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>窝窝公告管理-有乐窝</title>
<%@ include file="../common/easyui_common.jsp" %>
<script type="text/javascript" src="${realPath}/js/group.manage.editnotice.js?version=2.5"></script>
</head>
<body>
	<form id="group_form" class="easyform" style="width:60%">
		<input type="hidden" value="${group.gid}" name="gid">
		<table width="100%">
			<tr>
				<td colspan="2">窝窝公告（公告不能超过300字符）</td>
			</tr>
			<tr>
				<td colspan="2"><textarea style="width:100%;height:100px;" id="group_notice" name="groupNotice">${group.groupNotice}</textarea></td>
			</tr>
			<tr>
				<td colspan="2"><a href="javascript:groupManageNotice.editGroupNotice()" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="saveBtn">保存</a></td>
			</tr>
		</table>
	</form>
</body>
</html>