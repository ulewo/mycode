var page = 1;
$(function() {
	loadMoreTalk(page);
	$("#loadmorebtn").click(function() {
		page++;
		loadMoreTalk(page);
	});
});

function loadMoreTalk(page) {
	$("#loading").show();
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'queryUserTalk.jspx?userId=' + gloableParam.userId,// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			var list = data.list;
			var length = list.length;
			for ( var i = 0; i < length; i++) {
				new TalkItem(list[i]).item.appendTo($("#talklist"));
			}
			if (data.pageTotal > page) {
				$("#loadmorebtn").css({
					"display" : "block"
				});
			} else {
				$("#loadmorebtn").hide();
			}
			page = data.page;
		}
	});
}
