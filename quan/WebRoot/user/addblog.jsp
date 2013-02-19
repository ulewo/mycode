<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<%
		String patch = request.getContextPath();
	%>
	<script type="text/javascript">
	<!--
		window.UEDITOR_HOME_URL = "<%=patch%>/ueditor/";
	//-->
	</script>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../ueditor/editor_config_blog.js"></script>
	<script type="text/javascript" src="../ueditor/editor.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/user.blog.js"></script>
	<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
	<style type="text/css">
		#selected4 a{background:#FFFFFF;}
	</style>
  </head>
  <body>
  <jsp:include page="../common/head.jsp"/>
  <div class="main">
	  <div class="left">
	  	<jsp:include page="menue.jsp"></jsp:include>
	  </div>
	  <div class="right">
	    <div class="navPath"><a href="userInfo.jspx?userId=${user.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;发表博文</div>
	  	<div class="ad-part bbtn1" style="text-align:center;margin-top:10px;float:none;margin-left:0px;">
			<a href="javascript:submitForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		<div>
	  	<form action="saveBlog.jspx" method="post"  name="example" id="subForm">
	  	<input type="hidden" id="content" name="content">
		<div class="ad_line">
			<div class="ad_title">
				<input type="text" name="title" id="title">&nbsp;&nbsp;发表在
			</div>
			<div class="ad_item">
				<select name="itemId">
					<option value="0">全部文章</option>
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
		<div>
			<input type="radio" name="allowReplay" checked="checked" value="0"/>允许所有人评论
			<input type="radio" name="allowReplay" value="1"/>只允许登录用户评论
			<input type="radio" name="allowReplay" value="2"/>禁止评论
		</div>
		<div class="ad-part bbtn1" style="text-align:center;margin-top:10px;">
		<a href="javascript:submitForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		
		</form>
		</div>
	  </div>
	  <div style="clear:left;"></div>
 </div>
    <jsp:include page="../common/foot.jsp"/>
  </body>
  	<script type="text/javascript">
	var isHaveImg = false;
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
    }
	</script>
</html>
