$(function() {
	var content = $("#item_content").text();
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	$("#item_content").html(content);

	$(document).click(function() {
		$('#pm_emotion_cnt').hide();
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

	loadReTalk();
	
	$("#talkBtn").bind("click",addTalk);
});

function loadReTalk(page) {
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/user/loadReTalk?talkId="+talkId,// 请求的action路径
		success : function(data) {
			var list = data.list.list;
			var length = list.length;
			if(length>0){
				for ( var i = 0; i < length; i++) {
					new ReTalkItem(list[i]).item.appendTo($("#talklist"));
				}
			}else{
				$("<div class='noinfo'>暂无评论，赶紧抢沙发!</div>").appendTo($("#talklist"));
			}
		}
	});
}

function addTalk() {
	if($(this).attr("disable")=="disable"){
		return;
	}
	if (global.userId == "") {
		warm("show","请先登陆再评论");
		return;
	}
	var content = $("#talkcontent").val();
	if (content.trim() == "") {
		warm("show","回复内容不能为空");
		return;
	}

	if (content.trim().length > 250) {
		warm("show","回复内容不能超过250字符");
		return;
	}
	warm("hide","");
	btnLoading($(this),"评论中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"talkId" : talkId,
			"content" : content,
			"atUserId" : $("#hide_atuserId").val(),
			"atUserName" : $("#hide_atuserName").val(),
			"time" : new Date()
		},
		url : global.realPath+'/user/saveReTalk.action',// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"评论");
			if (data.result == "success") {
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
			} else {
				warm("show",data.message);
			}
		}
	});
}
