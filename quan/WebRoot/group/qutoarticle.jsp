<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<meta name="description" content="${group.groupName}-有乐窝">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet"  href="../css/group.article.css" type="text/css"  />
<script type="text/javascript">
<%
String patch = request.getContextPath();
%>
<!--
window.UEDITOR_HOME_URL = "<%=patch%>/ueditor/";
//-->
</script>


<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/group.addarticle.js"></script>

<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/group.article.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor.js"></script>

</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
	<jsp:include page="head.jsp"/>
	<div class="ad_con">
		<form action="subQuote.jspx" method="post"  name="example" id="subForm">
		<input type="hidden" name="id" value="${id}"  />
		<input type="hidden" name="content" id="content">
		<div class="ad_quto">
			<img border="0" src="../images/quto.gif">
			<b>引用&nbsp;
			<span style="color:blue">${reArticle.authorName}</span>
			&nbsp;在&nbsp;${fn:substring(reArticle.reTime,0,16)}&nbsp;的发表</b>
			<div style="margin-top:5px;">${reArticle.content}</div>
		</div>
		<div id="editor" style="text-align:left;">
		</div>
		<div class="ad-part bigButon" style="text-align:center;margin-top:10px;">
			<a href="javascript:subQutoForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		</form>
	</div>
</div>	
<script type="text/javascript">
window.UEDITOR_CONFIG.initialFrameWidth = 1000;
var editor = new UE.ui.Editor();
editor.render("editor");
editor.ready(function(){
    editor.setContent("");
});

function subQutoForm(){
	if(editor.getContent()==""){
		alert("内容不能为空");
		return ;
	}else{
		$("#content").val(editor.getContent());
	}
	$("#subForm").submit();
}

function initImg(imageUrls){
}
</script>
<jsp:include page="../common/foot.jsp"/>
</body>
</html>