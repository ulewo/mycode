$(function() {
	$("#talkBtn").click(function() {
		addTalk();
	});
	loadReTalk();
});

function loadReTalk(page) {
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'queryReTalk.jspx?talkId=' + GlobalParam.talkId,// 请求的action路径
		success : function(data) {
			var list = data.list;
			for ( var i = 0, length = list.length; i < length; i++) {
				new ReTalkItem(list[i]).item.appendTo($("#talklist"));
			}
		}
	});
}

function addTalk() {
	if (myParam.user == "") {
		alert("请先登录后再吐槽");
		return;
	}
	var content = $("#talkcontent").val();
	if (content.trim() == "") {
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
			"reTalkId" : GlobalParam.talkId,
			"content" : content,
			"atUserId" : $("#hide_atuserId").val(),
			"atUserName" : $("#hide_atuserName").val(),
			"time" : new Date()
		},
		url : 'addReTalk.jspx',// 请求的action路径
		success : function(data) {
			$("#talkBtn").show();
			$("#talkload").hide();
			if (data.msg == "success") {
				$("#talkcontent").val("");
				if ($("#talklist").children().length > 0) {
					$("#talklist").children().eq(0).before(
							new ReTalkItem(data.retalk).item);
				} else {
					new ReTalkItem(data.retalk).item.appendTo($("#talklist"));
				}
				if ($("#atpanel")[0] != null) {
					$("#atpanel").hide();
					$("#hide_atuserId").val("");
					$("#hide_atuserName").val("");
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
