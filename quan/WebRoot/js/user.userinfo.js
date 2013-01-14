function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}

function submitForm() {
	var name = $("#name").val();
	var checkCode = $("#checkCode").val();
	var content = $("#content").val();
	var userId = $("#userId").val();
	if (name == "") {
		art.dialog.tips("用户名不能为空!");
		return;
	}
	if (content.trim() == "") {
		art.dialog.tips("留言不能为空!");
		return;
	}
	if (userId == "" && checkCode == "") {
		art.dialog.tips("验证码不能为空!");
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
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			reUserName : name,
			content : content,
			checkCode : checkCode,
			userId : userId,
			quote : quote,
			"time" : new Date()
		},
		url : "addMessage.jspx",// 请求的action路径
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
				$("#quote_panle").remove();
				$(".nomessage").remove();
				new NotePanle(data.note).asHtml().appendTo($("#messagelist"));
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

/**
 * 修改基本信息
 */
function saveBaseInfo() {
	$("#subBtn").hide();
	$("#loadImg").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#baseInfo").serialize(),
		url : "updateUserInfo.jspx",// 请求的action路径
		success : function(data) {
			var msg = "保存成功";
			if (data.result != "ok") {
				msg = "网络出现故障，请稍候再试";
			}
			$("#subBtn").show();
			$("#loadImg").hide();
			$("#resultInfo").html(msg);
			$("#resultInfo").show();
		}
	});
}

function repassword() {
	$("#subBtn").hide();
	$("#loadImg").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#subform").serialize(),
		url : "resetPassword.jspx",// 请求的action路径
		success : function(data) {
			var msg = "";
			if (data.result == "ok") {
				msg = "密码修改成功，点击<a href='login.jsp'>这里</a>重新登录";
			} else if (data.result == "pwdError") {
				msg = "密码错误";
			} else {
				msg = "网络出现故障，请稍候再试";
			}
			$("#subBtn").show();
			$("#loadImg").hide();
			$("#resultInfo").html(msg);
			$("#resultInfo").show();
		}
	});
}
this.userId = "";
function initMessage(userId) {
	this.userId = userId;
	loadMessage(1);
}

function loadMessage(i) {
	$("<img src='../images/load.gif'>").appendTo($("#messagelist"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "message.jspx?page=" + i + "&userId=" + this.userId,// 请求的action路径
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
						new Pager(data.totalPage, 10, data.page).asHtml()
								.appendTo($(".pagination"));
					}

				} else {
					$("<span class='nomessage'>尚无任何留言</span>").appendTo(
							"#messagelist");
				}

			} else {
				alert("请求路径不正确!");
				document.location.href = "../index.jspx";
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
						+ note.reUserId + "'>" + note.reUserName
						+ "</a></span>").appendTo(re_name_time);
	} else {
		$("<span class='note_name'>" + note.reUserName + "</span>").appendTo(
				re_name_time);
	}
	$(
			"<span class='note_time nofirst'>发表于"
					+ note.postTime.substring(0, 19) + "</span>").appendTo(
			re_name_time);
	// 回复
	var re_span = $("<span class='nofirst'></span>").appendTo(re_name_time);
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		name : note.reUserName,
		time : note.postTime.substring(0, 19),
		content : note.message
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
	$("<div class='re_content'>" + note.message + "</div>").appendTo(re_info);
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
	},
	del : function(event) {
		var id = event.data.id;
		var message = event.data.message;
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : "deleteMessage.jspx?id=" + id,// 请求的action路径
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

function Pager(totalPage, pageNum, page) {
	this.ulPanle = $("<ul style='float:none'></ul>");
	if (totalPage <= 1) {
		return;
	}
	// 起始页
	var beginNum = 0;
	// 结尾页
	var endNum = 0;

	if (pageNum > totalPage) {
		beginNum = 1;
		endNum = totalPage;
	}

	if (page - pageNum / 2 < 1) {
		beginNum = 1;
		endNum = pageNum;
	} else {
		beginNum = page - pageNum / 2 + 1;
		endNum = page + pageNum / 2;
	}

	if (totalPage - page < pageNum / 2) {
		beginNum = totalPage - pageNum + 1;
	}
	if (beginNum < 1) {
		beginNum = 1;
	}
	if (endNum > totalPage) {
		endNum = totalPage;
	}
	if (page > 1) {
		$(
				"<li><a href='javascript:loadMessage(1)' class='prePage'>第一页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a class='prePage' href='javascript:loadMessage("
						+ (page - 1) + ")'>上一页</a></li>")
				.appendTo(this.ulPanle);
	}
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadMessage(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a class='prePage' href='javascript:loadMessage("
						+ (page + 1) + ")'>下一页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a class='prePage' href='javascript:loadMessage("
						+ totalPage + ")'>最后页</a></li>").appendTo(this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}
