/**
 * 添加吐槽
 */
function addTalk() {
	var content = $("#talkcontent").val();
	if (content.trim() == "" || content.trim() == "今天你吐槽了吗？") {
		alert("吐槽内容不能为空");
		return;
	}

	if (content.trim().length > 250) {
		alert("吐槽内容不能超过250字符");
		return;
	}
	$("#talkBtn").hide();
	$("#talkload").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"content" : content,
			"time" : new Date()
		},
		url : 'addTalk.jspx',// 请求的action路径
		success : function(data) {
			$("#talkBtn").show();
			$("#talkload").hide();
			if (data.msg == "success") {
				$("#talkcontent").val("今天你吐槽了吗？");
				$("#talkcontent").css({
					"color" : "#A9A9A9"
				});
				if ($("#talklist").children().length > 0) {
					$("#talklist").children().eq(0).before(
							new TalkItem(data.talk).item);
				} else {
					new TalkItem(data).item.appendTo($("#talklist"));
				}
			} else if (data.msg == "nologin") {
				alert("请先登录");
			} else if (data.msg == "contentError") {
				alert("内容不能为空");
			} else {
				alert("服务器异常，请稍候再试");
			}

		}
	});
}

function loadTalk() {
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'queryLatstTalk.jspx',// 请求的action路径
		success : function(data) {
			$("#talklist").empty();
			var list = data.list;
			for ( var i = 0, length = list.length; i < length; i++) {
				new TalkItem(list[i]).item.appendTo($("#talklist"));
			}
		}
	});
	setInterval("loadTalk()", 1000 * 60);
}

function TalkItem(data) {
	this.item = $("<div class='talkitem'><div>");
	$(
			"<div class='itemicon'><img src='" + myParam.realPath + "upload/"
					+ data.userIcon + "' width='37'></div>")
			.appendTo(this.item);
	var talkcon = $("<div class='itemcon'></div>").appendTo(this.item);
	$("<span class='item_user'><a href=''>" + data.userName + "</a></span>")
			.appendTo(talkcon);
	$("<span class='item_content'>：" + data.content + "</span>").appendTo(
			talkcon);
	$(
			"<span class='item_time'>" + data.createTime + "<a href=''>("
					+ data.reCount + "评)</a></span>").appendTo(talkcon);
	$("<div class='clear'></div>").appendTo(this.item);
}
