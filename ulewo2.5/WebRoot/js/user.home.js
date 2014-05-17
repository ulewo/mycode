var isloading = false;
var loadtype = 0;
var type=0;
$(function(){
	$("#talkBtn").bind("click",saveBlast);
	$(".trends_tag li").each(function(index){
		$(this).bind("click",function(){
			if(isloading){
				return;
			}
			$(".trends_tag li").removeClass("tag_select");
			$(".trends_tag li").eq(index).addClass("tag_select");
			$(".trends_item").hide();
			$(".trends_item").eq(index).show();
			isloading = true;
			loadtype = index;
			type=0;
			$(".trends_item_talk li").removeClass("select");
			$(".trends_item_talk li").eq(0).addClass("select");
			$(".trends_item_article li").removeClass("select");
			$(".trends_item_article li").eq(0).addClass("select");
			$(".trends_item_blog li").removeClass("select");
			$(".trends_item_blog li").eq(0).addClass("select");
			switch(index){
				case 0:
					loadPage(1);
				break;
				case 1:
					loadPage(1);
				break;
				case 2:
					loadPage(1);
				break;
			}
		});
	});
	$(".trends_item_talk li").each(function(index){
		$(this).bind("click",function(){
			if(isloading){
				return;
			}
			isloading  = true;
			$(".trends_item_talk li").removeClass("select");
			$(".trends_item_talk li").eq(index).addClass("select");
			type = index;
			loadPage(1);
		});
	});
	
	$(".trends_item_article li").each(function(index){
		$(this).bind("click",function(){
			if(isloading){
				return;
			}
			isloading  = true;
			$(".trends_item_article li").removeClass("select");
			$(".trends_item_article li").eq(index).addClass("select");
			type = index;
			loadPage(1);
		});
	});
	
	$(".trends_item_blog li").each(function(index){
		$(this).bind("click",function(){
			if(isloading){
				return;
			}
			isloading  = true;
			$(".trends_item_blog li").removeClass("select");
			$(".trends_item_blog li").eq(index).addClass("select");
			type = index;
			loadPage(1);
		});
	});
	loadTalk(type,1);
});

function loadPage(page){
	switch(loadtype){
		case 0:
			loadTalk(type,page);
		break;
		case 1:
			loadArticle(type,page);
		break;
		case 2:
			loadBlog(type,page);
		break;
	}
}

/*********吐槽**************/
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
				$(".noinfo").remove();
				if($(".talkitem").length>0){
					$(".talkitem").eq(0).before(new TalkItem(data.blast).item);
				}else{
					new TalkItem(data.blast).item.appendTo($("#talklist"));
				}
				$("#talkcontent").val("");
				deleteImg();
				tipsInfo("1分已到碗里");
			}else{
				warm("show",data.msg);
			}
		}
	});
}

function loadInitTalk(){
}
