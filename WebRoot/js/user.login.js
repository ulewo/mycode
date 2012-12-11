//设置监听时间
$(function() {
	$("#userName").focusin(function() {
		var userName = $(this).val();
		if (userName == "用户名/邮箱") {
			$(this).val("");
		}
		$(this).css({
			"color" : "#494949"
		});
	});
	$("#userName").focusout(function() {
		$(this).css({
			"color" : "#494949"
		});
		if ($(this).val() == "") {
			$(this).val("用户名/邮箱");
			$(this).css({
				"color" : "#999999"
			});
		}

	});

	$("#logincheck").click(function() {
		var check = $("#autologin").attr("checked");
		if (check) {
			$("#autologin").attr("checked", false);
		} else {
			$("#autologin").attr("checked", true);
		}
	});
})

function login() {
	var userName = $("#userName").val();
	var passWord = $("#passWord").val();
	var checkCode = $("#checkCode").val();
	var isAutoLogin = $("#autologin").attr("checked");

	var autoLogin = "N";
	if (isAutoLogin) {
		autoLogin = "Y";
	}

	if (userName.trim() == "" || userName == "用户名/邮箱") {
		$("#loginerror").html("<img src='images/error.png'/>用户名不能为空");
		return;
	}
	if (passWord.trim() == "" || passWord == "请输入密码") {
		$("#loginerror").html("<img src='images/error.png'/>密码不能为空");
		return;
	}
	if (checkCode.trim() == "") {
		$("#loginerror").html("<img src='images/error.png'/>验证码不能为空");
		return;
	}
	$("#loginerror").html("");
	$("#subBtn").html("<img src='images/loading.gif' width='20'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"userName" : userName,
			"passWord" : passWord,
			"checkCode" : checkCode,
			"autoLogin" : autoLogin,
			"time" : new Date()
		},
		url : 'login',// 请求的action路径
		success : function(data) {
			refreshImage();
			if (data.result == "success") {// 登录成功
				alert("登录成功！");
				document.location.href = "day";
			} else {
				$("#loginerror").html(
						"<img src='images/error.png'/>" + data.msg);
				$("#subBtn").html(
						"<a href='javascript:login()' class='subtn'>登录</a>");
			}
		},
		error : function() {
			$("#subBtn").html(
					"<a href='javascript:login()' class='subtn'>登录</a>");
		}
	});

}

// 刷新验证码
function refreshImage() {
	$("#codeImage").attr("src", "common/image.jsp?rand =" + Math.random());
}
