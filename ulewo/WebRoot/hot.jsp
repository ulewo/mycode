<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>热评--你乐我</title>
<meta name="description" content="分享快乐，分享囧事，分享生活，一份快乐两个人分享,就会成为两份快乐；一份忧愁两个人分担，便会成为半份忧愁 --你乐我">
<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录,热评 — 你乐我">
<style type="text/css">
.sel3{background:#4EB2D4}
</style>
</head>
<body>
<jsp:include page="menue.jsp"></jsp:include>
<c:set var="path" value="http://pic.ulewo.com/"></c:set>
<div class="body_con">
	<div class="left">
		<div class="pageArea"><p:pager url="" page="${page}" pageTotal = "${pageTotal }"></p:pager></div>
		<div id="con">
			<c:forEach var="article" items="${list}">
				<div class="articlePanel">
					  <div class="top">
					  	<c:if test="${article.uid!=null}">
					 	 	<div class="top_user_avator"><img src="${path}upload/avatar/${article.avatar}"></div>
					  		<div class="top_user_name"><a href="user?uid=${article.uid}">${article.userName}</a></div>
				  		</c:if>
					    <div class="top_user_time">${fn:substring(article.postTime,0,19)}</div>
					    <div class="detail_con">
					    	<a href="detail?id=${article.id}">查看详情</a>
					    </div>
					    <div class="clear"></div>
					  </div>
  					  <div class="content">${article.content}</div>
  					  <c:if test="${article.imgUrl!=null}">
		  			   <div class="imgArea">
		  			  	  <img src="${path}upload/small/${article.imgUrl}" class='small hover' type="small" imgUrl="${article.imgUrl}">
		  			    </div>
	  			 	  </c:if>
	  			 	  <c:if test="${article.videoUrl!=null&&article.medioType == 'V'}">
	  			 	  	<div><embed src="${article.videoUrl}" quality="high" width="600" height="500" align="middle" allowScriptAccess="always" allowFullScreen="true" mode="transparent" type="application/x-shockwave-flash">
	  			 	  		</embed>
	  			 	  	</div>
	  			 	  </c:if>
	  			 	  <div class="tagArea">
					    	<c:forEach var="tag" items="${article.tags}">
				    			<a href="javascript:search('${tag}')">${tag}</a>
				    		</c:forEach>
				    		<div class="clear"></div>
					  </div>
					  <div class="opArea">
					    <div class="countArea">
					      <c:if test="${!article.haveOper}">
						      <a href="javascript:void(0)" class="up" title="支持" onclick="up(${article.id},${article.up},${article.down},this)">${article.up}</a>
						      <a href="javascript:void(0)" class="down" title="反对" onclick="down(${article.id},${article.up},${article.down},this)">${article.down}</a>
					      </c:if>
					      <c:if test="${article.haveOper}">
					      	  <a href="javascript:void(0)" class="upno" title="支持">${article.up}</a>
						      <a href="javascript:void(0)" class="downno" title="反对">${article.down}</a>
					       </c:if>
					    </div>
					    <div class="sharecon">
					      <span class="sharetit">分享:</span>
					      <a href="javascript:dispatche(0,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="qqshare" title="分享到QQ空间"/></a>
					      <a href="javascript:dispatche(1,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="qqwbshare" title="分享到腾讯微博"/></a>
					      <a href="javascript:dispatche(2,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="sinawbshare" title="分享到新浪微博"/></a>
					      <a href="javascript:dispatche(3,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="renrenshare" title="分享到人人网"/></a>
					    </div>
					    <div class="replyCon">
					      <a href="javascript:void(0)" onfocus="this.blur()" onclick="showRe(${article.id},this)">评论</a>
					      <span>${article.reCount}</span>
					    </div>
					    <div class="clear"></div>
					  </div>
					  <div class="replayCon">
					    <div class="replayform">
					      <textarea class="re_textarea" onselect="setCaret(this);" onclick="setCaret(this);" onkeyup="setCaret(this);"></textarea>
					      <div class="replay_op">
					        <a href="javascript:void(0)" onfocus="this.blur()" class="face">表情</a>
					        <a href="javascript:void(0)" class="reBtn" onclick="addReArticle(${article.id},this)">发表</a>
					        <div class="clear"></div>
					      </div>
					    </div>
					    <div class="replaylist">
				 	 		评论加载中.......
				 	 	</div> 
					  </div>
				</div>
			</c:forEach>
		</div>
		<div class="pageArea"><p:pager url="" page="${page}" pageTotal = "${pageTotal }"></p:pager></div>
	</div>
	<div class="right">
		<jsp:include page="right.jsp"></jsp:include>		
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/public.js"></script>
</body>
</html>