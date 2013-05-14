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
	$("<span class='item_content'>：" + data.content + "</span>").appendTo(
			talkcon);
	var span = $(
			"<span class='item_time'>" + data.createTime + "<a href='"
					+ myParam.realPath + "user/talkDetail.jspx?userId="
					+ data.userId + "&talkId=" + data.id + "'>(" + data.reCount
					+ "评)</a></span>").appendTo(talkcon);
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

	$("<span class='item_content'>：" + data.content + "</span>").appendTo(
			talkcon);
	var item_time = $("<span class='item_time'></div>").appendTo(talkcon);
	$("<span class='item_time_t'>" + data.createTime + "</span>").appendTo(
			item_time);
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
					"<span id='u_atpanel_close'><img src='../images/delete.png'></span>")
					.bind("click", this.deleteAtPanel).appendTo(atpanel);
		}
		$("#hide_atuserId").val(atUserId);
		$("#hide_atuserName").val(atUserName);
	},
	deleteAtPanel : function() {
		if ($("#atpanel")[0] != null) {
			$("#atpanel").hide();
		}
	}

}
