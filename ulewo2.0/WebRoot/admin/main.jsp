<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/icon.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
</head>
<body>
	<div data-options="region:'north',border:false" style="height:60px;padding:10px">
		菜单
	</div>
	<div data-options="region:'west',split:true,title:'West'" style="width:150px;padding:10px;">west content</div>
	<div data-options="region:'center',title:'Center'">
		<div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            <div title="欢迎页" style="padding:20px;overflow:hidden;">   
                <div style="margin-top:20px;">  
                    <h3>你好，欢迎来到权限管理系统</h3>  
                </div>  
            </div>  
        </div>  
	</div>
</body>
</html>