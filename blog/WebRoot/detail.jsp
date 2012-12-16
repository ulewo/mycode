<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${article.title}</title>
<meta name="description" content="${article.title}">
<meta name="keywords" content="${article.title}">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/public.js"></script>
<script type="text/javascript" src="js/detail.js"></script>
<script type="text/javascript" src="scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="styles/SyntaxHighlighter.css"/>
<link rel="stylesheet" type="text/css" href="css/index.css" />

<style type="text/css">
.menue div a.article{background-position:0px -39px;width:82px;}
</style>
</head>
<body>
	<jsp:include page="head.jsp"></jsp:include>
		<div class="main">
			<div class="left">
					<div class="post">
						<h2>${article.title}</h2>
						<div>
							<span class="date">${fn:substring(article.postTime,0,10)}</span>
							<span class="categories">标签:
									<c:forEach var="tag" items="${article.tagList}">
										${tag}
									</c:forEach>
							</span>
							<span class="detail_readcount">阅读：<span>${article.readCount}</span></span>
							 <div class="sharecon">
							      <span class="sharetit">分享:</span>
							      <a href="javascript:share(0)" class="qqshare" title="分享到QQ空间"/></a>
							      <a href="javascript:share(1)" class="qqwbshare" title="分享到腾讯微博"/></a>
							      <a href="javascript:share(2)" class="sinawbshare" title="分享到新浪微博"/></a>
							      <a href="javascript:share(3)" class="renrenshare" title="分享到人人网"/></a>
					   		 </div>
							<div class="clear_float"></div>
						</div>
						<div class="description" id="content">
							${article.content}
						</div>
						<div class="comment_tit" id="comment_tit"></div>
						<div id="rearticle_list"></div>
						<div class="rearticle_form">
							<input type="text" id="userName" value="姓名" style="color:#666666"><br>
							<textarea rows="5" cols="20" id="content" style="color:#666666">请输入你想说的</textarea><br>
							<div id="subCon" class="subcon">
								<a href="javascript:void(0)" id='subBtn' class="colorbtn" onclick="addReArticle('${article.id}')">发送留言</a>
								<img src='images/loading.gif' id="load" style="display:none;">
							</div>
						</div>
					</div>
			</div>
			<div class="right">
				<jsp:include page="right.jsp"></jsp:include>
			</div>
			<div class="clear_float"></div>
		</div>
		<div class="foot"></div>
</body>
<script type="text/javascript">
	var realPath = "http://blog.ulewo.com/";
	var comment = "${comment}";
	$(function(){
		 $(".description pre").each(function () {
             var $this = $(this);
             if ($this.attr("class").indexOf("brush") != -1) {
            	 var $this = $(this);
                 if ($this.attr("class").indexOf("brush:") != -1) {
                     var lang = $this.attr("class").split(';')[0].split(':')[1];
                     $this.attr('name', 'code');
                     $this.attr('class', lang);
                 }
             }
         });
         dp.SyntaxHighlighter.HighlightAll('code');
		$("#userName").bind("blur",setNameInfo).bind("focus",clearNameInfo);
		$("#content").bind("blur",setContentInfo).bind("focus",clearContentInfo);
		loadReArticles('${article.id}');
		loadItem();
		loadRecord();
	});
	
	function share(type){
		var imgUrl = $("img.imageborder");
		var img = "";
		for(var i=0;i<imgUrl.length;i++){
			if(i==imgUrl.length-1){
				img=img+encodeURIComponent(realPath+$(imgUrl[i]).attr("src"));
			}else{
				img=img+encodeURIComponent(realPath+$(imgUrl[i]).attr("src")+"|");
			}
			
		}
		dispatche(type, "${article.title}", img, "${article.id}");
	}
</script>
</html>