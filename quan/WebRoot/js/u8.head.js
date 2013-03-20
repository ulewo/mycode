var _add = art.dialog.defaults;
_add["drag"] = true;
_add['lock'] = true;
_add['fixed'] = true;
_add['yesText'] = 'yes';
_add['noText'] = 'no';

var myParam = {};
function initParam(path, user) {
	myParam.realPath = path;
	myParam.user = user;
}

function showLoginDilog(url) {
	art.dialog.open(url, {
		title : '用户登录',
		width : 500,
		height : 300
	});
}

function showMenue() {
	$("#myspace").bind("mouseover", function() {
		var left = $("#myspace").offset().left;
		$(this).css({
			"background" : "#64C5E8",
			"color" : "#FFFFFF"
		});
		$("#other_info").css({
			"left" : left
		});
		$("#other_info").show();
	});

	$("#myspace").bind("mouseout", function() {
		$(this).css({
			"background" : "#FFFFFF",
			"color" : "#3E62A6"
		});
		setTimeout($("#other_info").hide(), 100);
	});

	$("#other_info").bind("mouseover", function() {
		$("#myspace").css({
			"background" : "#64C5E8",
			"color" : "#FFFFFF"
		});
		$(this).show();
	});

	$("#other_info").bind("mouseout", function() {
		$("#myspace").css({
			"background" : "#FFFFFF",
			"color" : "#3E62A6"
		});
		$(this).hide();
	});
}

// 搜索
function search() {
	var keyWord = $("#searchInput").val();
	keyWord = encodeURI(encodeURI(keyWord));
	document.location.href = myParam.realPath+"search.jspx?keyWord=" + keyWord;

}

// 登录
function login() {
	var redirectUrl = window.location.href;
	if (redirectUrl.indexOf("redirectUrl") == -1) {
		document.location.href = myParam.realPath
				+ "user/login.jsp?redirectUrl=" + redirectUrl;
	} else {
		document.location.href = window.location.href;
	}

}

function register() {
	document.location.href = myParam.realPath
				+ "user/register.jsp";
}

// 创建窝窝
function createWoWo() {
	if (myParam.user != "") {
		document.location.href = myParam.realPath + "group/creategroup.jsp";
	} else {
		document.location.href = myParam.realPath
				+ "user/login.jsp?redirectUrl=" + myParam.realPath
				+ "group/creategroup.jsp";
	}
}

// 退出
function logout() {
	var redirectUrl = myParam.realPath + "index.jspx";
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
// 加载通知
function loadNotice() {
	var showUrl = myParam.realPath + "user/notice.jspx";
	var url = myParam.realPath + "user/checkNotice.jspx";
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
