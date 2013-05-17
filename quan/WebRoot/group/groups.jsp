<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>圈子、群组 -有乐窝</title>
<meta name="description" content="圈子、群组 -有乐窝">
<meta name="keywords" content="圈子、群组 -有乐窝">
<link rel="stylesheet"  href="../css/allgroup.css" type="text/css"  />
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="../js/dialog.js"></script>
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script src="../dialog/artDialog.ext.js"></script>
<script type="text/javascript" src="../js/group.index.js"></script>
<script type="text/javascript">
		$(function(){
		$(".group").mouseover(function(){
			$(this).css("background","#F5F5F5");
		});
		$(".group").mouseout(function(){
			$(".group").css("background","#FFFFFF");
		});
	});
</script>
<style>
.menue ul li a.selected2{background:url("../images/nav.gif") no-repeat 0px -63px;font-weight:bolder;height:22px;color:#494949}
</style>	
</head>
<body>
<%@ include file="../common/head.jsp" %>
<div class="bodycon">
<jsp:include page="../common/head.jsp"/>
		<div class="left">
			<div class="groupTitle">所有圈子</div>
			<c:forEach var="group" items="${list}">
			<div class="group" >
				<div class="allgroupleft">
					<a href="group.jspx?groupNumber=${group.groupNumber }"><img src="../upload/${group.groupIcon}" border="0"></a>
				</div>
				<div class="allgroupright">
					<div class="groupinfo">
						<a href="group.jspx?groupNumber=${group.groupNumber }">${group.groupName }</a>
						&nbsp;&nbsp;创建于：${group.createTime}
						&nbsp;&nbsp;创建者：<a href="../user/userInfo.jspx?userId=${group.groupAuthor}">${group.authorName}</a>
						&nbsp;&nbsp;共${group.members}人
						&nbsp;&nbsp;主题${group.topicCount}
					</div>
					<div class="desc">
						${fn:substring(group.groupDesc,0,150)}......
					</div>
				</div>
				<div style="clear:both;"></div>
			</div>	
			</c:forEach>
		<div class="pagination">
			<%
			int pages = 1;
			int pageTotal = 0;
			int pageNum = 11;
			int beginNum = 0;
			int endNum = 0;
			String pageStr = request.getAttribute("page").toString();
			String pageTotalStr = request.getAttribute("pageTotal").toString();
			if(pageStr!=null){
				pages = Integer.parseInt(pageStr);
			}
			if(pageTotalStr!=null){
				pageTotal = Integer.parseInt(pageTotalStr);
			}
			
			if(pageNum>pageTotal){
				beginNum = 1;
				endNum = pageTotal;
			}
			
			if(pages-pageNum/2<1){
				beginNum = 1;
				endNum = pageNum;
			}else{
				beginNum = pages-pageNum/2;
				endNum = pages+pageNum/2;
			}
			
			if(pageTotal-pages<pageNum/2){
				beginNum = pageTotal - pageNum+1;
			}
			if(beginNum<1){
				beginNum = 1;
			}
			if(endNum>pageTotal){
				endNum = pageTotal ;
			}
			String url = "allGroups.jspx?page=";
			out.print("<ul>");	
			if(pages>1){
				out.print("<li><a href='"+url+1+"' class='prePage'>首&nbsp;&nbsp;页</a></li>");
				out.print("<li><a href='"+url+(pages-1)+"' class='prePage'><上一页</a></li>");		
			}
			for(int i= beginNum;i<=endNum;i++){
				if(pageTotal>1){
					if(i==pages){
						out.print("<li class='nowPage'>"+pages+"</li>");	
					}else{
						out.print("<li><a href='"+url+i+"'>"+i+"</a></li>");	
					}
				}
			}
			if(pages<pageTotal){
				out.print("<li><a href='"+url+(pages+1)+"' class='nextPage'>下一页></a></li>");	
				out.print("<li><a href='"+url+(pageTotal)+"' class='prePage'>尾&nbsp;&nbsp;页</a></li>");
			}
			out.print("</ul>");
			%>
		</div>
	</div>
	<div class="right">
		<div class="usersTitle"><span style="color:red;">新建</span>圈子</div>
			<div class="groups">
				<c:set var="num" value="0"></c:set>
				<c:forEach var="group" items="${latestGroups}">
					<div class="newgroup">
						<div class="groupioc">
							<div class="membericon">
							<a href="group.jspx?groupNumber=${group.groupNumber}"><img src="../upload/${group.groupIcon}" border="0"></a>
							</div>
						</div>
						<div class="groupcon">
							<div class="grouptitle"><a href="group.jspx?groupNumber=${group.groupNumber}">${group.groupName}</a></div>
							<div class="groupdesc">${fn:substring(group.groupDesc,0,35)}......</div>
						</div>
					</div>
				</c:forEach>
		</div>
	</div>
	<div style="clear:both;"></div>
</div>
<jsp:include page="../common/foot.jsp"/>	
</body>
</html>