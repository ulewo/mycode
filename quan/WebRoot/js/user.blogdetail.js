function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}

function subReply(blogId) {
	var checkCode = $("#checkCode").val();
	var content = $("#content").val();
	if (content.trim() == "") {
		art.dialog.tips("留言不能为空!");
		return;
	}
	if (content.trim().length > 500) {
		art.dialog.tips("留言内容不能超过500字符!");
		return;
	}
	var quote = ""
	if ($("#quote_panle").html() != null) {
		quote = "<div class='quote_panle'>" + $("#infocon").html() + "</div>";
	}
	if (quote != "") {
		blogauthor = $("#quoteUserId").val();
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
			quote : quote,
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
			refreshcode();
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
		url : "loadReply.jspx?&blogId=" + this.blogId,// 请求的action路径
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
					+ "' width='35'></div>").appendTo(this.noteCon);
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
		id : note.id,
		quoteUserId : note.userId,
		name : note.userName,
		time : note.postTime.substring(0, 19),
		content : note.content
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
		$("#quote_panle").empty();
		var name = event.data.name;
		var time = event.data.time;
		var content = event.data.content;
		var id = event.data.id;
		var quoteUserId = event.data.quoteUserId;
		var quote_panle = $("<div id='quote_panle' class='quote_panle' style='margin-top:10px;'></div>");
		$("#subform").before(quote_panle);
		$(
				"<a href='javascript:delquote()'><img src='../images/001_02.png' width='18' border=0 class='close_icon'></a>")
				.appendTo(quote_panle);
		var infocon = $("<div id='infocon'></div>").appendTo(quote_panle);
		$("<img src='../images/qbar_iconb24.gif' border=0 />")
				.appendTo(infocon);
		var b = $("<b></b>").appendTo(infocon);
		$("<span>&nbsp;回复</span>").appendTo(b);
		$("<span style='color:#46B'>&nbsp;" + name + "&nbsp;</span>").appendTo(
				b);
		$("<span >在<span style='color:#666'>" + time + "</span>的发表</span>")
				.appendTo(b);
		$("<div style='margin-top:5px;'>" + content + "</div>").appendTo(
				infocon);
		$("<input type='hidden' id='quoteId' value=" + id + ">").appendTo(
				$("#quote_panle"));
		$("<input type='hidden' id='quoteUserId' value=" + quoteUserId + ">")
				.appendTo($("#quote_panle"));
		$("#content").focus();
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
