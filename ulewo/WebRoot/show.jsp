<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${fn:substring(article.content,0,50)} --你乐我</title>
<meta name="description" content="${fn:substring(article.commend,0,100)} --你乐我">
<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录 ,有图有真相— 你乐我">
<link rel="stylesheet" type="text/css" href="css/common.css" />
</head>
<body>
<jsp:include page="menue.jsp"></jsp:include>
<c:set var="path" value="http://pic.ulewo.com/"></c:set>
<div class="body_con">
	<div class="left">
		<div id="con">
			<c:if test="${article!=null}">
			<div class="articlePanel">
				  <div class="top">
					  <c:if test="${article.uid!=null}">
					 	 	<div class="top_user_avator"><img src="${path}upload/avatar/${article.avatar}"></div>
					  		<div class="top_user_name"><a href="user?uid=${article.uid}">${article.userName}</a></div>
					  </c:if>
				    <div class="top_user_time">${fn:substring(article.postTime,0,19)}</div>
				    <div class="clear"></div>
				  </div>
	  			  <div class="content">${article.content}</div>
	  			  <c:if test="${article.imgUrl!=null}">
		  			   <div class="imgArea">
		  			  	<img src="${path}upload/big/${article.imgUrl}">
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
				     <div class="sharecon" id="sharecon">
				      <span class="sharetit">分享:</span>
				       	  <a href="javascript:dispatche(0,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="qqshare" title="分享到QQ空间"/></a>
					      <a href="javascript:dispatche(1,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="qqwbshare" title="分享到腾讯微博"/></a>
					      <a href="javascript:dispatche(2,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="sinawbshare" title="分享到新浪微博"/></a>
					      <a href="javascript:dispatche(3,'${fn:substring(article.commend,0,120)}','${article.imgUrl }',${article.id})" class="renrenshare" title="分享到人人网"/></a>
				    </div>
				    <div class="replyCon">
				      <a href="javascript:void(0)" onfocus="this.blur()" id="relink" onclick="showRe(${article.id},this)">评论</a>
				      <span id="reCount">${article.reCount}</span>
				    </div>
				    <div class="clear"></div>
				  </div>
				  <div class="replayCon" style="display:inline;">
				 	 <div class="replayform">
				 	 	<textarea class="re_textarea" id="re_textarea" onselect="setCaret(this)" onclick="setCaret(this)"onkeyup="setCaret(this)"></textarea>
				 	 	<div class="replay_op">
				 	 		<a href="javascript:void(0)" class="face" onfocus="this.blur()">表情</a>
				 	 		<a href="javascript:void(0)" class="reBtn" onclick="addReArticle(${article.id},this)">发表</a>
				 	 		<div class="clear"></div>
				 	 	</div>
				 	 </div>
				 	 <div class="replaylist">
				 	 		评论加载中.......
				 	 </div> 		
				  </div>
				</div>
			</c:if>
			<c:if test="${article==null}">
				<div class="loading">你访问文章不存在，或者已经删除</div>
			</c:if>
		</div>
	</div>
	<div class="right">
		<jsp:include page="right.jsp"></jsp:include>		
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/show.js"></script>
<script type="text/javascript">
	$(function(){
		showInitRe(${article.id},$("#relink"));
	})
	
</script>
</body>
</html>