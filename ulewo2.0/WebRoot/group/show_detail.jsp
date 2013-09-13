<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${article.title}-有乐窝</title>
<meta name="description" content="${article.title}">
<meta name="keywords" content="${article.title}">
<%@ include file="../common/path.jsp" %>
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
global.articleId= "${article.id}";
</script>
<script type="text/javascript" src="${realPath}/ueditor/editor_config.js"></script>
<script type="text/javascript" src="${realPath}/ueditor/editor.js"></script>
<script type="text/javascript" src="${realPath}/ueditor/editor.parse.js"></script>
<script type="text/javascript" src="${realPath}/js/scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="${realPath}/js/styles/SyntaxHighlighter.css"/>
<script type="text/javascript" src="${realPath}/js/group.showarticle.js"></script>
<script type="text/javascript" src="${realPath}/js/emotion.data.js"></script>
<script type="text/javascript" src="${realPath}/js/share.js"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.detail.css">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${gid}/attachedFile">资&nbsp;&nbsp;源</a></li>
				<li><a href="${realPath}/group/${gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<ul class="group_item">
				<li style="margin-left:2px;"><a href="${realPath}/group/${gid}" <c:if test="${article.itemId==0}">class="select"</c:if>>全部文章</a></li>
				<c:forEach var="item" items="${itemList}">
					<li><a href="${realPath}/group/${gid}?itemId=${item.id}" <c:if test="${article.itemId==item.id}">class="select"</c:if>>${item.itemName}</a></li>
				</c:forEach>
			</ul>
			<div class="author_info">
				<div class="author_icon"><img src="${realPath}/upload/${article.author.userLittleIcon}"></div>
				<div class="author_info_con">
					<div class="article_tit">
						<span class="article_tit_title" id="article_title">${article.title}</span>
					</div>
					<div class="author_info_content">
						<div class="author_info_content_op">
							<a href="${realPath}/user/${article.author.userId}">${article.author.userName}</a>&nbsp;
							发表于 ${article.postTime}&nbsp;
							阅读  ${article.readNumber}&nbsp;
							回复  ${article.reNumber} 
						</div>
						<div class="op_favorite"><span id="favoriteCount">0</span>人收藏了此文章 <span id="op_favorite"></span></div>
						<div class="share">
							<div class="share_tit">分享:</div>
							<a href="javascript:void(0)" class="share_sina" title="分享到新浪微博"></a>
							<a href="javascript:void(0)" class="share_qzone" title="分享到QQ空间"></a>
							<a href="javascript:void(0)" class="share_qweibo" title="分享到腾讯微博"></a>
							<a href="javascript:void(0)" class="share_ren" title="分享到人人网"></a>
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="article_detail">
				<div style="display:inline-block;float:right;margin-left:10px;">
					<div>
						<script type="text/javascript">
						/*小方块，创建于2013-7-10*/
						var cpro_id = "u1317703";
						</script>
						<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
					</div>
				</div>
				${article.content}
				<div class="clear"></div>
				<a name="attached"></a>
			</div>
			
			<c:if test="${article.file!=''&&article.file!=null}">
			<div class="article_attached">
				<div class="attached_tit">附件</div>
				<div class="attached_con" id="attached_con">
					<span class="filename">${article.file.fileName}</span>&nbsp;&nbsp;
					<span class="mark">所需积分：${article.file.mark}</span>&nbsp;&nbsp;
					<span class="dccount">下载：<a href="javascript:void(0)" name="${article.file.id}" id="dcount">${article.file.dcount}</a></span>&nbsp;&nbsp;
					<a href="javascript:void(0)" name="${article.file.id}" id="downloadFile">点击下载</a>
					<div id="attachedUser"></div>
				</div>
			</div>
			</c:if>
			<div class="recomment_tit">共有${article.reNumber}个回帖</div>
			<div id="recomment"></div>
			<div id="pager" class="pagination"></div>
			
			
			<c:if test="${user!=null}">
				<div class="new_article_p" id="new_article_p">
					<div class="new_article_input"></div>
					<div class="new_article_btn">回复帖子</div>
					<div class="clear"></div>
				</div>
			</c:if>
			<c:if test="${user==null}">
				<div class="new_article_p">
					<div class="new_article_input"></div>
					<div class="new_article_btn">回复帖子</div>
					<div class="clear"></div>
					<div class="add_hide">
						<div class="shadeLogin" style="margin-top:10px;margin-left:150px;">回帖，请先 <a href="javascript:goto_login()">登录</a>&nbsp;&nbsp;<a href="javascript:goto_register()">注册</a></div>
					</div>
				</div>
			</c:if>
			
			<div class="add_article" id="add_article">
				<%@ include file="rearticle.jsp" %>
			</div>
		</div>
		<%@ include file="../common/foot.jsp" %>
	</div>
</body>
</html>