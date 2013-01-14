function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}

function subReply(blogId) {
	var name = $("#name").val();
	var checkCode = $("#checkCode").val();
	var content = $("#content").val();
	if (name == "") {
		art.dialog.tips("用户名不能为空!");
		return;
	}
	if (content.trim() == "") {
		art.dialog.tips("留言不能为空!");
		return;
	}
	if (checkCode == "") {
		art.dialog.tips("验证码不能为空!");
		return;
	}
	if (content.trim().length > 500) {
		art.dialog.tips("留言内容不能超过500字符!");
		return;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			reUserName : name,
			content : content,
			checkCode : checkCode,
			blogId : blogId,
			"time" : new Date()
		},
		url : "savaReply.jspx",// 请求的action路径
		success : function(data) {
			if (data.msg == "noUserName") {// 登录成功
				art.dialog.tips("用户名不能为空!");
			} else if (data.msg == "noContent") {
				art.dialog.tips("留言不能为空!");
			} else if (data.msg == "checkCodeErr") {
				art.dialog.tips("验证码错误!");
			} else if (data.msg == "error") {
				art.dialog.tips("出现错误，稍后重试!");
			} else {
				art.dialog.tips("发表成功");
				$(".nomessage").remove();
				var reUserId = data.msg.userId;
				var userName = data.msg.userName;
				var content = data.msg.content;
				var posttime = data.msg.postTime;
				var main = $("<div class='main_message'></div>");
				if ($(".main_message").length > 0) {
					$(".main_message").eq($(".main_message").length - 1).after(
							main);
				} else {
					main.appendTo($("#messagelist"));
				}
				var nameHtml = userName;
				if (reUserId != "") {
					nameHtml = "<a href='userInfo.jspx?userId=" + reUserId
							+ "'>" + userName + "</a>";
				}
				var namecon = $(
						"<div><span class='message_name'>" + nameHtml
								+ "</span>&nbsp;&nbsp;&nbsp;&nbsp;发表于："
								+ posttime + "</div>").appendTo(main);
				var con = $("<div class='message_con'>" + content + "</div>")
						.appendTo(main);
				resetForm();
			}
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
	$("<img src='../images/load.gif'>").appendTo($("#messagelist"));
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
	// 头像
	$(
			"<div class='re_icon'><img src='../upload/" + reUserIcon
					+ "' width='35'></div>").appendTo(this.noteCon);
	// 留言信息
	var re_info = $("<div class='re_info'></div>").appendTo(this.noteCon);
	// 姓名，时间，回复
	var re_name_time = $("<div class='note_name_time'></div>")
			.appendTo(re_info);
	if (note.reUserId != "") {
		$(
				"<span class='note_name'><a href='userInfo.jspx?userId="
						+ note.userId + "'>" + note.userName + "</a></span>")
				.appendTo(re_name_time);
	} else {
		$("<span class='note_name'>" + note.userName + "</span>").appendTo(
				re_name_time);
	}
	$(
			"<span class='note_time nofirst'>发表于"
					+ note.postTime.substring(0, 19) + "</span>").appendTo(
			re_name_time);
	var re_span = $("<span class='nofirst'></span>").appendTo(re_name_time);
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		name : note.reUserName,
		time : note.postTime.substring(0, 19),
		content : note.message
	}, this.quote).appendTo(re_span);
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
		$("#quote_panle").remove();
		var name = event.data.name;
		var time = event.data.time;
		var content = event.data.content;
		var quote_panle = $("<div id='quote_panle' class='quote_panle'></div>");
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
	}
}

function delquote() {
	$("#quote_panle").remove();
}
