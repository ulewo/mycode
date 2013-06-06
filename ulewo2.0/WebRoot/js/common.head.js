// 登录
function login() {
	var redirectUrl = window.location.href;
	if (redirectUrl.indexOf("redirectUrl") == -1) {
		document.location.href ="http://ulewo.com/login?redirectUrl=" + redirectUrl;
	} else {
		document.location.href = window.location.href;
	}

}

function register() {
	document.location.href = "http://ulewo.com/register";
}

function logout() {
	var redirectUrl = global.realPath + "index.jspx";
	var url = myParam.realPath + "user/logout.jspx";
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
			if (data.msg == "ok") {// 登录成功
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
