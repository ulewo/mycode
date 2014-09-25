<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${topic.title}-有乐窝</title>
<meta name="description" content="${topic.title}">
<meta name="keywords" content="${topic.title}">
<%@ include file="../common/path.jsp" %>
<script type="text/javascript">
var topic = {};
topic.topicId = "${topic.topicId}";
topic.gid="${topic.gid}";
topic.topicType="${topic.topicType}";
</script>
<script type="text/javascript">
<!--
	window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
	var summary = "${topic.summary}";
//-->
</script>
<%@ include file="../common/ueditorcommon.jsp" %>
<script type="text/javascript" src="${realPath}/css/codehighlight/brush.js?version=2.5"></script>
<link type="text/css" rel="stylesheet" href="${realPath}/css/codehighlight/shCore.css?version=2.5"/>
<link type="text/css" rel="stylesheet" href="${realPath}/css/codehighlight/shThemeDefault.css?version=2.5"/>
<script type="text/javascript" src="${realPath}/js/group.showarticle.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/emotion.data.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/ulewo.common.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/share.js?version=2.8"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/group.detail.css?version=2.5">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css?version=2.5">

</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<%@ include file="group_info.jsp" %>
		<div class="group_body">
			<ul class="group_tag">
				<li><a href="${realPath}/group/${group.gid}" class="tag_select">讨&nbsp;&nbsp;论</a></li>
				<li><a href="${realPath}/group/${group.gid}/img">图&nbsp;&nbsp;片</a></li>
				<li><a href="${realPath}/group/${group.gid}/attachedFile">资&nbsp;&nbsp;源</a></li>
				<li><a href="${realPath}/group/${group.gid}/member">成&nbsp;&nbsp;员</a></li>
			</ul>
			<ul class="group_item">
				<li style="margin-left:2px;"><a href="${realPath}/group/${topic.gid}" <c:if test="${item.categoryId==0}">class="select"</c:if>>全部文章</a></li>
				<c:forEach var="item" items="${categoryList}">
					<li><a href="${realPath}/group/${topic.gid}/cateId/${item.categoryId}" <c:if test="${topic.categoryId==item.categoryId}">class="select"</c:if>>${item.name}</a></li>
				</c:forEach>
			</ul>
			<div class="author_info">
				<div class="author_icon"><img src="${realPath}/upload/${topic.userIcon}"></div>
				<div class="author_info_con">
					<div class="article_tit">
						<span class="article_tit_title" id="article_title">${topic.title}</span>
					</div>
					<div class="author_info_content">
						<div class="author_info_content_op">
							<a href="${realPath}/user/${topic.userId}">${topic.userName}</a>&nbsp;
							发表于 ${topic.showCreateTime}&nbsp;
							阅读  ${topic.readCount}&nbsp;
							回复  ${topic.commentCount} 
						</div>
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div style="text-align:left;margin-top:10px;">
					<div>
						<script type="text/javascript">
						/*帖子详情，创建于2013-7-10*/
						var cpro_id = "u1317825";
						</script>
						<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
					</div>
			</div>
			<c:if test="${topic.topicType=='1'}">
				<div class="surveycon" id="surveycon">
					
				</div>
			</c:if>
			<div class="article_detail">
				${topic.content}
				<a name="attached"></a>
				<div class="clear"></div>
			</div>
			<c:if test="${topic.file!=''&&topic.file!=null}">
			<div class="article_attached">
				<div class="attached_tit">附件</div>
				<div class="attached_con" id="attached_con">
					<span class="filename">${topic.file.fileName}</span>&nbsp;&nbsp;
					<span class="mark">所需积分：${topic.file.downloadMark}</span>&nbsp;&nbsp;
					<span class="dccount">下载：<a href="javascript:void(0)" name="${topic.file.attachmentId}" id="dcount">${topic.file.downloadCount}</a></span>&nbsp;&nbsp;
					<a href="javascript:void(0)" name="${topic.file.attachmentId}" id="downloadFile">点击下载</a>
					&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.ulewo.com/group/10024/topic/4538" target="_blank">没有积分？</a>
					<div id="attachedUser"></div>
				</div>
			</div>
			</c:if>
			<div class="author_info_content">
						<div class="author_info_content_op">
							<div class="op_like_tit">你的态度：</div><a href="javascript:void(0)" opid="${topic.topicId}" type="T" disable = "false" class="op_like" title="赞"><span>${topic.likeCount}</span></a>
						</div>
						<div class="share">
							<div class="share_tit">分享:</div>
							<a href="javascript:void(0)" class="share_sina" title="分享到新浪微博"></a>
							<a href="javascript:void(0)" class="share_qzone" title="分享到QQ空间"></a>
							<a href="javascript:void(0)" class="share_qweibo" title="分享到腾讯微博"></a>
							<a href="javascript:void(0)" class="share_ren" title="分享到人人网"></a>
						</div>
						<div class="op_favorite">
							<span id="favoriteCount">0</span>人收藏了此文章 <span id="op_favorite"></span>
							<c:if test="${user!=null}">
								<a href="${realPath}/manage/main#collectionT" target="_blank">查看我的收藏</a>
							</c:if>
						</div>
						<div class="clear"></div>
			</div>
			<div class="recomment_tit">共有${topic.commentCount}个回帖</div>
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