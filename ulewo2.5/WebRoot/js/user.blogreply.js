var page = 1
$(function(){
$("#sendBtn").bind("click",subReply);
	$(document).click(function() {
		$('#pm_emotion_cnt').hide();
	});
	$("#pm_emotion_cnt").click(function(event) {
		event.stopPropagation();
	});
	$(".pm_emotions_bd").find("a").each(function(index) {
		$(this).bind("click", function() {
			var curValue = $("#content").val();
			$("#content").val(curValue + $(this).attr("title"));
			$("#pm_emotion_cnt").hide();
		});
	});
	$("#op_favorite a").live("click",function(){
		if(global.userId==""){
			alert("请先登录");
			return;
		}
		if($(this).attr("disable")=="disable"){
			return;
		}
		btnLoading($(this),"收藏中.....");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				partId:userId,
				articleId:blogId,
				type : "B"
			},
			url : global.realPath+"/user/favoriteArticle.action",// 请求的action路径
			success : function(data) {
				if (data.result =="200") {
					$("#favoriteCount").text(parseInt($("#favoriteCount").text())+1);
					$("#op_favorite").html('<span>已收藏</span>');
				} else {
					btnLoaded($("#op_favorite a"),"我要收藏");
					alert(data.msg);
				}
			}
		});
	});
	$(".share a").each(function(index){
		$(this).bind("click",function(){
			dispatche(index);
		});
	});
	checkFavorite(blogId,"B");
	lazyLoadImage("blog_content");
	
	SyntaxHighlighter.all();
	
	initReply(1);
	$(".blog_content img").live("click",function(){
		var imgsrc = $(this).attr("src");
		window.open(imgsrc);
	});
	$("#loadmorebtn").click(function() {
		page++;
		initReply(page);
	});
});

function showEmotion() {
	$("#pm_emotion_cnt").show();
}

function subReply() {
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#content").val();
	if (content.trim() == "") {
		warm("show","回复内容不能为空");
		return;
	}
	warm("hide","");
	btnLoading($(this),"发表留言中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			blogAuthor:userId,
			content : content,
			blogId : blogId,
			atUserName : $("#atUserName").val(),
			atUserId : $("#atUserId").val(),
			"time" : new Date()
		},
		url : global.realPath+"/user/saveComment.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#sendBtn"),"发表留言");
			if (data.result == "200") {
				tipsInfo("2分已到碗里");
				$(".noinfo").remove();
				if($(".reply_item").length>0){
					$(".reply_item").eq(0).before(new NotePanle(data.comment).asHtml());
				}else{
					new NotePanle(data.comment).asHtml().appendTo($("#messagelist"));
				}
				resetForm();
			} else {
				warm("show",data.msg);
			}
		}
	});
}

function resetForm() {
	if ($("#name") != null) {
		$("#name").val("");
	}
	if ($("#checkCode") != null) {
		$("#checkCode").val("");
	}
	$("#content").val("");
	if ($("#comment_form_at")[0] != null) {
		$("#comment_form_at").remove();
	}
	$("#atUserId").val("");
	$("#atUserName").val("");
}

function initReply(page) {
	$("#loading").show();
	$("#loadmorebtn").hide();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data:{
			"blogId":blogId,
			"userId":userId,
			"page":page
		},
		dataType : "json",
		url : realPath+"/user/blogComment",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			var rowData = data.rowData;
			if (data.result == "200") {
				if (rowData.list.length > 0) {
					for ( var i = 0,length = rowData.list.length; i < length; i++) {
						new NotePanle(rowData.list[i]).asHtml().appendTo(
								"#messagelist");
					}
					if (rowData.page.pageTotal > 1) {
						$(".pagination").show();
					}
					if (rowData.page.pageTotal > page) {
						$("#loadmorebtn").css({
							"display" : "block"
						});
					} else {
						$("#loadmorebtn").hide();
					}
					ulewo_common.setPostion();
				} else {
					$("<span class='noinfo'>没有评论，速度抢沙发！</span>").appendTo(
							"#messagelist");
				}
			}
		}
	});
}

function NotePanle(note) {
	// 最外层div
	this.noteCon = $("<div class='reply_item'></div>");
	// @定位
	$("<a name='re" + note.id + "'></a>").appendTo(this.noteCon);
	// 头像
	$("<div class='reply_usericon'><img src='"+realPath+"/upload/" + note.userIcon
					+ "' width='35'></div>")
			.appendTo(this.noteCon);
	
	// 留言信息
	var reply_con_p = $("<div class='reply_con_p'></div>").appendTo(this.noteCon);
	// 姓名,回复内容
	var reply_con_info = $("<div class='reply_con_info'></div>")
			.appendTo(reply_con_p);
	
		$("<span><a href='"+realPath+"/user/"
						+ note.userId + "'>" + note.userName + "</a></span>")
				.appendTo(reply_con_info);
	
	if (note.atUserId != ""&&note.atUserId != null) {
		$(
				"<span class='reply_re'>回复</span><span><a href='"+realPath+"/user/"
						+ note.atUserId
						+ "'>"
						+ note.atUserName
						+ "</a></span>").appendTo(reply_con_info);
	}
	var content = note.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$("<span class='reply_con_content'>" + content + "</span>").appendTo(reply_con_info);
	var reply_info = $("<div class='reply_info'></div>").appendTo(reply_con_p);
	
	if (note.sourceFrom == "A") {
		$(
				"<div class='reply_time'>" + note.showCreateTime
						+ "&nbsp;(Android)&nbsp;</div>").appendTo(reply_info);
	} else {
		$("<div class='reply_time'>" + note.showCreateTime + "</div>")
				.appendTo(reply_info);
	}
	
	var reply_op = $("<div class='reply_op'></div>").appendTo(reply_info);
	if(sessionUserId!=""){
		$("<a href='javascript:void(0)'>回复</a>").bind("click", {
			atUserId : note.userId,
			atUserName : note.userName,
		}, this.quote).appendTo(reply_op);
	}
}


NotePanle.prototype = {
	asHtml : function() {
		return this.noteCon;
	},
	quote : function(event) {
		if ($("#comment_form_at")[0] != null) {
			$("#comment_form_at").remove();
		}
		var atUserName = event.data.atUserName;
		var atUserId = event.data.atUserId;
		$("#atUserId").val(atUserId);
		$("#atUserName").val(atUserName);
		$("#content").focus();
		var quote_panle = $("<div id='comment_form_at' class='comment_form_at' style='margin-top:10px;'></div>");
		$("#subform").before(quote_panle);
		$("<a href='javasccript:void(0)'>@" + atUserName + "</a>").appendTo(
				quote_panle);
		$("<span><img src='"+realPath+"/images/delete.png'></span>")
				.appendTo(quote_panle).bind("click", function() {
					$("#comment_form_at").remove();
					$("#atUserId").val("");
					$("#atUserName").val("");
				});
	}
}

function delquote() {
	$("#quote_panle").remove();
}
