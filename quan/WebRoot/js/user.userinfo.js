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
				$(".nomessage").remove();
				var reUserId = data.msg.reUserId;
				var userName = data.msg.reUserName;
				var content = data.msg.message;
				var posttime = data.msg.postTime;
				var main = $("<div class='main_message'></div>");
				if ($(".main_message").length > 0) {
					$(".main_message").eq(0).before(main);
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
				resetForm(reUserId);
			}
			refreshcode();
		}
	});
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
				resetForm(reUserId);
			}
			refreshcode();
		}
	});
}

function resetForm(reUserId) {
	if (reUserId == "") {
		$("#name").val("");
	}

	$("#checkCode").val("");
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
