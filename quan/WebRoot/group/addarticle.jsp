<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		String patch = request.getContextPath();
	%>
	<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = "<%=patch%>/ueditor/";
	//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-有乐窝</title>
<meta name="description" content="${group.groupName}-有乐窝">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet"  href="../css/group.article.css" type="text/css"  />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/group.article.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor.js"></script>
</head>
<body>
<%@ include file="../common/head.jsp" %>
<div class="bodycon">
	<jsp:include page="head.jsp"/>
	<div class="ad_con">
		<form action="subArticle.jspx" method="post"  name="example" id="subForm">
		<input type="hidden" name="image" id="faceImg" value="">
		<input type="hidden" name="gid" value="${gid}"  />
		<input type="hidden" id="content" name="content">
		<div class="ad_line">
			<div class="ad_title">
				<input type="text" name="title" id="title">&nbsp;&nbsp;发表在
			</div>
			<div class="ad_item">
				<select name="itemId" id="itemId">
					<option value=""></option>
					<option value="0">全部分类</option>
  					<c:forEach var="item" items="${itemList}">
						<option value="${item.id}">${item.itemName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="ad_line">
			<input type="text" name="keyWord" id="keyWord">&nbsp;&nbsp;<span style="color:#999999">好的关键字可以让别人更容易找到此篇文章（可选）多个关键字用半角逗号隔开</span>
		</div>
		<div id="editor" style="text-align:left;">
		</div>
		<div class="ad-part bbtn1" style="text-align:center;margin-top:10px;" id="subtn">
			<a href="javascript:submitForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		</form>
	</div>
</div>	
<jsp:include page="../common/foot.jsp"/>
</body>
<script type="text/javascript">
window.UEDITOR_CONFIG.initialFrameWidth = 1000;
    var editor = new UE.ui.Editor();
    editor.render("editor");
    editor.ready(function(){
        editor.setContent("");
    });
    
    function submitForm(){
    	var title = $("#title").val();
    	if(title.trim()==""){
    		alert("标题不能为空");
    		return;
    	}else{
    		title = title.replaceHtml();
    		$("#title").val(title);
    	}
    	
    	var itemId = $("#itemId").val();
    	if(itemId==""){
    		alert("请选择分类");
    		$("#itemId").focus();
    		return;
    	}
    	
    	var keyWord =  $("#keyWord").val();
		if(keyWord.trim()==""){
			alert("关键字不能为空");
			return;
		}else{
			keyWord = keyWord.replaceHtml();
			$("#keyWord").val(keyWord);
		}
    	if(editor.getContent()==""){
			alert("内容不能为空");
			return ;
		}else{
			$("#content").val(editor.getContent());
		}
    	$("#subtn").html("<img src='../images/load.gif'/>");
    	$("#subForm").submit();
    }
    
    function initImg(imageUrls){
    	if(imageUrls.length>0){
    		$("#faceImg").val(imageUrls[0].url);
    	}
    }
	</script>
</html>