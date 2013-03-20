function callBackReArticle(data, userId) {
	if (data.msg == "nologin") {
		alert("请首先登录");
		return;
	} else if (data.msg == "checkCodeErr") {
		alert("验证码错误");
		refreshcode2();
		return;
	} else if (data.msg == "contentError") {
		alert("输入内容为空，或者内容太长");
		return;
	}
	clearValue();
	var rePanel = new RePanel(data.reArticle);
	rePanel.asHtml().appendTo($("#recomment"));
}

function clearValue() {
	$("#reContent").val("");
	$("#checkCode").val("");
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

function refreshcode2() {
	$("#checkCodeImage2").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}

function loadReComment(id) {
	var rePanel;
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "queryReComment.jspx?id=" + id,// 请求的action路径
		success : function(data) {
			var list = data.list;
			var length = list.length;
			var childlist;
			var childLength;
			for ( var i = 0; i < length; i++) {
				var recomment = list[i];
				if (recomment.pid == 0) {
					rePanel = new RePanel(recomment);
					rePanel.asHtml().appendTo($("#recomment"));
				}
				childlist = list[i].childList;
				childLength = childlist.length;
				for ( var j = 0; j < childLength; j++) {
					new SubRePanel(childlist[j]).asHtml().appendTo(
							rePanel.reComent_Con);
				}
			}
		}
	});
}

function RePanel(data) {
	data.pid = data.id;
	this.outerHeight = $("<div class='outerHeight'></div>");
	// 头像
	var authorIcon = data.authorIcon || "defaultsmall.gif";
	this.ui_avatar = $("<div class='ui_avatar'><img src='../upload/"
			+ authorIcon + "' width='30'></div>");
	if (data.authorid != "") {
		this.ui_avatar = $("<div class='ui_avatar'><a href='../user/userInfo.jspx?userId="
				+ data.authorid
				+ "'><img src='../upload/"
				+ authorIcon
				+ "' width='30' border='0'></a></div>");
	}
	this.ui_avatar.appendTo(this.outerHeight);
	// 内容
	this.reComent_Con = $("<div class='reComent_Con'></div>").appendTo(
			this.outerHeight);
	$("<div class='clear'></div>").appendTo(this.outerHeight);
	this.comments_content = $("<div class='comments_content'></div>").appendTo(
			this.reComent_Con);
	$(
			"<a href=''>" + data.authorName
					+ "</a>:<span class='comment_content_word'>" + data.content
					+ "</span>").appendTo(this.comments_content);
	this.comments_op = $("<div class='comments_op'></div>").appendTo(
			this.comments_content);
	$("<span class='com_op_time'>" + data.reTime.substring(0, 16) + "</span>")
			.appendTo(this.comments_op);
	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op).bind("click", {
		data : data
	}, this.showReForm);
	$("<div class='coment_sub_panel'></div>").appendTo(this.comments_content);
}
RePanel.prototype = {
	asHtml : function() {
		return this.outerHeight;
	},
	showReForm : function(event) {
		$("#recoment_form_panel").remove();
		var data = event.data.data;
		var reComent_Con = $(this).parent().parent().parent();
		if (reComent_Con.children(".comtent_sub").length > 0) {
			reComent_Con.children(".comtent_sub").last().after(
					new Recoment_form_panel(data).recoment_form_panel);
		} else {
			reComent_Con
					.append(new Recoment_form_panel(data).recoment_form_panel);
		}
	}
}
function SubRePanel(data) {
	this.comment_sub = $("<div class='comtent_sub'></div>");
	this.ui_avatar = $(
			"<div class='ui_avatar'><img src='../upload/" + data.authorIcon
					+ "' width='30'></div>").appendTo(this.comment_sub);
	this.comments_content_sub = $("<div class='comments_content_sub'></div>")
			.appendTo(this.comment_sub);
	$("<div class='clear'></div>").appendTo(this.comment_sub);
	$(
			"<a href='../user/userInfo.jspx?userId="
					+ data.authorid
					+ "'>"
					+ data.authorName
					+ "</a>&nbsp;回复&nbsp;<a href='../user/userInfo.jspx?userId="
					+ data.atUserId + "'>" + data.atUserName
					+ "</a>:<span class='comment_content_word'>" + data.content
					+ "</span>").appendTo(this.comments_content_sub);
	this.comments_op_sub = $("<div class='comments_op_sub'></div>").appendTo(
			this.comments_content_sub);
	$("<span class='com_op_time'>" + data.reTime.substring(0, 16) + "</span>")
			.appendTo(this.comments_op_sub);
	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op_sub).bind("click", {
		data : data
	}, this.showReForm);
	;
}
SubRePanel.prototype = {
	asHtml : function() {
		return this.comment_sub;
	},
	showReForm : function(event) {
		$("#recoment_form_panel").remove();
		var data = event.data.data;
		$(this).parent().parent().parent().parent().children(".comtent_sub")
				.last()
				.after(new Recoment_form_panel(data).recoment_form_panel);
	}
}

