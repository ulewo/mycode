function subReForm(userId, id, gid) {
	var reUserName = $("#reUserName").val();
	var recontent = $("#reContent").val();
	var checkCode = $("#checkCode").val();
	if (userId == "" && reUserName.trim() == "") {
		art.dialog.tips("请填写用户名");
		return;
	}

	if (recontent.trim() == "") {
		art.dialog.tips("请填写回复内容");
		return;
	}
	if (userId == "" && checkCode == "") {
		art.dialog.tips("请填写验证码");
		return;
	}

	if (recontent.trim().length > 500) {
		art.dialog.tips("内容超过500字符，请重新输入");
		return;
	}
	$("#subBtn").attr("disabled", true);
	$("<img src='../images/loading.gif' width='20'>")
			.appendTo($("#subBtn_con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"articleid" : id,
			"reUserName" : reUserName,
			"reContent" : recontent,
			"checkCode" : checkCode,
			"gid" : gid
		},
		url : 'addReArticle.jspx',// 请求的action路径
		success : function(data) {
			callBackReArticle(data);
			$("#reCount").html(parseInt($("#reCount").html()) + 1);
			clearValue();
			$("#subBtn").attr("disabled", false);
			$("#subBtn_con").children("img").remove();
		}
	});
}

function callBackReArticle(data) {
	if (data.reArticle == "checkCodeErr") {
		art.dialog.tips("验证码错误");
		refreshcode();
		return;
	}
	var articleId = data.reArticle.id;
	var reUserName = data.reArticle.authorName;
	var authorid = data.reArticle.authorid;
	var reContent = data.reArticle.content;
	var reTime = data.reArticle.reTime.substring(0, 16);
	var user = data.reArticle.author;
	var reUserImg = "";
	if (user != null) {
		reUserImg = data.reArticle.author.userLittleIcon;
		reUserName = data.reArticle.author.userName;
	}
	var recon_con = $("<div class='recon_con'><div>").appendTo($("#reCon"));
	if (authorid == "") {
		$(
				"<div class='recon_img'><img src='../upload/default.gif' width='50'><div>")
				.appendTo(recon_con);
	} else {
		$(
				"<div class='recon_img'><a href='../user/userInfo.jspx?userId="
						+ authorid + "'><img src='../upload/" + reUserImg
						+ "' width='50' border='0'></a><div>").appendTo(
				recon_con);
	}
	var recon_info = $("<div class='recon_info'></div>").appendTo(recon_con);
	$("<div class='clear'></div>").appendTo(recon_con);
	var recon_info_re = $("<div class='recon_info_re'></div>").appendTo(
			recon_info);
	$("<div class='recon_info_con'>" + reContent + "</div>").appendTo(
			recon_info);
	if (authorid != "") {
		reUserName = "<a href='../user/userInfo.jspx?userId=" + authorid + "'>"
				+ reUserName + "</a>";
	}
	var recon_info_info = $(
			"<div class='recon_info_info'>" + "<span class='info_lou'>"
					+ ($(".recon_con").length) + "楼</span>"
					+ "<span class='info_name'>" + reUserName + "</span>"
					+ "<span class='info_time'>发表时间：" + reTime + "</span>"
					+ "</div>").appendTo(recon_info_re);
	var recon_info_info_op = $(
			"<div class='recon_info_info_op'>"
					+ "<span><a href='javascript:void(0)' onclick='quote("
					+ data.reArticle.id
					+ ")'>引用</a></span><span class='re_op_d'>" +
					// "<a href='####'>删除</a>" +
					"</span>" + "</div>").appendTo(recon_info_re);

}

function clearValue() {
	$("#reUserName").val("");
	$("#reContent").val("");
	$("#checkCode").val("");
}

function reArticle(id) {
	document.location.href = "reArticle.jspx?id=" + id;
}

function quote(id) {
	document.location.href = "quote.jspx?id=" + id;
}

function showAboutArticle(keyWord, gid) {
	$("<img src='../images/loading.gif' id='loadImg'>").appendTo(
			$("#about_con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"keyWord" : keyWord,
			"gid" : gid
		},
		url : 'aboutArticles.jspx',// 请求的action路径
		success : function(data) {
			$(".about_con").children("#loadImg").remove();
			var articles = data.articleList;
			if (articles.length > 0) {
				for ( var i = 0; i < articles.length; i++) {
					if ($("#title_con").html() != articles[i].title) {
						$(
								"<div class='about_con_info'><a href='post.jspx?id="
										+ articles[i].id + "'>"
										+ articles[i].title + "</a></div>")
								.appendTo($("#about_con"));
					}
				}
			} else {
				$("<div class='about_con_info'>没有找到相关记录</div>").appendTo(
						$("#about_con"));
			}

		}
	});
}

function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}
