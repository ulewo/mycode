$(function(){
	initReply(1);
});



function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}

function subReply(blogId) {
	var content = $("#content").val();
	if (content.trim() == "") {
		art.dialog.tips("留言不能为空!");
		return;
	}
	if (content.trim().length > 500) {
		art.dialog.tips("留言内容不能超过500字符!");
		return;
	}
	$("#sendBtn").hide();
	$("#loading").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			content : content,
			blogId : blogId,
			atUserName : $("#atUserName").val(),
			atUserId : $("#atUserId").val(),
			"time" : new Date()
		},
		url : "savaReply.jspx",// 请求的action路径
		success : function(data) {
			if (data.result == "fail") {
				art.dialog.tips(data.msg);
			} else {
				art.dialog.tips("发表成功");
				$(".nomessage").remove();
				$("#quote_panle").remove();
				$(".nomessage").remove();
				new NotePanle(data.note).asHtml().appendTo($("#messagelist"));
				resetForm();
			}
			$("#sendBtn").show();
			$("#loading").hide();
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
	$("<img src='../images/load.gif'>评论加载中......").appendTo($("#messagelist"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : realPath+"replayList?blogId=" + blogId,// 请求的action路径
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
					$("<span class='nomessage'>尚无任何留言</span>").appendTo(
							"#messagelist");
				}

			} else {
				alert("加载评论失败!");
				// document.location.href = "../index.jspx";
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
	$(
			"<div class='reply_usericon'><img src='../upload/" + reUserIcon
					+ "' width='35'></div>")
			.appendTo(this.noteCon);
	// 留言信息
	var reply_con_p = $("<div class='reply_con_p'></div>").appendTo(this.noteCon);
	// 姓名,回复内容
	var reply_con_info = $("<div class='reply_con_info'></div>")
			.appendTo(reply_con_p);
	if (note.userId != "") {
		$(
				"<span><a href='userInfo.jspx?userId="
						+ note.userId + "'>" + note.userName + "</a></span>")
				.appendTo(reply_con_info);
	} else {
		$("<span class='note_name'>" + note.userName + "</span>").appendTo(
				reply_con_info);
	}
	if (note.atUserId != ""&&note.atUserId != null) {
		$(
				"<span class='reply_re'>回复</span><span><a href='userInfo.jspx?userId="
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
		$("#reblogform").before(quote_panle);
		$("<a href='javasccript:void(0)'>@" + atUserName + "</a>").appendTo(
				quote_panle);
		$("<span><img src='../images/delete.png'></span>")
				.appendTo(quote_panle).bind("click", function() {
					$("#comment_form_at").remove();
					$("#atUserId").val("");
					$("#atUserName").val("");
				});
	},
	del : function(event) {
		var id = event.data.id;
		var message = event.data.message;
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : "deleteReply.jspx?id=" + id,// 请求的action路径
			success : function(data) {
				if (data.msg == "ok") {
					message.remove();
					art.dialog.tips("删除成功");
				} else if (data.msg == "noperm") {
					art.dialog.tips("你无权进行此操作");
				} else {
					art.dialog.tips("网络异常，请稍后再试");
				}
			}
		});

	}
}

function delquote() {
	$("#quote_panle").remove();
}
