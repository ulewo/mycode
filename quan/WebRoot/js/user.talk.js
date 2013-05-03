$(function() {
	loadUserTalk();
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
			var length = 0;
			if (list.lenth <= 5) {
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
