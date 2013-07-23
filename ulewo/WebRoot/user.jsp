<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${user.userName}的空间--你乐我</title>
<meta name="description" content="分享快乐，分享囧事，分享生活，一份快乐两个人分享,就会成为两份快乐；一份忧愁两个人分担，便会成为半份忧愁 --你乐我">
<meta name="keywords" content="快乐，糗事，囧事，搞笑图片，笑话，生活,冷笑话,笑话网,语录 — 你乐我">
<link rel="stylesheet" type="text/css" href="css/user.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.Jcrop.css" />
</head>
<body>
<jsp:include page="menue.jsp"></jsp:include>
<c:set var="path" value=""></c:set>
<div class="body_con">
	<div class="left">
		<div class="avator_con">
			<div class="avator"><img src="${userVo.avatar}" width="70" id="avatarIcon"></div>
			<div class="name">${userVo.userName}</div>
			<div class="clear"></div>
		</div>
		<div class="menue_tag" id="menue_tag">
			<ul>
				<li class="sel" type="my">我发布的</li>
				<li type="re">我评论的</li>
				<c:if test="${param.uid==user.uid}">
				<li type="avatar">修改图像</li>
				</c:if>
				
			</ul>
		</div>
		<div id="con" class="con">
			
		</div>
		<div class = "pageArea"></div>
		<div id="avatar">
			<div id="custArea">
					<div>
						<iframe src="uploadavatar.jsp" width="350" height="27" frameborder="0" id="iframe"></iframe>
					</div>
	  				<div id="imgArea">
						  <div class="cutarea" id="cutarea">
						  </div>
						  <div class="purviews" id="purviews" >
						  </div>
		  				  <div style="clear:left;"></div>
	  				</div>
	  				<div style="margin-top:10px;">
	  					<a href="javascript:saveCutImg()" class="subtn" id="cutSave">保存</a>
	  					<img src="images/loading.gif"  id="loadImg"/>
	  				</div>
		 		</div>
		</div>
	</div>
	<div class="right">
		<jsp:include page="right.jsp"></jsp:include>		
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
<script type="text/javascript" src="js/user.js"></script>
<script type="text/javascript" src="js/common_ajax.js"></script>
<script type="text/javascript" src="js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="js/jQuery.UtrialAvatarCutter.js"></script>
<script type="text/javascript">
var cutter = new jQuery.UtrialAvatarCutter(
		{
			//主图片所在容器ID
			content : "cutarea",
			
			//缩略图配置,ID:所在容器ID;width,height:缩略图大小
			purviews : [{id:"purviews",width:70,height:70}],
			
			//选择器默认大小
			selector : {width:70,height:70}
		}
	);
GlobalVar.type="my";
GlobalVar.uid = "${param.uid}";
$(function() {
	$("#menue_tag").find("li").bind("click",function(){
		$("#menue_tag").find("li").css({height:"24px",background:"#F7F7F7"});
		$(this).css({height:"25px",background:"#FFFFFF"});
		$("#con").html("");
		GlobalVar.type = $(this).attr("type");
		if(GlobalVar.type=="re"||GlobalVar.type=="my"){
			$("#con").show();
			$(".pageArea").show();
			$("#avatar").hide();
			loadArticle(1);
		}else if(GlobalVar.type=="avatar"){
			$("#con").hide();
			$("#avatar").show();
			$(".pageArea").hide();
		}
	});
	
	//图片裁剪
	cutter.init();
})
</script>
</body>
</html>