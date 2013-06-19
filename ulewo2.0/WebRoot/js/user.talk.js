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
	$("#loadmorebtn").hide();
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + '/user/loadTalk?userId=' + userId,// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			var list = data.list.list;
			var length = list.length;
			for ( var i = 0; i < length; i++) {
				new TalkItem(list[i]).item.appendTo($("#talklist"));
			}
			if (data.list.pageTotal > page) {
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
