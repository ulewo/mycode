dynamic.isloading = false;
dynamic.type=0;
dynamic.page = 1;
$(function(){
	dynamic.loadDynamic(dynamic.page);
	$("#loadmorebtn").click(function() {
		dynamic.page =dynamic.page+1;
		dynamic.loadDynamic(dynamic.page);
	});
	$("#talkBtn").bind("click",saveBlast);
	$(".trends_tag li").each(function(index){
		$(this).bind("click",function(){
			if(dynamic.isloading){
				return;
			}
			$(".trends_tag li").removeClass("tag_select");
			$(".trends_tag li").eq(index).addClass("tag_select");
			dynamic.isloading = true;
			switch(index){
				case 0:
					dynamic.loadDynamic(1);
				break;
				case 1:
					dynamic.type = 1;
					loadPage(1);
				break;
				case 2:
					dynamic.type = 2;
					loadPage(1);
				break;
			}
		});
	});
});

function loadPage(page){
	$("#loading").show();
	$("#loadmorebtn").hide();
	$("#dynamicList").empty();
	$("#pager").empty();
	$("#pager").show();
	if(dynamic.type==1){
		loadBlast(page);
	}else if(dynamic.type==2){
		loadTopic(page);
	}
}
/**
 * 加载吐槽
 * @param page
 */
function loadBlast(page){
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + '/user/dynamic4Blast?page='+page+'&userId=' + dynamic.userId,// 请求的action路径
		success : function(data) {
			dynamic.isloading = false;
			$("#loading").hide();
			if(data.resultCode = "200"){
				var list = data.result.list;
				var length = list.length;
				if(length==0){
					$("<div class='noinfo'>囧,没有发现吐槽</div>").appendTo($("#dynamicList"));
				}else{
					for ( var i = 0; i < length; i++) {
						new DynamicItem(list[i]).appendTo($("#dynamicList"));
					}
					var page = data.result.page;
					if(page.pageTotal>1){
						new Pager(page.pageTotal,10,page.page).asHtml().appendTo($("#pager"));
					}else{
						$("#pager").hide();
					}
				}
			}else{
				alert(data.msg);
			}
		}
	});
}

function loadTopic(page){
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + '/user/dynamic4Topic?page='+page+'&userId=' + dynamic.userId,// 请求的action路径
		success : function(data) {
			dynamic.isloading = false;
			$("#loading").hide();
			if(data.resultCode = "200"){
				var list = data.result.list;
				var length = list.length;
				if(length==0){
					$("<div class='noinfo'>囧,没有发现帖子</div>").appendTo($("#dynamicList"));
				}else{
					for ( var i = 0; i < length; i++) {
						new DynamicItem(list[i]).appendTo($("#dynamicList"));
					}
					var page = data.result.page;
					if(page.pageTotal>1){
						new Pager(page.pageTotal,10,page.page).asHtml().appendTo($("#pager"));
					}else{
						$("#pager").hide();
					}
				}
			}else{
				alert(data.msg);
			}
		}
	});
}

dynamic.loadDynamic = function(page){
	if(page==1){
		$("#dynamicList").empty();
	}
	$("#pager").empty();
	$("#loading").show();
	$("#loadmorebtn").hide();
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + '/user/dynamic?page='+page+'&userId=' + dynamic.userId,// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			dynamic.isloading = false;
			if(data.resultCode = "200"){
				var list = data.result.list;
				var length = list.length;
				if(length==0){
					$("<div class='noinfo'>囧,没有发现动态</div>").appendTo($("#dynamicList"));
					
				}else{
					for ( var i = 0; i < length; i++) {
						new DynamicItem(list[i]).appendTo($("#dynamicList"));
					}
					if (data.result.page.pageTotal > dynamic.page) {
						$("#loadmorebtn").css({
							"display" : "block"
						});
					} else {
						$("#loadmorebtn").hide();
					}
				}
				dynamic.page = data.result.page.page;
			}else{
				alert(data.msg);
			}
		}
	});
}


