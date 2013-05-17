$(function() {
	$("#talkBtn").click(function() {
		addTalk();
	});
	loadUserTalk();

	$(document).click(function() {
		$('#talk_img_con').hide();
		$('#pm_emotion_cnt').hide();
	});
	$("#talk_img_con").click(function(event) {
		event.stopPropagation();
	});
	$("#pm_emotion_cnt").click(function(event) {
		event.stopPropagation();
	});

	$(".pm_emotions_bd").find("a").each(function(index) {
		$(this).bind("click", function() {
			var curValue = $("#talkcontent").val();
			$("#talkcontent").val(curValue + $(this).attr("title"));
			$("#pm_emotion_cnt").hide();
		});
	});
});

function loadUserTalk(page) {
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'queryUserTalk.jspx?userId=' + gloableParam.userId,// 请求的action路径
		success : function(data) {
			var list = data.list;
			if (list == "") {
				$("#talklist").html("木有任何吐槽");
				return;
			}
			var length = 0;
			if (list.length <= 5) {
				length = list.length;
			} else {
				length = 5;
			}
			for ( var i = 0; i < length; i++) {
				new TalkItem(list[i]).item.appendTo($("#talklist"));
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
			"imgurl" : $("#imgUrl").val(),
			"time" : new Date()
		},
		url : 'addTalk.jspx',// 请求的action路径
		success : function(data) {
			$("#talkBtn").show();
			$("#talkload").hide();
			if (data.msg == "success") {
				$("#talkcontent").val("");
				$("#talkcontent").css({
					"color" : "#A9A9A9"
				});
				if ($("#talklist").children().length > 0) {
					$("#talklist").children().eq(0).before(
							new TalkItem(data.talk).item);
				} else {
					$("#talklist").empty();
					new TalkItem(data.talk).item.appendTo($("#talklist"));
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

/** *********吐槽图片上传************** */
function showUploader() {
	$("#talk_img_con").show();
}
function closeUploader() {
	$("#talk_img_con").hide();
}
/** 上传完成* */
function showImg(imgurl) {
	$("#imgUrl").val(imgurl);
	$("#talk_img_fram").hide();
	$("#talk_img_showimg>img").attr("src", "../upload/" + imgurl);
	$("#talk_img_showimg").show();
}

function deleteImg() {
	$("#imgUrl").val("");
	$("#talk_img_fram").show();
	$("#talk_img_showimg>img").attr("src", "");
	$("#talk_img_showimg").hide();
}

/** *************插入表情*************** */
function showEmotion() {
	$("#pm_emotion_cnt").show();
}
