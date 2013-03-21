<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${article.title}-有乐窝</title>
<meta name="description" content="${article.title}-有乐窝">
<meta name="keywords" content="${article.keyWord}">
<link rel="stylesheet"  href="../css/article.showarticle.css" type="text/css"  />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/article.showArticle.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script type="text/javascript" src="../js/group.index.js"></script>
<script type="text/javascript" src="../scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="../styles/SyntaxHighlighter.css"/>
	<script type="text/javascript">
		$(document).ready(function () {
            $(".showarticle pre").each(function () {
                var $this = $(this);
                if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
                    var lang = $this.attr("class").split(';')[0].split(':')[1];
                    $this.attr('name', 'code');
                    $this.attr('class', lang);
                }
            });
            $(".recon_info_con pre").each(function () {
                var $this = $(this);
                if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
                    var lang = $this.attr("class").split(';')[0].split(':')[1];
                    $this.attr('name', 'code');
                    $this.attr('class', lang);
                }
            });
            dp.SyntaxHighlighter.HighlightAll('code');
            $("#subBtn").bind("mouseover",function(){
            	$(this).css({"background":"#38678F"});
            });
            $("#subBtn").bind("mouseout",function(){
            	$(this).css({"background":"#4682B4"});
            });
        });
	
		function changeImage(){
			$("#showlcodeImage").attr("src","../common/image.jsp?rand ="+Math.random());
		}
	</script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
<jsp:include page="head.jsp"/>
	<div class="itemcon">
		<div class="itemcon_item"><jsp:include page="items.jsp"/></div>
		<div class="itemcon_btn">
			<div class="bbtn2"><a href="javascript:addGroup()">+加入圈子</a></div>
			<div class="bbtn1"><a href="javascript:addArticle()">◎发表话题</a></div>
		</div>
	</div>
		<div class="main">
			<div class="title_con">
				<div class="title" id="title_con">${article.title}</div>
				<div class="article_info">${fn:substring(article.postTime,0,16)} 阅读(${article.readNumber})&nbsp;&nbsp;回复(<span id="reCount">${article.reNumber}</span>)</div>
				<!-- <div class="next_article"><a href="">上一篇</a>&nbsp;&nbsp;<a href="">下一篇</a></div> -->
			</div>
			<div class="user_info">
				<div class="user_icon">
					<a href="../user/userInfo.jspx?userId=${article.author.userId }"><img src="../upload/${article.author.userLittleIcon}" border="0" width="60"></a>
				</div>
				<div class="user_info_info">
					<div class="user_name">
						作者:<a href="../user/userInfo.jspx?userId=${article.author.userId }">${article.author.userName}</a>&nbsp;&nbsp;
						文章数:${article.author.articleNumber}&nbsp;&nbsp;来自:${article.author.address}
					</div>
					<div class="user_chare">
						个性:${article.author.characters}
					</div>
				</div>
			</div>
			<div class="showarticle" id="${article.id }">${article.content }</div>
			<div class="about_title">
				你可能也喜欢
			</div>
			<div class="about_con" id="about_con">
				<script>
					showAboutArticle('${article.keyWord}','${gid}');
				</script>
			</div>
			<div class="reCon" id="reCon">
				<c:set var="num" value="${(page-1)*15}"></c:set>
				
			</div>
			<div id="recomment"></div>
 			<div class="page_con">
				<div class="bbtn1"><a href="javascript:reArticle('${article.id}')">☆高级回复</a></div>
				<div class="bbtn1"><a href="javascript:addArticle()">◎发表话题</a></div>
			</div>
			<form action="fastReArticle.jspx" method="post">
				<input type="hidden" value="${article.authorId}" id="authorId">
				<input type="hidden" value="${article.title}" id="articleTit">
				<div class="fast_re_con">
					<div class="ui_avatar">
						<c:if test="${user!=null}">
							<img src="../upload/${user.userLittleIcon}" width="30">
						</c:if>
						<c:if test="${user==null}">
							<img src="../upload/default.gif" width="30">
						</c:if>
					</div>
					<div class="recoment_form_panel fastRe" id="reFormPanel">
						<div class="comment_form_textarea">
							<textarea id="reContent"></textarea>
						</div>
						<div class="comment_form_panel">
							<div class="comment_checkcode">
								<input type="text" name="checkCode" id="checkCode">
							</div>
							<div class="comment_checkcode_img">
								<a href="JavaScript:refreshcode2();" onfocus="this.blur();"><img id="checkCodeImage2" src="../common/image.jsp" border="0" height="22"></a>
							</div>
							<div class="comment_checkcode_link"><a href="javascript:refreshcode2()">换一张</a></div>
							<div class="comment_checkcode_rebtn" id="subBtn_con">
								<input type="button" class="button" onclick="subReForm('${user.userId}','${id}','${gid }')" value="回复" id="subBtn">
							</div>
						</div>
						<c:if test="${user==null}">
							<div class="shade" id="shade">
								<div class="shadeLogin">回复，请先 <a href="javascript:login()">登录</a>&nbsp;&nbsp;<a href="javascript:register()">注册</a></div>
							</div>
						</c:if>
					</div>
					<div class="clear"></div>
				</div>
			</form>
	</div>
</div>
<jsp:include page="../common/foot.jsp"/>
<%
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"; 
%>
<script type="text/javascript">
	initGroupParam("<%=realPath1%>","${user.userId}","${gid}");
	var articleId = "${article.id }";
	$(function(){
		loadReComment("${article.id }");
	});
</script>
</body>
</html>