<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收藏管理-有乐窝</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.manage.public.css">
<style type="text/css">
#selected8 a{background:#ffffff;color:#333333;font-weight:bold;}
.favorite_item{list-style:none;border-bottom:2px solid #DCDCDC;height:25px;margin:0px;padding:0px;margin-top:10px;}
.favorite_item li{float:left;margin-left:15px;display:inline-block;}
.favorite_item li a{color:#494949;text-decoration:none;font-size:13px;display:inline-block;padding:3px 5px 0px 5px;;height:20px;text-align:center;}
.favorite_item li a:hover{height:22px;color:#494949;border-bottom:2px solid #64C5E8;color:#64C5E8}
.favorite_item li.select a{height:22px;color:#494949;border-bottom:2px solid #64C5E8;color:#64C5E8}
.item{margin-top:10px;padding:0px 5px;}
a.link {display:inline-block;float:left;}
a.del{display:inline-block;float:right;} 
</style>
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
	  		<%@ include file="left.jsp" %>
		</div>
		<div class="right" style="padding-bottom:10px;">
			<div class="right_top_m">
				<a href="${realPath}/user/${user.userId}">空间</a>&gt;&gt; 收藏管理
			</div>
			<ul class="favorite_item">
				<li class="select"><a href="javascript:void(0)">帖&nbsp;&nbsp;子</a></li>
				<li><a href="javascript:void(0)">博&nbsp;&nbsp;客</a></li>
			</ul>
			<div id="favorite_list">
				
			</div>
			<div id="pager" class="pagination" style="margin-top:10px;"></div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.manage.userinfo.js"></script>
	<script type="text/javascript">
	var isloading = false;
	var type="A";
 	$(function(){
 		$(".favorite_item li").each(function(index){
 			$(this).bind("click",function(){
 				if(isloading){
 					return;
 				}
 				isloading  = true;
 				$(".favorite_item li").removeClass("select");
 				$(".favorite_item li").eq(index).addClass("select");
 				if(index==0){
 					type="A";
 				}else if(index==1){
 					type="B";
 				}
 				loadPage(1);
 			});
 		});
 		loadPage(1);
 		$(".del").live("click",function(){
 			var curObj= $(this);
 			var type = $(this).attr("type");
 			var articleId = $(this).attr("articleId");
 			if(confirm("确定要删除此条收藏吗？")){
 				$.ajax({
 	 	 			async : true,
 	 	 			cache : true,
 	 	 			type : 'GET',
 	 	 			dataType : "json",
 	 	 			url : global.realPath+"/manage/deleteFavoriteArticle.action?type="+type+"&articleId="+articleId,// 请求的action路径
 	 	 			success : function(data) {
 	 	 				if(data.result=="success"){
 	 	 					curObj.parent().remove();
 	 	 				}else{
 	 	 					alert(data.message);
 	 	 				}
 	 	 			}
 	 	 		});
			}
 			
 		});
 	});
 	function loadPage(page){
 		loadFavorite(type,page);
 	}
 	function loadFavorite(type,page){
 		$("#favorite_list").empty();
 		$("#pager").empty();
 		$("<div class='loading' style='text-align:center;margin-top:10px;'><img src='"+global.realPath+"/images/load.gif'></div>").appendTo($("#favorite_list"));
 		$.ajax({
 			async : true,
 			cache : true,
 			type : 'GET',
 			dataType : "json",
 			url : global.realPath+"/manage/queryFavoriteArticle.action?type="+type+"&page="+page,// 请求的action路径
 			success : function(data) {
 				$(".loading").remove();
 				if(data.result=="success"){
 					var list = data.data.list;
 					var length = list.length;
 					if(length>0){
 						if(type=="A"){
 							for(var i=0;i<length;i++){
								$("<div class='item'>"+
									"<a class='link' href='"+global.realPath+"/group/"+list[i].partId+"/topic/"+list[i].articleId+"' target='_blank'>"+list[i].title+"</a>"+
									"<a class='del' href='javascript:void(0)' articleId='"+list[i].articleId+"' type='A'>删除</a>"+
									"<div class='clear'></div>"+	
									"</div>").appendTo($("#favorite_list"));
							
							}
 						}else if(type=="B"){
 							for(var i=0;i<length;i++){
 								$("<div class='item'>"+
 										"<a class='link' href='"+global.realPath+"/user/"+list[i].partId+"/blog/"+list[i].articleId+"' target='_blank'>"+list[i].title+"</a>"+
 										"<a class='del' href='javascript:void(0)' articleId='"+list[i].articleId+"' type='B'>删除</a>"+
 										"<div class='clear'></div>"+
 										"</div>").appendTo($("#favorite_list"));
							}
 						}
 						if(data.data.pageTotal>1){
							new Pager(data.data.pageTotal,10,data.data.page).asHtml().appendTo($("#pager"));
						}else{
							$("#pager").hide();
						}
 					}else{
 						if(type=="A"){
 							$("<div class='noinfo'>没有收藏帖子</div>").appendTo($("#favorite_list"));
 						}else if(type=="B"){
 							$("<div class='noinfo'>没有收藏博文!</div>").appendTo($("#favorite_list"));
 						}
 						
 					}
 				}
 				isloading = false;
 			}
 		});
 	}
 </script>
	<%@ include file="../common/foot_manage.jsp" %>
</body>
</html>