$(function() {
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
})

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
	$("#talk_img_showimg>img").attr("src", global.realPath+"/upload/" + imgurl);
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
	
function TalkItem(data) {
	this.item = $("<div class='talkitem'></div>");
	$(
			"<div class='itemicon'><img src='" + myParam.realPath + "upload/"
					+ data.userIcon + "' width='37'></div>")
			.appendTo(this.item);
	var talkcon = $("<div class='itemcon'></div>").appendTo(this.item);
	$(
			"<span class='item_user'><a href='" + myParam.realPath
					+ "user/userInfo.jspx?userId=" + data.userId + "'>"
					+ data.userName + "</a></span>").appendTo(talkcon);
	var content = data.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + myParam.realPath
				+ "images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$("<span class='item_content'>：" + content + "</span>").appendTo(talkcon);
	var span = $(
			"<span class='item_time'>" + data.createTime + "<a href='"
					+ myParam.realPath + "user/talkDetail.jspx?userId="
					+ data.userId + "&talkId=" + data.id + "'>(" + data.reCount
					+ "评)</a></span>").appendTo(talkcon);
	if (data.sourceFrom == "A") {
		$("<span>&nbsp;Android&nbsp;</span>").appendTo(span);
	}
	if (data.imgurl != "") {
		$(
				"<a href='" + myParam.realPath + "user/talkDetail.jspx?userId="
						+ data.userId + "&talkId=" + data.id + "'><img src='"
						+ myParam.realPath + "images/img.gif' border=0></a>")
				.appendTo(span);
	}
	$("<div class='clear'></div>").appendTo(this.item);
}

function ReTalkItem(data) {
	this.item = $("<div class='talkitem'></div>");
	$(
			"<div class='itemicon'><img src='" + myParam.realPath + "upload/"
					+ data.userIcon + "' width='37'></div>")
			.appendTo(this.item);
	var talkcon = $("<div class='itemcon'></div>").appendTo(this.item);
	$(
			"<span class='item_user'><a href='" + myParam.realPath
					+ "user/userInfo.jspx?userId=" + data.userId + "'>"
					+ data.userName + "</a></span>").appendTo(talkcon);
	if (data.atUserId != "" && data.atUserId != null) {
		$(
				"<span style='color:#008000'>&nbsp;回复&nbsp;</span><span class='item_user'><a href='"
						+ myParam.realPath
						+ "user/userInfo.jspx?userId="
						+ data.atUserId
						+ "'>"
						+ data.atUserName
						+ "</a></span>").appendTo(talkcon);
	}
	var content = data.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + myParam.realPath
				+ "images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}

	$("<span class='item_content'>：" + content + "</span>").appendTo(talkcon);
	var item_time = $("<span class='item_time'></div>").appendTo(talkcon);
	$("<span class='item_time_t'>" + data.createTime + "</span>").appendTo(
			item_time);
	if (data.sourceFrom == "A") {
		$("<span class='item_time_t'>&nbsp;Android&nbsp;</span>").appendTo(
				item_time);
	}
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		atUserId : data.userId,
		atUserName : data.userName
	}, this.reTalk).appendTo(
			$("<span class='item_time_r'></span>").appendTo(item_time));
	$("<div class='clear'></div>").appendTo(this.item);
}
ReTalkItem.prototype = {
	reTalk : function(event) {
		var data = event.data;
		var atUserId = data.atUserId;
		var atUserName = data.atUserName;
		if ($("#atpanel")[0] != null) {
			$("#u_atpanel_userid").text(atUserName);
			$("#atpanel").css({
				"display" : "block"
			});

		} else {
			var atpanel = $("<div id='atpanel'></div>");
			$("#u_talk_textarea_con").before(atpanel);
			$("<span id='u_atpanel_userid'>" + atUserName + "</span>")
					.appendTo(atpanel);
			$(
					"<span id='u_atpanel_close' style='cursor:pointer'><img src='../images/delete.png'></span>")
					.bind("click", function() {
						if ($("#atpanel")[0] != null) {
							$("#atpanel").hide();
							$("#hide_atuserId").val("");
							$("#hide_atuserName").val("");
						}
					}).appendTo(atpanel);
		}
		$("#hide_atuserId").val(atUserId);
		$("#hide_atuserName").val(atUserName);
	}
}
