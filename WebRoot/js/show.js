function showInitRe(id, obj) {
	// 通过当前链接获取到评论区域的dom对象
	var replayCon = $(obj).parent().parent().parent().children().last();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data : {
			articleid : id
		},
		dataType : "json",
		url : 'reArticleList',// 请求的action路径
		success : function(data) {
			$(replayCon).children(".replaylist").html("");
			if (data.result == "success") {
				for ( var i = 0; i < data.list.length; i++) {
					new ReArticle(data.list[i], i + 1).repanel.appendTo($(
							replayCon).children(".replaylist"));
				}
			} else {
				alert("系统异常，请稍后再试");
			}
		}
	});
}

function shareArticle(path, title, id, imgurl) {
	link = path + "detail?id=" + id;
	imgurl = path + "upload/big/" + imgurl;
	$("#sharecon").children("a").eq(0).bind("click", function() {
		shareQzone(title, link, title, title, imgurl)
	});
	$("#sharecon").children("a").eq(1).bind("click", function() {
		shareToWb(title, link, title, imgurl);
	});
	$("#sharecon").children("a").eq(2).bind("click", function() {
		shareTSina(title, link, title, imgurl);
	});
	$("#sharecon").children("a").eq(3).bind("click", function() {
		shareRR(title, link, title, imgurl);
	});
}
