<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="../css/easyuithemes/icon.css">
<link rel="stylesheet" type="text/css" href="../css/admin.main.css">
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/admin.main.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:60px;padding:10px">
		有乐窝后台管理系统欢迎你！
	</div>
	<div data-options="region:'west',split:true,title:'主菜单',split:false" style="width:200px;">
		<div id="menue" class="easyui-accordion"  style="width:198px;height:auto;">  
		   <!-- 
		    <div title="吐槽管理" style="padding-bottom:5px;">  
		       <ul id="tt0" class="easyui-tree">  
    				<li>  
	        			<span>吐槽管理</span>  
					        <ul>  
					            <li>  
					                <span>所有吐槽</span>  
					            </li>  
					            <li>  
					                <span>吐槽评论</span>  
					            </li> 
					        </ul>  
			    	</li>  
				</ul>  
		    </div> 
		    <div title="文章管理" style="padding-bottom:5px;">  
		       <ul id="tt" class="easyui-tree">  
    				<li>  
	        			<span>文章管理</span>  
					        <ul>  
					            <li>  
					                <span>主题文章</span>  
					            </li>  
					            <li>  
					                <span>回复</span>  
					            </li> 
					             <li>  
					                <span>附件管理</span>  
					            </li>  
					        </ul>  
			    	</li>  
				</ul>  
		    </div> 
		     <div title="窝窝管理">  
		       <ul id="tt2" class="easyui-tree">  
    				<li>  
	        			<span>文章管理</span>  
					        <ul>  
					            <li>  
					                <span>主题文章</span>  
					            </li>  
					            <li>  
					                <span>回复</span>  
					            </li> 
					             <li>  
					                <span>附件管理</span>  
					            </li>  
					        </ul>  
			    	</li>  
				</ul>    
		    </div>
		    <div title="博客管理">  
		        <ul id="tt3" class="easyui-tree">  
    				<li>  
	        			<span>文章管理</span>  
					        <ul>  
					            <li>  
					                <span>主题文章</span>  
					            </li>  
					            <li>  
					                <span>回复</span>  
					            </li> 
					             <li>  
					                <span>附件管理</span>  
					            </li>  
					        </ul>  
			    	</li>  
				</ul>      
		    </div>
		    <div title="会员管理">  
		        <ul id="tt4" class="easyui-tree">  
    				<li>  
	        			<span>文章管理</span>  
					        <ul>  
					            <li>  
					                <span>主题文章</span>  
					            </li>  
					            <li>  
					                <span>回复</span>  
					            </li> 
					             <li>  
					                <span>附件管理</span>  
					            </li>  
					        </ul>  
			    	</li>  
				</ul>        
		    </div>
		     -->
	    </div> 
	</div>
	<div data-options="region:'center'" style="width:600px;">
		<div class="easyui-tabs" id="centerTab" fit="true" border="false">  
            <div title="欢迎页" style="padding:20px;overflow:hidden;">   
                <div style="margin-top:20px;">  
                    <h3>你好，欢迎来到有乐窝管理系统</h3>  
                </div>  
            </div>  
        </div>  
	</div>
</body>
</html>