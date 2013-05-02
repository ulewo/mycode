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

this.blogId = "";
function loadReply(blogId) {
	this.blogId = blogId;
	initReply(1);
}

function initReply(i) {
	$("<img src='../images/load.gif'>评论加载中......").appendTo($("#messagelist"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "loadReply.jspx?blogId=" + this.blogId,// 请求的action路径
		success : function(data) {
			$("#messagelist").empty();
			$(".pagination").empty();
			if (data.msg == "ok") {
				if (data.list.length > 0) {
					for ( var i = 0; i < data.list.length; i++) {
						new NotePanle(data.list[i]).asHtml().appendTo(
								"#messagelist");
					}
					if (data.totalPage > 1) {
						$(".pagination").show();
					}

				} else {
					$("<span class='nomessage'>尚无任何留言</span>").appendTo(
							"#messagelist");
				}

			} else {
				alert("请求路径不正确!");
				// document.location.href = "../index.jspx";
			}
		}
	});
}

function NotePanle(note) {
	var reUserIcon = note.reUserIcon || "default.gif";
	// 最外层div
	this.noteCon = $("<div class='main_message'></div>");
	// @定位
	$("<a name='re" + note.id + "'></a>").appendTo(this.noteCon);
	// 头像
	$(
			"<div class='re_icon'><img src='../upload/" + reUserIcon
					+ "' width='35' style='border:1px solid #9B9B9B'></div>")
			.appendTo(this.noteCon);
	// 留言信息
	var re_info = $("<div class='re_info'></div>").appendTo(this.noteCon);
	// 姓名，时间，回复
	var re_name_time = $("<div class='note_name_time'></div>")
			.appendTo(re_info);
	if (note.userId != "") {
		$(
				"<span class='note_name'><a href='userInfo.jspx?userId="
						+ note.userId + "'>" + note.userName + "</a></span>")
				.appendTo(re_name_time);
	} else {
		$("<span class='note_name'>" + note.userName + "</span>").appendTo(
				re_name_time);
	}

	if (note.atUserId != "") {
		$(
				"<span style='color:#008000'>&nbsp;回复：</span><span class='note_name'><a href='userInfo.jspx?userId="
						+ note.atUserId
						+ "'>"
						+ note.atUserName
						+ "</a></span>").appendTo(re_name_time);
	}

	if (note.sourceFrom == "A") {
		$(
				"<span class='note_time nofirst'>发表于:" + note.postTime
						+ "(来自:android客户端)</span>").appendTo(re_name_time);
	} else {
		$("<span class='note_time nofirst'>发表于" + note.postTime + "</span>")
				.appendTo(re_name_time);
	}

	var re_span = $("<span class='nofirst'></span>").appendTo(re_name_time);
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		atUserId : note.userId,
		atUserName : note.userName,
	}, this.quote).appendTo(re_span);
	if (userId == sessionUserId) {
		var del_span = $("<span class='nofirst'></span>")
				.appendTo(re_name_time);
		$("<a href='javascript:void(0)'>删除</a>").bind("click", {
			id : note.id,
			message : this.noteCon
		}, this.del).appendTo(del_span);
	}
	// 回复的内容
	$("<div class='re_content'>" + note.content + "</div>").appendTo(re_info);
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
