//设置监听时间

function repassword() {
	var passWord = $("#passWord").val();
	var rePassWord = $("#rePassWord").val();
	var checkCode = $("#checkCode").val();

	if (passWord.trim() == "") {
		$("#loginerror").html("<img src='../images/error.png'/>新密码不能为空");
		return;
	}
	if (rePassWord.trim() == "") {
		$("#loginerror").html("<img src='../images/error.png'/>重复密码不能为空");
		return;
	}
	if (passWord.trim() != rePassWord.trim()) {
		$("#loginerror").html("<img src='../images/error.png'/>两次密码不一致");
		return;
	}
	if (checkCode.trim() == "") {
		$("#loginerror").html("<img src='../images/error.png'/>验证码不能为空");
		return;
	}
	$("#loginerror").html("");
	$("#subBtn").html("<img src='../images/loading.gif' width='20'>");
	$("#subform").submit();
}

// 刷新验证码
function refreshImage() {
	$("#codeImage").attr("src", "../common/image.jsp?rand =" + Math.random());
}
