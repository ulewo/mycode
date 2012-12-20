<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小友吧 大世界 小智慧 大财富 --友吧中国</title>
<meta name="description" content="友吧中国 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小友吧 大世界 小智慧 大财富 — 友吧中国">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/jquery.backgroundPosition.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<style>
	.selected1{background:#1C91BE;}
</style>
<script type="text/javascript">
var imagearry = new Array();
<c:set var="num" value="0"></c:set>
<c:forEach var="article" items="${imgArticle}">
	imagearry[${num}] = "${article.image }|${article.id}";
	<c:set var="num" value="${num+1}"></c:set>
</c:forEach>
</script>
</head>
<body>
<jsp:include page="head.jsp"/>
  <div class="bodycon">
  	<div class="mainfirst">
  		<div class="mainimg">
  			<div class="imagecon"><a href="" id="url" onfocus="this.blur()"><img src="images/bigload.gif" width="545" height="248" id="img" border=0/></a></div>
				<div class="rightBody">
					<div class="rightcon">
						<c:forEach var="article" items="${imgArticle}">
							<div class="titinf">${fn:substring(article.title,0,26)}</div>
						</c:forEach>
					</div>
				</div>
  		</div>
  		<div class="commend">
  			<div class="maincommendtit">推荐文章</div>
	  		<div class="maincommend">
	  			<c:forEach var="article" items="${commendArticle}">
	  				<div class="maincommend_list">
	  					<a href="group/post.jspx?id=${article.id}" title="${article.title}">
	  					<script>
								document.write("${article.title}".cutString(34));
						</script>
						</a>
	  				</div>
	  			</c:forEach>
	  		</div>
  		</div>
  	</div>
  	<div class="maincon">
	  	<div class="mainleft">
		  	<div class="mainlisttit">最新文章</div>
				<c:forEach var="article" items="${list}">
					<div class="mainlist">
						<div class="mainlist_img">
							<img src="upload/${article.image}">
						</div>
						<div class="mainlist_con">
							<div class="mainlist_con_title"><a href="group/post.jspx?id=${article.id}">${article.title}</a></div>
							<div class="mainlist_con_username"><a href="user/userInfo.jspx?userId=${article.authorId}">${article.authorName}</a>&nbsp;&nbsp;发表于：${fn:substring(article.postTime,0,10)}</div>
							<div class="mainlist_con_summary">${fn:substring(article.summary,0,100)}(${article.reNumber}评论)</div>
						</div>
						<div style="clear:left;"></div>
					</div>
				</c:forEach>
		</div>
		<div class="mainright">
		<div class="right_tit">推荐吧</div>
			<div class="g_admin">
				<c:forEach var="group" items="${commendGroupList}">
					<div class="groupmain">
						<div class="groupimg">
							<a href="group/group.jspx?gid=${group.id}"><img src="upload/${group.groupIcon}" width="50" border="0"></a>
						</div>
						<div class="groupinfo">
							<div>
								<a href="group/group.jspx?gid=${group.id}" title="${group.groupName}">
								<script>
								 document.write("${group.groupName}".cutString(12));
							    </script>
							    </a>
							</div>
							<div>成员${group.members}人</div>
							<div>文章${group.topicCount}篇</div>
						</div>
					<div style="clear:left;"></div>
					</div>
				</c:forEach>
			</div>
			<div class="right_tit">最活跃成员</div>
			<div class="g_admin">
				<c:forEach var="member" items="${activeUserList}">
					<div class="g_mem">
						<div class="g_men_img"><a href="user/userInfo.jspx?userId=${member.userId}"><img src="upload/${member.userLittleIcon}" width="60" border="0"></a></div>
						<div class="g_men_name"><a href="user/userInfo.jspx?userId=${member.userId}" title="${member.userName}">
							<script>
								document.write("${member.userName}".cutString(8));
							</script>
						</a></div>
					</div>
				</c:forEach>
				<div style="clear:left;"></div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	</div>
	 <jsp:include page="foot.jsp"/>
</body>
</html>