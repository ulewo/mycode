<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${userVo.userName}个人博客-有乐窝</title>
<meta name="description" content="${userVo.userName}个人博客-有乐窝">
<meta name="keywords" content="${userVo.userName}个人博客-有乐窝">
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.blog.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
			<%@ include file="left.jsp" %>
			<div class="left_item">
				<div class="left_item_tit">博客分类</div>
				<div class="left_img_p">
					<div class="blog_item"><a href="${realPath}/user/${userId}/blog">全部文章</a><span>(${totalCount})</span></div>
					<c:forEach var="item" items="${blogItemList}">
						<div class="blog_item"><a href="${realPath}/user/${userId}/blog/itemId/${item.id}">${item.itemName}</a><span>(${item.articleCount})</span></div>
					</c:forEach>
				</div>
			</div>
			<div class="left_item">
				<div class="left_item_tit">阅读排行</div>
				<div class="left_img_p">
					<c:set var="num" value="1"/>
					<c:forEach var="blog" items="${hotlist}">
						<div class="blog_rang"><span style="color:#3E62A6">${num}.</span><a href="${realPath}/user/${blog.userId}/blog/${blog.id}">${blog.title}</a></div>
						<c:set var="num" value="${num+1}"></c:set>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="blog_list">
				<div class="right_top_m">
					<a href="${realPath}/user/${userId}">空间</a>&gt;&gt;<a href="${realPath}/user/${userId}/blog">博客</a>
					<c:if test="${blogitem!=null}">
						&gt;&gt;${blogitem.itemName}
					</c:if>
				</div>
				<c:forEach var="blog" items="${result.list}">
					<div class="blog_list_item">
						<div class="blog_list_tit"><a href="${realPath}/user/${userId}/blog/${blog.id}">${blog.title}</a></div>
						<div class="blog_item_op">
							<span>分类:</span>
							<c:if test="${blog.itemId==null||blog.itemId==''}">
								<a href="${realPath}/user/${userId}/blog">全部分类</a>
							</c:if>
							<a href="${realPath}/user/${userId}/blog/itemId/${blog.itemId}">${blog.itemName}</a>
							<c:if test="${user.userId==userId}">(<a href="${realPath}/manage/edit_blog.action?id=${blog.id}">修改</a>|<a href="javascript:void(0)" name="${blog.id}" class="del">删除</a>)</c:if>
						</div>
						<div class="blog_summary">
							${blog.summary}
						</div>
						<div class="blog_item_info">
							<span>发布于 ${blog.postTime}，阅读(${blog.readCount})|评论(${blog.reCount})</span>
							<a href="${realPath}/user/${userId}/blog/${blog.id}">阅读全文</a>
						</div>
					</div>
				</c:forEach>
				<c:if test="${empty result.list}">
					<div class="left_noinfo">没有发现博文</div>
				</c:if>
				<div class="pagination">
					<c:if test="${itemId==null}">
						<p:pager url="${realPath}/user/${userId}/blog" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
					</c:if>
					<c:if test="${itemId!=null}">
						<p:pager url="${realPath}/user/${userId}/blog/itemId/${itemId}" page="${result.page}" pageTotal = "${result.pageTotal }"></p:pager>
					</c:if>
				</div>
			</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	 <%@ include file="../common/foot.jsp" %>
	 <script type="text/javascript">
	 $(function(){
		 $(".del").bind("click",function(){
			 var id  =$(this).attr("name");
			 var obj  = $(this);
			 $.ajax({
					async : true,
					cache : false,
					type : 'GET',
					dataType : "json",
					url : global.realPath+"/manage/deleteBlog.action?id="+id,// 请求的action路径
					success : function(data) {
						if (data.result == "fail") {
							alert(data.message);
						} else {
							obj.parent().parent().remove();
						}
					}
				});
		 });
	 });
	 </script>
	<div class="righ_ad">
		<div><a href="javascript:void(0)" onclick="$('.righ_ad').hide()">关闭</a></div>
		<div>
		<script type="text/javascript">
			/*160*600，创建于2013-7-10*/
			var cpro_id = "u1317726";
			</script>
		<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  		</div>
	</div>
</body>
</html>