function DynamicItem(data){
	this.item = $("<div class='dy-item'></div>");
	var iconCon = $("<div class='icon-con'></div>").appendTo(this.item);
	var iconLeft = $("<div class='icon-left'><img src='"+global.realPath+"/upload/"+data.userIcon+"'/></div>").appendTo(iconCon);
	var iconRight = $("<div class='icon-right'></div>").appendTo(iconCon);
	var rightName = $("<div class='right-name'></div>").appendTo(iconRight);
	var nameLink = $("<a href='"+global.realPath+"/user/"+data.userId+"' target='_blank'>"+data.userName+"</a>").appendTo(rightName);
	var realPath = "";
	if(data.type=="T"){
		$("<span>&nbsp;&nbsp;发表文章&nbsp;&nbsp;</span><a href='"+global.realPath+"/group/"+data.gid+"/topic/"+data.id+"' target='_blank'>"+data.title+"</a>").appendTo(rightName);
	}else if(data.type=="B"){
		$("<span>&nbsp;&nbsp;发表博文&nbsp;&nbsp;</span><a href='"+global.realPath+"/user/"+data.userId+"/blog/"+data.id+"' target='_blank'>"+data.title+"</a>").appendTo(rightName);
	}else if(data.type=="L"){
		realPath = global.realPath+"/upload/";
		$("<span>&nbsp;&nbsp;发表吐槽&nbsp;&nbsp;</span><a href='"+global.realPath+"/user/"+data.userId+"/blast/"+data.id+"' target='_blank'>查看</a>").appendTo(rightName);
	}else if(data.type=="W"){
		$("<span>&nbsp;&nbsp;创建了窝窝&nbsp;&nbsp;</span><a href='"+global.realPath+"/group/"+data.gid+"' target='_blank'>"+data.title+"</a>").appendTo(rightName);
	}
	var rightTime = $("<div class='right-tiem'><span>"+data.showCreateTime+"</span><span>&nbsp;&nbsp;评论("+data.comments+")</span></div>").appendTo(iconRight);
	var summary = data.summary;
	if(data.type=='L'){
		for ( var emo in emotion_data) {
			summary = summary.replace(emo, "&nbsp;<img src='" + global.realPath
					+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
		}
	}
	var summary = $("<div class='summary'>"+summary+"</div>").appendTo(this.item);
	var imagesArray = data.imagesArray;
	if(imagesArray!=null){
		var imagUrl = "";
		var imageCon = $("<div class='imageCon'></div>").appendTo(this.item);
		var image;
		var length = imagesArray.length;
		if(length >=5){
			length = 5
		}
		for(var i=0;i<length;i++){
			image = imagesArray[i];
			imagUrl = realPath+image.substring(0,image.lastIndexOf("small")-1);
			$("<a href='"+imagUrl+"' target='_blank'><img src='"+realPath+image+"'></a><span>&nbsp;&nbsp;</span>").appendTo(imageCon);
		}
	}
	return this.item;
}



/*********吐槽**************/
function saveBlast(){
	if(global.userId==""){
		alert("请先登录");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#talkcontent").val().trim();
	if(content==""||content=="今天你吐槽了吗？"){
		warm("show","吐槽内容不能为空");
		return;
	}
	if(content.length>250){
		warm("show","吐槽内容不能超过250字符");
		return;
	}
	warm("hide","");
	btnLoading($(this),"发布中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			content:content,
			imgUrl:$("#imgUrl").val()
		},
		url : global.realPath+"/user/saveBlast.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"发布");
			if(data.result=="200"){
				info("发布成功");
				deleteImg();
				tipsInfo("2分已到碗里");
				$("#talkcontent").val("");
				$(".noinfo").remove();
				var myblast = data.blast;
				myblast.summary = myblast.content;
				myblast.comments = myblast.commentCount;
				myblast.imagesArray  = new Array(myblast.imageUrl);
				myblast.type='L';
				if($("#dynamicList").children().length>0){
					$("#dynamicList").children().eq(0).before(new DynamicItem(myblast)); 
				}else{
					new DynamicItem(myblast).appendTo($("#dynamicList")); 
				}
			}else{
				warm("show",data.msg);
			}
		}
	});
}



