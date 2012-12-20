//设置监听时间

function nextSetp() {
	var email = $("#email").val();
	var checkCode = $("#checkCode").val();

	if (email.trim() == "") {
		$("#loginerror").html("<img src='../images/error.png'/>用户名不能为空");
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
