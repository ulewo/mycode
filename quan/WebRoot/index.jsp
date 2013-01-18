<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/index_new.css">
<script type="text/javascript">
	function createGroup(){
		var user = "${user}";
		if(user==""){
			showLoginDilog('user/login.jsp')
		}
	}
</script>
</head>
<body>
	<jsp:include page="head.jsp"/>
  	<div class="main">
  		<div class="left">
  			<div class="titinfo">本周热点</div>
  			<div class="hot">
  				<c:forEach var="article" items="${commendArticle}">
  				</c:forEach>
	  			<ul class="new_list">
	  				<c:forEach var="article" items="${commendArticle}" begin="0" end="4">
	  					<li><a href="group/post.jspx?id=${article.id}" title="${article.title}">${article.title}</a></li>
  					</c:forEach>
	  			</ul>
	  			<ul class="new_list">
	  				<c:forEach var="article" items="${commendArticle}" begin="5" end="10">
	  					<li><a href="group/post.jspx?id=${article.id}" title="${article.title}">${article.title}</a></li>
  					</c:forEach>
	  			</ul>
	  			<div class="clear"></div>
  			</div>
  			<div class="titinfo">最新文章</div>
	  			<ul class="new_article_list">
	  				<c:forEach var="article" items="${list}">
	  					<li><span class="article_tit"><a href="group/group.jspx?gid=${article.gid}">[${article.groupName}]</a><a href="group/post.jspx?id=${article.id}" class="sec_span"  title="${article.title}">${article.title}</a></span><span class="article_user">${fn:substring(article.postTime,0,10)} by ${article.authorName}</span></li>
	  				</c:forEach>
	  			</ul>
  			<div class="titinfo">最新博文</div>
  			  <div>
				<ul class="new_blog">
					<c:forEach var="blog" items="${blogList}">
						<li><span class="article_tit"><a href="user/blogdetail.jspx?id=${blog.id}" title="${article.title}">${blog.title}</a><span class="sec_span">${fn:substring(blog.postTime,0,10)} by ${blog.userName}</span></span><span class="article_read">${blog.reCount}回/${blog.readCount}阅</span></li>
					</c:forEach>
	  			</ul>
	  			</div>
  		</div>
  		<div class="right">
  			<div class="create_wo"><a href="javascript:createGroup()">创建我的窝窝</a></div>
  			<div class="titinfo">每日图文</div>
  			<div>
  				<c:forEach var="article" items="${imgArticle}">
  					<div class="day_pic">
					<div class="day_pic_img"><img src="upload/${article.image}"></div>
					<div class="day_pic_tit"><a href="group/post.jspx?id=${article.id}" title="${article.title}">${article.title}</a></div>
				</div> 
  				</c:forEach>
				<div class="clear"></div>
			  </div>	
	  			<div class="titinfo">推荐窝窝</div>
	  			<c:forEach var="group" items="${commendGroupList}">
	  				<div class="recommend_wo">
  					<div class="wo_img"><a href="group/group.jspx?gid=${group.id}"><img src="upload/${group.groupIcon}"></a></div>
  					<div class="wo_info">
  						<div><a href="group/group.jspx?gid=${group.id}">${group.groupName}</a></div>
  						<div>成员${group.members}人</div>
  						<div>文章${group.topicCount}篇</div>
  					</div>
  					<div class="clear"></div>
  				</div>
	  			</c:forEach>
  				<div class="titinfo">最活跃成员</div>
			  	<div>
			  		<c:forEach var="member" items="${activeUserList}">
				  		<div class="user_img">
				  			<div><a href="user/userInfo.jspx?userId=${member.userId}"><img src="upload/${member.userLittleIcon}"></a></div>
				  			<div class="user_img_name"><a href="user/userInfo.jspx?userId=${member.userId}" title="${member.userName}">${member.userName}</a></div>
				  		</div>
			  		</c:forEach>
			  		<div class="clear"></div>
			  	</div>
  			</div>
  			<div class="clear"></div>
  		</div>
  	 <jsp:include page="foot.jsp"/>
</body>
</html>