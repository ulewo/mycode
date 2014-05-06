<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>有乐窝后台管理系统</title>
<%@ include file="../common/easyui_common.jsp" %>
<link rel="stylesheet" type="text/css" href="../css/admin.main.css?version=2.5">
<script>
var userManage = {}
userManage.gid = "${gid}";
</script>
<script type="text/javascript" src="../js/user.manage.main.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:45px;padding:10px;font-size:13px;">
		${user.userName},欢迎回来 <a href="../user/${user.userId}" style="color:#6DAFE0">&lt;&lt;回个人中心</a>
	</div>
	<div data-options="region:'west',title:'主菜单'" style="width:200px;">
		<div id="menue"  data-options="animate:false" style="width:198px;height:auto;border:0px;">  
		   
	    </div> 
	</div>
	<div data-options="region:'center'" style="border:0px;">
		<div class="easyui-tabs" id="centerTab" data-options="fit:true" border="false">  
            <div title="欢迎页" >  
            	<div style="padding:10px;">${user.userName},欢迎回来</div> 
            </div>  
        </div>  
	</div>
</body>
</html>