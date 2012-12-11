//设置监听时间
$(function() {
	$(".long_input").focusin(function() {
		$(this).parent().css({
			"border-color" : "#93B9DE",
			"border-width" : "2px"
		});
		// 父节点
		var parentCon = $(this).parent().parent();
		// 删除警告信息
		$(parentCon).children(".promptInfo").remove();
		// 获取输入框提示信息
		var html = showPrompt($(this).attr("name"));
		// 将提示信息添加到父节点中
		parentCon.append(html);
	});
	$(".long_input").focusout(function() {
		$(this).parent().css({
			"border-color" : "#B7CEE3",
			"border-width" : "1px"
		});
		var parentCon = $(this).parent().parent();
		// 将提示信息删除掉
		parentCon.children(".promptInfo").remove();
		// 检测输入信息合法性
		checkInfor($(this), "check");
	});
})

// 获取提示信息
function showPrompt(name) {
	var info = "";
	var hrml = "";
	if (name == "userName") {
		info = "用户名长度1-20位字符，由中文、_、数字、字母组成";
	} else if (name == "email") {
		info = "用于取回密码，请填写正确的常用邮箱";
	} else if (name == "passWord") {
		info = "密码长度6-16位字符，由数字，字母组成";
	} else if (name == "rePassWord") {
		info = "请再次输入您的密码";
	} else if (name == "checkCode") {
		info = "请输入图片中的字符";
	}
	return hrml = "<div class='promptInfo'>" + info + "</div>"
}

// 验证信息
function checkInfor(obj, type) {
	// 父节点
	var parentCon = obj.parent().parent();
	// 获取输入框的名称，用来判断是那个输入框
	var name = obj.attr("name");
	var userName = $(".long_input").eq(0).val();
	var email = $(".long_input").eq(1).val();
	var passWord = $(".long_input").eq(2).val();
	var rePassWord = $(".long_input").eq(3).val();
	var checkCode = $(".long_input").eq(4).val();

	var checkEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;// 验证email
	var checkUserName = /^[\w\u4e00-\u9fa5]+$/; // 只能是数字，字母，下划线，中文。
	var checkPassWord = /^[0-9a-zA-Z]+$/; // 只能是数字，字母

	// 初始化信息
	obj.parent().css({
		"border-color" : "#93B9DE",
		"border-width" : "1px"
	});
	$(parentCon).children(".promptInfo").remove();
	var flag = "Y";
	if (name == "userName") {
		if (userName.trim() == "" || userName.realLength() < 1
				|| userName.realLength() > 20
				|| !checkUserName.test(userName.trim())) {
			obj.parent().css({
				"border-color" : "#FE2C2C",
				"border-width" : "2px"
			});
			parentCon
					.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>请输入合法的用户名</div></div>");
			flag = "N";
		} else {
			if (type == "check") {
				parentCon
						.append("<div class='promptInfo'><img src='images/loading.gif' width='20'></div>");
				$
						.ajax({
							async : true,
							cache : false,
							type : 'POST',
							dataType : "json",
							data : {
								"userName" : userName,
								"time" : new Date()
							},
							url : 'login?type=check',// 请求的action路径
							success : function(data) {
								parentCon.children(".promptInfo").remove();
								$("#ajaxReturn").val(data.result);
								if (data.result == "Y") {
									parentCon
											.append("<div class='promptInfo'><div class='iconimg'><img src='images/ok.png'></div><div class='okInfo'>恭喜，用户名可以使用</div></div>");
								} else {
									parentCon
											.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>抱歉，用户名已经被占用</div></div>");
								}
							}
						});
				flag = $("#ajaxReturn").val();
			}
		}
	} else if (name == "email") {
		if (email.trim() == "" || !checkEmail.test(email.trim())) {
			obj.parent().css({
				"border-color" : "red",
				"border-width" : "2px"
			});
			parentCon
					.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>请输入合法的邮箱</div></div>");
			flag = "N";
		}
	} else if (name == "passWord") {
		if (passWord.trim() == "" || passWord.length < 6
				|| passWord.length > 16 || !checkPassWord.test(passWord.trim())) {
			obj.parent().css({
				"border-color" : "red",
				"border-width" : "2px"
			});
			parentCon
					.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>请输入合法的密码</div></div>");
			flag = "N";
		}
	} else if (name == "rePassWord") {
		if (rePassWord.trim() == "" || rePassWord.trim() != passWord.trim()) {
			obj.parent().css({
				"border-color" : "red",
				"border-width" : "2px"
			});
			parentCon
					.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>两次输入的密码不一直</div></div>");
			flag = "N";
		}
	} else if (name == "checkCode") {
		if (checkCode.trim() == "") {
			obj.parent().css({
				"border-color" : "red",
				"border-width" : "2px"
			});
			parentCon
					.append("<div class='promptInfo'><div class='iconimg'><img src='images/error.png'></div><div class='errorInfo'>请输入图片中的字符</div></div>");
			flag = "N";
		}
	}
	return flag;
}

// 注册
function register() {
	var userName = $(".long_input").eq(0).val();
	var email = $(".long_input").eq(1).val();
	var passWord = $(".long_input").eq(2).val();
	var checkCode = $(".long_input").eq(4).val();
	var check0 = checkInfor($(".long_input").eq(0), "submit");
	var check1 = checkInfor($(".long_input").eq(1), "submit");
	var check2 = checkInfor($(".long_input").eq(2), "submit");
	var check3 = checkInfor($(".long_input").eq(3), "submit");
	var check4 = checkInfor($(".long_input").eq(4), "submit");
	if (check0 == "Y" && check1 == "Y" && check2 == "Y" && check3 == "Y"
			&& check4 == "Y") {// 检测都通过
		$("#subBtn").html("<img src='images/loading.gif' width='20'>");
		$("#subform").submit();
	}
}

// 刷新验证码
function refreshImage() {
	$("#codeImage").attr("src", "common/image.jsp?rand =" + Math.random());
}
