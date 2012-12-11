function loadArticle(page) {
	$("#con").html("");
	$(".pageArea").html("");
	$(
			"<div class='loading'><img src='images/loading.gif'/>&nbsp;&nbsp;页面加载中....</div>")
			.appendTo($("#con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'article?type=' + GlobalVar.type + '&timeRange='
				+ GlobalVar.timeRange + '&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				$(".loading").remove();
				// 加载内容
				for ( var i = 0; i < data.list.length; i++) {
					new ArticlePanel(data.list[i]).getHtml().appendTo("#con");
				}
				// 加载分页
				new Pager(data.pageTotal, 10, page).asHtml().appendTo(
						$(".pageArea"));
			} else {
				alert("系统异常稍后再试");
			}
		}
	});
}