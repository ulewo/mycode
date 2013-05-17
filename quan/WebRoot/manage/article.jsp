<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<style>
	
</style>
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/manage.article.js"></script>
<link rel="stylesheet"  href="../css/manage.article.css" type="text/css"  />
<style type="text/css">
#selected4 a{background:#FFFFFF;}
</style>
<script type="text/javascript">
</script>
</head>
<body>
<%@ include file="../common/head.jsp" %>
<div class="maincon">
	<div class="left">
		<jsp:include page="menue.jsp"></jsp:include>
	</div>
	<div class="right">
		<div class="articlecount">所有主题(${countNumber})</div>
		<div class="articleop mbt">
			<a href="javascript:void(0)" onclick="setTop('set')">置顶</a>
			<a href="javascript:void(0)" onclick="setTop('cancel')">取消置顶</a>
			<a href="javascript:void(0)" onclick="setGood('set')">精华</a>
			<a href="javascript:void(0)" onclick="setGood('cancel')">取消精华</a>
			<a href="javascript:void(0)" onclick="setTitle('set')">个性标题</a>
			<a href="javascript:void(0)" onclick="deleteArticle()">删除</a>
		</div>
		<div class="ath">
			<div class="atit">
				<div class="atit_check"><input type="checkbox" id="checkAll"></div>
				<div class="atit_tit">帖子主题</div>
			</div>	
			<div class="aauthor1">分类</div>
			<div class="aauthor3">最后回应</div>
			<div class="aauthor2">操作</div>
		</div>
		<form action="" method="post" id="subForm">
		<input type="hidden" name="opType" id="opType" value="">
		<input type="hidden" name="page"   value="${page}">
		<input type="hidden" name="gid"  value="${gid}">
			<c:forEach var="article" items="${articleList}">
				<div class="atr">
					<div class="atit">
						<div class="atit_check"><input type="checkbox" name="ids" value="${article.id}" class="checkId"></div>
						<c:if test="${article.grade==2}"><div class="atit_img"><img src="../images/ico-top.gif"></div></c:if>
						<c:if test="${article.essence=='Y'}"><div class="atit_img"><img src="../images/ico-ess.gif"></div></c:if>
						<div class="atit_tit">
							<a href="../group/post.jspx?id=${article.id}" ${article.titleStyle}>${article.title}</a>
							<span class="atit_tit_num">${article.reNumber }/${article.readNumber}</span>
						</div>
					</div>
					<div class="aauthor1">
						${article.itemName}
					</div>
					<div class="aauthor3">
						<span class="timestyle">${article.postTime}</span>
					</div>
					<div class="aauthor2">
						<a href="editArticle.jspx?id=${article.id}&gid=${gid}">修改</a>
					</div>
					<div class="clear"></div>
				</div>
			</c:forEach>
		</form>
	<div class="pagebttom">
	    <div  class="pagination">
			<p:pager url="articleManage.jspx?gid=${gid}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
		</div>
	</div>
	</div>
	<div class="clear"></div>
	<jsp:include page="../common/foot.jsp"></jsp:include>
</div>
</body>
</html>