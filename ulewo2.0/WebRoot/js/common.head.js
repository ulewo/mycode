// 登录跳转
function goto_login() {
	var redirectUrl = window.location.href;
	if (redirectUrl.indexOf("redirectUrl") == -1) {
		document.location.href =global.realPath+"/login?redirectUrl=" + redirectUrl;
	} else {
		document.location.href = window.location.href;
	}

}

function goto_register() {
	document.location.href = global.homePath+"/register";
}

function logout() {
	var redirectUrl = global.realPath;
	var url =  global.realPath + "/user/logout";
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"time" : new Date()
		},
		url : url,// 请求的action路径
		success : function(data) {
			if (data.msg == "success") {
				document.location.href = redirectUrl;
			}
		}
	});
}

function loadNotice() {
	var showUrl = global.realPath + "user/notice.jspx";
	var url = global.realPath + "user/checkNotice.jspx";
	if (myParam.user != "") {
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : url,// 请求的action路径
			success : function(data) {
				if (parseInt(data.noticeCount) > 0) {
					var left = $("#myspace").offset().left;
					$("#notice_panle").css({
						"left" : left - 110,
						"display" : "block"
					});
					$(".tip_con").html(
							"有" + data.noticeCount + "条@我，<a href='" + showUrl
									+ "'>点击查看</a>");
				}
			}
		});
	}
}
/**刷新验证码**/
function refreshImage() {
	$("#codeImage").attr("src", global.realPath+"/common/image.jsp?rand =" + Math.random());
}