$(function(){
	lazyLoadImage("blog_content");
	$(".blog_content pre").each(function () {
        var $this = $(this);
        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
            var lang = $this.attr("class").split(';')[0].split(':')[1];
            $this.attr('name', 'code');
            $this.attr('class', lang);
        }
    });
    dp.SyntaxHighlighter.HighlightAll('code');
   // var s = {"highlightJsUrl":window.UEDITOR_HOME_URL+"third-party/SyntaxHighlighter/shCore.js","highlightCssUrl":window.UEDITOR_HOME_URL+"third-party/SyntaxHighlighter/shCoreDefault.css"}
	//uParse("div .blog_content",s);
	initReply(1);
	$("#sendBtn").bind("click",subReply);
});

function subReply() {
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#content").val();
	if (content.trim() == "") {
		warm("show","回复内容不能为空");
		return;
	}
	if (content.trim().length > 500) {
		warm("show","回复内容不能超过500字符");
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
		url : global.realPath+"/user/saveReplay.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#sendBtn"),"发表留言");
			if (data.result == "fail") {
				warm("show",data.message);
			} else {
				$(".noinfo").remove();
				if($(".reply_item").length>0){
					$(".reply_item").eq(0).before(new NotePanle(data.note).asHtml());
				}else{
					new NotePanle(data.note).asHtml().appendTo($("#messagelist"));
				}
				resetForm();
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
}

function initReply(i) {
	$("<img src='"+global.realPath+"/images/load.gif'>评论加载中......").appendTo($("#messagelist"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : realPath+"/user/replayList?blogId=" + blogId,// 请求的action路径
		success : function(data) {
			$("#messagelist").empty();
			$(".pagination").empty();
			var result = data.paginResult;
			if (data.result == "success") {
				if (result.list.length > 0) {
					for ( var i = 0,length = result.list.length; i < length; i++) {
						new NotePanle(result.list[i]).asHtml().appendTo(
								"#messagelist");
					}
					if (result.pageTotal > 1) {
						$(".pagination").show();
					}
				} else {
					$("<span class='noinfo'>没有评论，速度抢沙发！</span>").appendTo(
							"#messagelist");
				}

			}
		}
	});
}

function NotePanle(note) {
	var reUserIcon = note.reUserIcon || "default.gif";
	// 最外层div
	this.noteCon = $("<div class='reply_item'></div>");
	// @定位
	$("<a name='re" + note.id + "'></a>").appendTo(this.noteCon);
	// 头像
	if(reUserIcon!=""&&reUserIcon!=null){
		$(
				"<div class='reply_usericon'><img src='"+realPath+"/upload/" + reUserIcon
						+ "' width='35'></div>")
				.appendTo(this.noteCon);
	}else{
		$("<div class='reply_usericon'><img src='"+realPath+"/upload/default.gif' width='35'></div>").appendTo(this.noteCon);
	}
	
	// 留言信息
	var reply_con_p = $("<div class='reply_con_p'></div>").appendTo(this.noteCon);
	// 姓名,回复内容
	var reply_con_info = $("<div class='reply_con_info'></div>")
			.appendTo(reply_con_p);
	if (note.userId != "") {
		$(
				"<span><a href='"+realPath+"/user/"
						+ note.userId + "'>" + note.userName + "</a></span>")
				.appendTo(reply_con_info);
	} else {
		$("<span class='note_name'>" + note.userName + "</span>").appendTo(
				reply_con_info);
	}
	if (note.atUserId != ""&&note.atUserId != null) {
		$(
				"<span class='reply_re'>回复</span><span><a href='"+realPath+"/user/"
						+ note.atUserId
						+ "'>"
						+ note.atUserName
						+ "</a></span>").appendTo(reply_con_info);
	}

	$("<span class='reply_con_content'>" + note.content + "</span>").appendTo(reply_con_info);
	
	var reply_info = $("<div class='reply_info'></div>").appendTo(reply_con_p);
	
	if (note.sourceFrom == "A") {
		$(
				"<div class='reply_time'>" + note.postTime
						+ "(来自:android客户端)</div>").appendTo(reply_info);
	} else {
		$("<div class='reply_time'>" + note.postTime + "</div>")
				.appendTo(reply_info);
	}
	
	var reply_op = $("<div class='reply_op'></div>").appendTo(reply_info);
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		atUserId : note.userId,
		atUserName : note.userName,
	}, this.quote).appendTo(reply_op);
	if (userId == sessionUserId) {
		$("<a href='javascript:void(0)'>删除</a>").bind("click", {
			id : note.id,
			message : this.noteCon
		}, this.del).appendTo(reply_op);
	}
	$("<div class='clear'></div>").appendTo(reply_info);
	// 清除浮动
	$("<div class='clear'></div>").appendTo(this.noteCon);
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
		$("<div class='clear'></div>").appendTo(quote_panle);
	},
	del : function(event) {
		var id = event.data.id;
		var message = event.data.message;
		if(confirm("确定要删除此条评论吗？")){
			$.ajax({
				async : true,
				cache : false,
				type : 'GET',
				dataType : "json",
				url : realPath+"/user/deleteReplay.action?replyId=" + id,// 请求的action路径
				success : function(data) {
					if (data.result == "success") {
						message.remove();
					} else{
						alert(data.message);
					}
				}
			});
		}
	}
}

function delquote() {
	$("#quote_panle").remove();
}