function Recoment_form_panel(data) {
	this.recoment_form_panel = $("<div class='recoment_form_panel' id='recoment_form_panel'></div>");
	var comment_form_at = $("<div class='comment_form_at'></div>").appendTo(
			this.recoment_form_panel);
	$("<a href='javasccript:void(0)'>@" + data.authorName + "</a>").appendTo(
			comment_form_at);
	$("<span><img src='../images/delete.png'></span>")
			.appendTo(comment_form_at).bind("click", function() {
				$("#recoment_form_panel").remove();
			});
	this.textarea = $("<textarea></textarea>").appendTo(
			$("<div class='comment_form_textarea'></div>").appendTo(
					this.recoment_form_panel));
	this.checkCode_area = $("<div class='comment_form_panel'></div>").appendTo(
			this.recoment_form_panel);
	this.checkCode = $("<input type='text'>").appendTo(
			$("<div class='comment_checkcode'></div>").appendTo(
					this.checkCode_area));
	$(
			"<div class='comment_checkcode_img'><a href='JavaScript:refreshcode();' onfocus='this.blur();'><img id='checkCodeImage' src='../common/image.jsp' border='0' height='22'></a></div>")
			.appendTo(this.checkCode_area);
	$(
			"<div class='comment_checkcode_link'><a href='javascript:refreshcode()'>换一张</a></div>")
			.appendTo(this.checkCode_area);
	$(
			"<div class='comment_checkcode_rebtn'><a href='javascript:void(0)'>回复</a></div>")
			.bind("click", {
				data : data,
				reCotent : this.textarea,
				checkCode : this.checkCode
			}, this.subReComent).appendTo(this.checkCode_area);
	if (groupParam.userId == "") {
		var shade = $("<div class='shade' id='shade'></div>").appendTo(
				this.recoment_form_panel);
		var shadeLogin = $(
				"<div class='shadeLogin'>回复，请先 <a href='javascript:login()'>登录</a>&nbsp;&nbsp;<a href='javascript:register()'>注册</a></div>")
				.appendTo(shade);
		shade.css({
			"width" : "520px",
			"height" : "115px",
			"left" : "-5px",
			"top" : "23px"
		});
		shadeLogin.css({
			"marginTop" : "40px"
		});
	}
}
Recoment_form_panel.prototype = {
	subReComent : function(event) {
		var data = event.data.data;
		data.reCotent = event.data.reCotent.val();
		data.checkCode = event.data.checkCode.val();
		subSubReComment(data);
	}
}

function subSubReComment(data) {
	var pid = data.pid;
	var atUserId = data.authorid;
	var atUserName = data.authorName;
	var reCotent = data.reCotent;
	var checkCode = data.checkCode;
	var articleTit = $("#articleTit").val();
	var authorId = $("#authorId").val();
	if (reCotent == "") {
		alert("请填写回复内容");
		return;
	}
	if (reCotent.trim().length > 500) {
		alert("内容超过500字符，请重新输入");
		return;
	}
	if (checkCode == "") {
		alert("请填写验证码");
		return;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"articleid" : articleId,
			"reContent" : reCotent,
			"checkCode" : checkCode,
			"gid" : groupParam.gid,
			"atUserId" : atUserId,
			"atUserName" : atUserName,
			"pid" : pid
		},
		url : 'addSubReComment.jspx',// 请求的action路径
		success : function(data) {
			if (data.msg == "ok") {
				$("#recoment_form_panel").before(
						new SubRePanel(data.reArticle).asHtml());
				$("#recoment_form_panel").remove();
			} else if (data.msg == "checkCodeErr") {
				alert("验证码错误");
				refreshcode()();
			} else if (data.msg == "nologin") {
				alert("请登录后再发贴");
			} else if (data.msg == "contentError") {
				alert("输入内容为空，或者内容太长");
				return;
			}

		}
	});
}

function subReForm(userId, id, gid) {
	var recontent = $("#reContent").val();
	var checkCode = $("#checkCode").val();
	var authorId = $("#authorId").val();
	var articleTit = $("#articleTit").val();

	if (recontent.trim() == "") {
		alert("请填写回复内容");
		return;
	}
	if (checkCode == "") {
		alert("请填写验证码");
		return;
	}

	if (recontent.trim().length > 500) {
		alert("内容超过500字符，请重新输入");
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
			"reContent" : recontent,
			"checkCode" : checkCode,
			"gid" : gid,
			"authorId" : authorId,
			"title" : articleTit
		},
		url : 'addReArticle.jspx',// 请求的action路径
		success : function(data) {
			callBackReArticle(data, userId);
			$("#reCount").html(parseInt($("#reCount").html()) + 1);
			$("#subBtn").attr("disabled", false);
			$("#subBtn_con").children("img").remove();
		}
	});
}