<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName} — 有乐窝</title>
<meta name="description" content="${summary}">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet"  href="../css/group.index.css" type="text/css"  />
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/plugins/iframeTools.js"></script>
<script type="text/javascript" src="../js/group.index.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script>
function cutString(str){
	return str.substring(0,3);
}
</script>
<style type="text/css">
</style>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon main">
	<jsp:include page="head.jsp"></jsp:include>
	<div class="itemcon">
		<div class="itemcon_item"><jsp:include page="items.jsp"/></div>
		<div class="itemcon_btn">
			<c:if test="${showManageGroup=='Y'}"><div class="bbtn2"><a href="../manage/manage.jspx?gid=${group.id}" target="_blank">管理圈子</a></div></c:if>
		</div>
	</div>
	<div class="main_con">
		<div class="main_left">
			<div class="left_sum">
				<div class="left_ginfo">
					<div class="glogo">
						<a href=""><img src="../upload/${group.groupIcon}" border="0" width="60"></a>
					</div>
					<div class="ginfo">
						<div>
							<span class="gname">${group.groupName}</span>&nbsp;&nbsp;
							<span class="gzhu">群主:<a href="../user/userInfo.jspx?userId=${admin.userId}" target="_blank">${admin.userName}</a></span>&nbsp;&nbsp;
							<span>创建于:${fn:substring(group.createTime,0,10)}</span>&nbsp;&nbsp;
						</div>
						<div>
							<span>成员${group.members}个</span>&nbsp;/
							<span>话题:${group.topicCount}</span>&nbsp;/
							<!-- <span>人气:${group.visitCount}</span>&nbsp;/ -->
							<span>今日发表:<span style="color:red;font-weight:bold;">${todayCount}</span></span>
						</div>
					</div>
					<div class="addg">
						<div class="bbtn2"><a href="javascript:addGroup()">+加入圈子</a></div>
						<div class="bbtn1"><a href="javascript:addArticle()">◎发表话题</a></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="g_sum">
				${group.groupDesc}
				</div>
			</div>
			<div class="article">
				<div class="ath">
					<div class="atit">话题</div>
					<div class="aauthor">作者</div>
					<div class="aauthor">最后回复</div>
				</div>
				<c:forEach var="article" items="${articleList}">
					<div class="atr">
						<div class="atit">
							<c:if test="${article.grade==2}"><div class="atit_img"><img src="../images/ico-top.gif"></div></c:if>
							<c:if test="${article.essence=='Y'}"><div class="atit_img2"><img src="../images/ico-ess.gif"></div></c:if>
							<div class="atit_tit">
							<c:if test="${article.itemName!=null}">【<a href="topics.jspx?gid=${group.id}&itemId=${article.itemId }">${article.itemName }</a>】</c:if>
							<c:if test="${article.itemName==null}">【<a href="topics.jspx?gid=${group.id}">全部话题</a>】</c:if>
							<a href="post.jspx?id=${article.id}" ${article.titleStyle} target="_blank">${article.title }</a>
							&nbsp;&nbsp;&nbsp;&nbsp;<span style="color:#999999">${article.reNumber }/${article.readNumber}</span>
							</div>
						</div>
						<div class="aauthor">
							<a href="../user/userInfo.jspx?userId=${article.authorId}" target="_blank">${article.authorName}</a><br>
							<span class="timestyle">${fn:substring(article.postTime,0,16)}</span>
						</div>
						<div class="aauthor">
							<c:if test="${article.lastReAuthorId!=null}">
								<a href="../user/userInfo.jspx?userId=${article.lastReAuthorId}" target="_blank">${article.lastReAuthorName}</a><br>
							</c:if>
							<c:if test="${article.lastReAuthorId==null}">
								${article.lastReAuthorName }<br>
							</c:if>
							<span class="timestyle">${fn:substring(article.lastReTime,0,16)}</span>
						</div>
						<div class="clear"></div>
					</div>
				</c:forEach>
			</div>
			
		</div>
		<div class="main_right">
			<div class="right_tit" style="margin-top:0px;">圈主/管理员</div>
			<div class="g_admin">
				<c:forEach var="admin" items="${adminList}">
					<div class="g_admin_c">
						<c:if test="${admin.grade==2 }">
							<div class="g_admin_icon"><img src="../images/qz.gif"></div>
							<div class="g_admin_title">圈主</div>
						</c:if>
						<c:if test="${admin.grade==1 }">
							<div class="g_admin_icon"><img src="../images/gly.gif"></div>
							<div class="g_admin_title">管理员</div>
						</c:if>
						<div class="g_admin_name"><a href="../user/userInfo.jspx?userId=${admin.userId}" target="_blank">${admin.userName}</a></div>
					</div>
				</c:forEach>
				
			</div>
			<div class="g_ad"></div>
			<!-- 
			<div class="right_tit">圈子应用</div>
			<div class="g_admin">dddddddd</div>
			 -->
			<div class="right_tit">新加入成员</div>
			<div class="g_admin">
				<c:forEach var="member" items="${newsMembers}">
					<div class="g_mem">
						<div class="g_men_img"><a href="../user/userInfo.jspx?userId=${member.userId}" target="_blank"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
						<div class="g_men_name"><a href="../user/userInfo.jspx?userId=${member.userId}" title="${member.userName}" target="_blank">
							<script>
								document.write("${member.userName}".cutString(8));
							</script>
						</a></div>
					</div>
				</c:forEach>
			<div class="clear"></div>
			</div>
			<div class="right_tit">最活跃成员</div>
			<div class="g_admin">
				<c:forEach var="member" items="${activeMembers}">
						<div class="g_mem">
							<div class="g_men_img"><a href="../user/userInfo.jspx?userId=${member.userId}" target="_blank"><img src="../upload/${member.userIcon}" width="60" border="0"></a></div>
							<div class="g_men_name"><a href="../user/userInfo.jspx?userId=${member.userId}" title="${member.userName}" target="_blank">
								<script>
								document.write("${member.userName}".cutString(8));
							</script>
							</a></div>
						</div>
				</c:forEach>
				<div class="clear"></div>
			</div>
			<!-- 
			<div class="right_tit">友情圈子</div>
			<div class="g_admin">
				<c:forEach var="group" items="${friendGroupList}">
						<div class="g_friend">
							<div class="g_friend_img"><img src="../upload/${group.groupIcon}" width="60"></div>
							<div class="g_friend_info">
								<div>${group.groupName}</div>
								<div>成员${group.memberCount}个&nbsp;/&nbsp;话题${group.articleCount}个</div>
							</div>
						</div>
				</c:forEach>
			</div>
			 -->
		</div>
		<div class="clear"></div>
	</div>
<jsp:include page="../common/foot.jsp"/>
</div>
<%
String realPath1 = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/"; 
%>
<script type="text/javascript">
	initGroupParam("<%=realPath1%>","${user.userId}","${gid}");
</script>
</body>
</html>