<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
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
<link rel="stylesheet"  href="../css/manage.article.css" type="text/css"  />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/swfupload.js"></script>
<script type="text/javascript" src="../editor/handler.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/group.article.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor.js"></script>
<style type="text/css">
#selected4 a{background:#FFFFFF;}
</style>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
		<div class="right_titpo"><a href="articleManage.jspx?gid=${gid}">文章列表</a>&gt;&gt;修改文章</div>
		<div class="ad_con">
			<form action="updateArticle.jspx" method="post"  name="example" id="subForm">
			<input type="hidden" name="image" id="faceImg" value="">
			<input type="hidden" name="gid" value="${gid}"  />
			<input type="hidden" name="id" value="${id}"  />
			<input type="hidden" id="content" name="content">
			<div class="ad_line">
				<div class="ad_title">
					<input type="text" name="title" id="title" value="${article.title}">&nbsp;&nbsp;发表在
				</div>
				<div class="ad_item">
					<select name="itemId">
	  					<c:forEach var="item" items="${itemList}">
							<option value="${item.id}" <c:if test="${item.id==article.itemId}">selected</c:if>>${item.itemName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="ad_line">
				<input type="text" name="keyWord" id="keyWord" value="${article.keyWord}">&nbsp;&nbsp;<span style="color:#999999">好的关键字可以让别人更容易找到此篇文章（可选）多个关键字用半角逗号隔开</span>
			</div>
			<div id="editor" style="text-align:left;">
			</div>
			<div class="ad-part bbtn1" style="text-align:center;margin-top:10px;">
				<a href="javascript:submitForm()" onfocus="this.blur()" >发表话题</a>
			</div>
			</form>
		</div>
	</div>
	<div class="clear"></div>
</div>	
</body>
<script type="text/javascript">
window.UEDITOR_CONFIG.initialFrameWidth = 800;
    var editor = new UE.ui.Editor();
    editor.render("editor");
    editor.ready(function(){
        editor.setContent('${article.content}');
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
    	$("#subForm").submit();
    }
    
    function initImg(imageUrls){
    	if(imageUrls.length>0){
    		$("#faceImg").val(imageUrls[0].url);
    	}
    }
	</script>
</html>