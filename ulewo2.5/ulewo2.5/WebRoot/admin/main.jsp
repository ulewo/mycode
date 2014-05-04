<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>有乐窝后台管理系统</title>
<%@ include file="../common/easyui_common.jsp" %>
<link rel="stylesheet" type="text/css" href="../css/admin.main.css?version=2.5">
<script type="text/javascript" src="../js/admin.main.js?version=2.5"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:45px;padding:10px;font-size:18px;">
		有乐窝后台管理系统欢迎你！
	</div>
	<div data-options="region:'west',title:'主菜单'" style="width:200px;">
		<div id="menue" data-options="animate:false"  style="width:198px;height:auto;border:0px;">  
		   
	    </div> 
	</div>
	<div data-options="region:'center'" style="border:0px;">
		<div class="easyui-tabs" id="centerTab" data-options="fit:true" border="false">  
            <div title="欢迎页" >   
                  	欢迎管理员
            </div>  
        </div>  
	</div>
</body>
</html>