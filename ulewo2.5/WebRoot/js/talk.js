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
			if(curValue=="今天你吐槽了吗？"){
				curValue="";
			}
			$("#talkcontent").val(curValue + $(this).attr("title"));
			$("#pm_emotion_cnt").hide();
		});
	});
	$(".talkitem").live("mouseover",function(){
		$(this).css("background-color","#f2f6fc");
		$(this).find(".ok_like_small").css("display","inline-block");
	});
	$(".talkitem").live("mouseout",function(){
		$(this).css("background-color","#FFFFFF");
		$(this).find(".ok_like_small").hide();
	})
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
	
function TalkItem(data,type) {
	this.item = $("<div class='talkitem'></div>");
	$(
			"<div class='itemicon'><img src='" + global.realPath + "/upload/"
					+ data.userIcon + "' width='37'></div>")
			.appendTo(this.item);
	var talkcon = $("<div class='itemcon'></div>").appendTo(this.item);
	$(
			"<span class='item_user'><a href='" + global.realPath + "/user/"
					+ data.userId + "'>" + data.userName + "</a></span>")
			.appendTo(talkcon);
	var content = data.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$("<span class='item_content'>：" + content + "</span>").appendTo(talkcon);
	var span = $(
			"<span class='item_time'><span class='item_time_time'>" + data.showCreateTime + "</span><a href='"
					+ global.realPath + "/user/" + data.userId + "/blast/"
					+ data.blastId + "' class='item_time_s'>(" + data.commentCount + "评)</a></span>")
			.appendTo(talkcon);
	if(data.smallImageUrl != null&&data.smallImageUrl != ""&&type=="list"){
		var smallImageUrl = global.realPath+"/upload/"+data.smallImageUrl;
		var realImageUrl = global.realPath+"/upload/"+data.imageUrl;
		$("<div style='margin-bottom:5px;padding:5px;border:1px solid #9A9A9A;display:inline-block;'><a href='"+realImageUrl+"' target='_blank'><img src='"+smallImageUrl+"' style='max-width:100px'></a></div>").appendTo(talkcon);
	}
	
	if (data.sourceFrom == "A") {
		$("<span class='item_time_s'>&nbsp;Android&nbsp;</span>").appendTo(span);
	}
	if (data.imageUrl != ""&&data.imageUrl!=null) {
		$(
				"<a class='item_time_s' href='" + global.realPath + "/user/" + data.userId
						+ "/blast/" + data.blastId + "'><img src='" + global.realPath
						+ "/images/img.gif' border=0></a>").appendTo(span);
	}
	$('<a href="javascript:void(0)" opid="'+data.blastId+'" type="L" disable = "false"  class="op_like ok_like_small item_time_s" style="display:none;" title="赞"></a>').appendTo(span)
	$("<div class='clear'></div>").appendTo(this.item);
}

function ReTalkItem(data) {
	this.item = $("<div class='talkitem'></div>");
	$(
			"<div class='itemicon'><img src='" + global.realPath + "/upload/"
					+ data.userIcon + "' width='37'></div>")
			.appendTo(this.item);
	var talkcon = $("<div class='itemcon'></div>").appendTo(this.item);
	$(
			"<span class='item_user'><a href='" + global.realPath
					+ "/user/" + data.userId + "'>"
					+ data.userName + "</a></span>").appendTo(talkcon);
	if (data.atUserName != "" && data.atUserName != null) {
		$(
				"<span style='color:#008000'>&nbsp;回复&nbsp;</span><span class='item_user'><a href='"
						+ global.realPath
						+ "/user/"
						+ data.atUserId
						+ "'>"
						+ data.atUserName
						+ "</a></span>").appendTo(talkcon);
	}
	var content = data.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}

	$("<span class='item_content'>：" + content + "</span>").appendTo(talkcon);
	var item_time = $("<span class='item_time'></div>").appendTo(talkcon);
	$("<span class='item_time_t'>" + data.showCreateTime + "</span>").appendTo(
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
					"<span id='u_atpanel_close' style='cursor:pointer'><img src='"
							+ global.realPath + "/images/delete.png'></span>")
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
