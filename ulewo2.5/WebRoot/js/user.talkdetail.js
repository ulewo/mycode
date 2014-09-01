var page = 1;
$(function() {
	var content = $("#item_content").html();
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
	loadBlastComment(1);
	$("#talkBtn").bind("click",addTalk);
	$("#loadmorebtn").click(function() {
		page++;
		loadBlastComment(page);
	});
});

function loadBlastComment(page) {
	$("#loading").show();
	$("#loadmorebtn").hide();
	$.ajax({
		async : true,
		cache : true,
		type : 'POST',
		dataType : "json",
		data:{
			blastId:blast.blastId,
			page:page
		},
		url : global.realPath+"/user/loadBlastComment",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.result=="200"){
				var list = data.data.list;
				var length = list.length;
				if(length>0){
					for ( var i = 0; i < length; i++) {
						new ReTalkItem(list[i]).item.appendTo($("#talklist"));
					}
					$("<div class='clear'></div>").appendTo($("#talklist"));
				}else{
					$("<div class='noinfo'>暂无评论，赶紧抢沙发!</div>").appendTo($("#talklist"));
				}
				if (data.data.page.pageTotal > page) {
					$("#loadmorebtn").css({
						"display" : "block"
					});
				} else {
					$("#loadmorebtn").hide();
				}
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
			"blastId" : blast.blastId,
			"content" : content,
			"atUserId" : $("#hide_atuserId").val(),
			"atUserName" : $("#hide_atuserName").val(),
			"time" : new Date()
		},
		url : global.realPath+'/user/saveBlastCommetn.action',// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"评论");
			if (data.result == "200") {
				tipsInfo("1分已到碗里");
				$(".noinfo").remove();
				$("#talkcontent").val("");
				if ($("#talklist").children().length > 0) {
					$("#talklist").children().eq(0).before(
							new ReTalkItem(data.comment).item);
				} else {
					new ReTalkItem(data.comment).item.appendTo($("#talklist"));
				}
				if ($("#atpanel")[0] != null) {
					$("#atpanel").hide();
					$("#hide_atuserId").val("");
					$("#hide_atuserName").val("");
				}
			} else {
				warm("show",data.msg);
			}
		}
	});
}