/*function loadTalk(type,page){
	$("#trends_list").empty();
	$("#pager").empty();
	$("<div class='loading'><img src='"+global.realPath+"/images/load.gif'></div>").appendTo($("#trends_list"));
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/user/loadTalk?userId="+userId+"&type="+type+"&page="+page,// 请求的action路径
		success : function(data) {
			$(".loading").remove();
			if(data.result=="success"){
				var list = data.data.list;
				var length = list.length;
				if(length>0){
					if(type==0||type==2){
						for(var i=0;i<length;i++){
							new UserTalkItem(list[i]).talk_item.appendTo($("#trends_list"));
						}
					}else if(type==1||type==3){
						for(var i=0;i<length;i++){
							new UserReTalkItem(list[i]).talk_item.appendTo($("#trends_list"));
						}
					}
					if(data.data.pageTotal>1){
						new Pager(data.data.pageTotal,10,data.data.page).asHtml().appendTo($("#pager"));
					}else{
						$("#pager").hide();
					}
				}else{
					$("<div class='noinfo'>没有记录!</div>").appendTo($("#trends_list"));
				}
			}
			isloading = false;
		}
	});
}
*//*********回复吐槽**********//*
function UserTalkItem(talk){
	this.talk_item = $('<div class="talk_item"></div>');
	$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+talk.userId+'"><img src="'+global.realPath+'/upload/'+talk.userIcon+'" width="40"></a></div>').appendTo(this.talk_item);
	var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
	$('<div class="talk_tit">'+
			'<a href="'+global.realPath+'/user/'+talk.userId+'">'+talk.userName+'</a>'+
			'<span>&nbsp;&nbsp;发布了吐槽</span>'+
			'</div>').appendTo(talk_con);
	var content = talk.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$('<div class="talk_content">'+content+'</div>').appendTo(talk_con);
	var Android = "";
	if(talk.sourceFrom == "A"){
		Android= "&nbsp;Android";
	}
	$('<div class="talk_info">'+
			'<span>'+talk.createTime+Android+'</span>'+
			'<a href="'+global.realPath+'/user/'+talk.userId+'/talk/'+talk.id+'" target="_blank">评论('+talk.reCount+')</a>'+
			'<div class="clear"></div>'+
			'</div>').appendTo(talk_con);
	$('<div class="clear"></div>').appendTo(this.talk_item);
}

function UserReTalkItem(talk){
	this.talk_item = $('<div class="talk_item"></div>');
	$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+talk.userId+'"><img src="'+global.realPath+'/upload/'+talk.userIcon+'" width="40"></a></div>').appendTo(this.talk_item);
	var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
	$('<div class="talk_tit">'+
			'<a href="'+global.realPath+'/user/'+talk.userId+'">'+talk.userName+'</a>'+
			'<span>&nbsp;回复了吐槽</span>'+
			'</div>').appendTo(talk_con);
	var content = talk.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	if(talk.atUserId!=""&&talk.atUserId!=null){
		content = "回复<a href='"+global.realPath+"/user/"+talk.atUserId+"'>"+talk.atUserName+"</a>："+content;
	}
	$('<div class="talk_content">'+content+'</div>').appendTo(talk_con);
	var talkcontent = talk.talkcontent;
	for ( var emo in emotion_data) {
		talkcontent = talkcontent.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$('<div class="talk_content_p"><a href="'+global.realPath+'/user/'+talk.talkUserId+'">'+talk.talkUserName+'</a>:&nbsp;'+
			talkcontent+'<a href="'+global.realPath+'/user/'+talk.talkUserId+'"/talk/'+talk.talkId+'" target="_blank">详情</a></div>').appendTo(talk_con);
	var Android = "";
	if(talk.sourceFrom == "A"){
		Android= "&nbsp;Android";
	}
	$('<div class="talk_info">'+
			'<span>'+talk.createTime+Android+'</span>'+
			'<div class="clear"></div>'+
			'</div>').appendTo(talk_con);
	
	$('<div class="clear"></div>').appendTo(this.talk_item);
}

*//*********讨论*************//*
function loadArticle(type,page){
	$("#trends_list").empty();
	$("#pager").empty();
	$("<div class='loading'><img src='"+global.realPath+"/images/load.gif'></div>").appendTo($("#trends_list"));
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/user/loadArticle?userId="+userId+"&type="+type+"&page="+page,// 请求的action路径
		success : function(data) {
			$(".loading").remove();
			if(data.result=="success"){
				var list = data.data.list;
				var length = list.length;
				if(length>0){
					if(type==0||type==2){
						for(var i=0;i<length;i++){
							new UserArticleItem(list[i]).talk_item.appendTo($("#trends_list"));
						}
					}else if(type==1||type==3){
						for(var i=0;i<length;i++){
							new UserReArticleItem(list[i]).talk_item.appendTo($("#trends_list"));
						}
					}
					if(data.data.pageTotal>1){
						new Pager(data.data.pageTotal,10,data.data.page).asHtml().appendTo($("#pager"));
					}else{
						$("#pager").hide();
					}
				}else{
					$("<div class='noinfo'>没有记录!</div>").appendTo($("#trends_list"));
				}
			}
			isloading = false;
		}
	});
}

function UserArticleItem(article){
	this.talk_item = $('<div class="talk_item"></div>');
	$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+article.authorId+'"><img src="'+global.realPath+'/upload/'+article.authorIcon+'" width="40"></a></div>').appendTo(this.talk_item);
	var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
	$('<div class="talk_tit">'+
			'<a href="'+global.realPath+'/user/'+article.authorId+'">'+article.authorName+'</a>'+
			'<span>&nbsp;在&nbsp;</span>'+
			'<a href="'+global.realPath+'/group/'+article.gid+'">'+article.groupName+'</a>'+
			'<span>&nbsp;发表了文章&nbsp;</span>'+
			'<a href="'+global.realPath+'/group/'+article.gid+"/topic/"+article.id+'">'+article.title+'</a>'+
			'</div>').appendTo(talk_con);
	$('<div class="talk_content">'+article.summary+'</div>').appendTo(talk_con);
	var Android = "";
	if(article.sourceFrom == "A"){
		Android= "&nbsp;Android";
	}
	$('<div class="talk_info">'+
			'<span>'+article.postTime+Android+'</span>'+
			'<a href="'+global.realPath+'/group/'+article.gid+"/topic/"+article.id+'" target="_blank">评论('+article.reNumber+')</a>'+
			'<a href="'+global.realPath+'/group/'+article.gid+"/topic/"+article.id+'" target="_blank">查看原文</a>'+
			'<div class="clear"></div>'+
			'</div>').appendTo(talk_con);
	$('<div class="clear"></div>').appendTo(this.talk_item);
}

function UserReArticleItem(article){
	this.talk_item = $('<div class="talk_item"></div>');
	$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+article.authorid+'"><img src="'+global.realPath+'/upload/'+article.authorIcon+'" width="40"></a></div>').appendTo(this.talk_item);
	var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
	$('<div class="talk_tit">'+
			'<a href="'+global.realPath+'/user/'+article.authorid+'">'+article.authorName+'</a>'+
			'<span>&nbsp;回复了文章&nbsp;</span>'+
			'<a href="'+global.realPath+'/group/'+article.gid+"/topic/"+article.articleId+'">'+article.articleTitle+'</a>'+
			'</div>').appendTo(talk_con);
	var content = article.content;
	if(article.atUserId!=""&&article.atUserId!=null){
		content = "回复&nbsp;<a href='"+global.realPath+"/user/"+article.atUserId+"'>"+article.atUserName+"</a>："+content;
	}
	$('<div class="talk_content">'+content+'</div>').appendTo(talk_con);
	var Android = "";
	if(article.sourceFrom == "A"){
		Android= "&nbsp;Android";
	}
	$('<div class="talk_info">'+
			'<span>'+article.reTime+Android+'</span>'+
			'<a href="'+global.realPath+'/group/'+article.gid+"/topic/"+article.articleId+'" target="_blank">查看原文</a>'+
			'<div class="clear"></div>'+
			'</div>').appendTo(talk_con);
	$('<div class="clear"></div>').appendTo(this.talk_item);
}

*//*******博客*********//*
function loadBlog(type,page){
		$("#trends_list").empty();
		$("#pager").empty();
		$("<div class='loading'><img src='"+global.realPath+"/images/load.gif'></div>").appendTo($("#trends_list"));
		$.ajax({
			async : true,
			cache : true,
			type : 'GET',
			dataType : "json",
			url : global.realPath+"/user/loadBlog?userId="+userId+"&type="+type+"&page="+page,// 请求的action路径
			success : function(data) {
				$(".loading").remove();
				if(data.result=="success"){
					var list = data.data.list;
					var length = list.length;
					if(length>0){
						if(type==0||type==2){
							for(var i=0;i<length;i++){
								new UserBlogItem(list[i]).talk_item.appendTo($("#trends_list"));
							}
						}else if(type==1||type==3){
							for(var i=0;i<length;i++){
								new UserReBlogItem(list[i]).talk_item.appendTo($("#trends_list"));
							}
						}
						if(data.data.pageTotal>1){
							new Pager(data.data.pageTotal,10,data.data.page).asHtml().appendTo($("#pager"));
						}else{
							$("#pager").hide();
						}
					}else{
						$("<div class='noinfo'>没有记录!</div>").appendTo($("#trends_list"));
					}
				}
				isloading = false;
			}
		});
}

function UserBlogItem(blog){
	this.talk_item = $('<div class="talk_item"></div>');
	$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+blog.userId+'"><img src="'+global.realPath+'/upload/'+blog.userIcon+'" width="40"></a></div>').appendTo(this.talk_item);
	var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
	$('<div class="talk_tit">'+
			'<a href="'+global.realPath+'/user/'+blog.userId+'">'+blog.userName+'</a>'+
			'<span>&nbsp;发表了博文&nbsp;</span>'+
			'<a href="'+global.realPath+'/user/'+blog.userId+"/blog/"+blog.id+'">'+blog.title+'</a>'+
			'</div>').appendTo(talk_con);
	$('<div class="talk_content">'+blog.summary+'</div>').appendTo(talk_con);
	$('<div class="talk_info">'+
			'<span>'+blog.postTime+'</span>'+
			'<a href="'+global.realPath+'/user/'+blog.userId+"/blog/"+blog.id+'" target="_blank">评论('+blog.reCount+')</a>'+
			'<a href="'+global.realPath+'/user/'+blog.userId+"/blog/"+blog.id+'" target="_blank">查看原文</a>'+
			'<div class="clear"></div>'+
			'</div>').appendTo(talk_con);
	$('<div class="clear"></div>').appendTo(this.talk_item);
}

function UserReBlogItem(article){
		this.talk_item = $('<div class="talk_item"></div>');
		$('<div class="talk_icon"><a href="'+global.realPath+'/user/'+article.userId+'"><img src="'+global.realPath+'/upload/'+article.reUserIcon+'" width="40"></a></div>').appendTo(this.talk_item);
		var talk_con = $('<div class="talk_con"></div>').appendTo(this.talk_item);
		$('<div class="talk_tit">'+
				'<a href="'+global.realPath+'/user/'+article.userId+'">'+article.userName+'</a>'+
				'<span>&nbsp;回复了博文&nbsp;</span>'+
				'<a href="'+global.realPath+'/user/'+article.userId+"/blog/"+article.blogId+'">'+article.blogTitle+'</a>'+
				'</div>').appendTo(talk_con);
		var content = article.content;
		if(article.atUserId!=""&&article.atUserId!=null){
			content = "回复&nbsp;<a href='"+global.realPath+"/user/"+article.atUserId+"'>"+article.atUserName+"</a>："+content;
		}
		$('<div class="talk_content">'+content+'</div>').appendTo(talk_con);
		var Android = "";
		if(article.sourceFrom == "A"){
			Android= "&nbsp;Android";
		}
		$('<div class="talk_info">'+
				'<span>'+article.postTime+Android+'</span>'+
				'<a href="'+global.realPath+'/user/'+article.userId+"/blog/"+article.blogId+'" target="_blank">查看原文</a>'+
				'<div class="clear"></div>'+
				'</div>').appendTo(talk_con);
		$('<div class="clear"></div>').appendTo(this.talk_item);
}*/


