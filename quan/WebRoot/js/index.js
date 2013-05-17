$(function() {
	$("#talkBtn").click(function() {
		addTalk();
	});
	$("#talkcontent").focus(function() {
		if ($(this).val() == "今天你吐槽了吗？") {
			$(this).val("");
		}
		$(this).css({
			"color" : "#000000"
		});
	});
	$("#talkcontent").blur(function() {
		if ($(this).val() == "" || $(this).val() == "今天你吐槽了吗？") {
			$(this).val("今天你吐槽了吗？");
			$(this).css({
				"color" : "#A9A9A9"
			});
		}
	});

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
			if (curValue == "今天你吐槽了吗？") {
				$("#talkcontent").css({
					"color" : "#000000"
				});
				curValue = "";
			}
			$("#talkcontent").val(curValue + $(this).attr("title"));
			$("#pm_emotion_cnt").hide();
		});
	});

	loadTalk();

});

/**
 * 添加吐槽
 */
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
			"imgurl" : $("#imgUrl").val(),
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
	setInterval("loadTalk()", 1000 * 60 * 5);
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
	$("#talk_img_showimg>img").attr("src", "upload/" + imgurl);
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
