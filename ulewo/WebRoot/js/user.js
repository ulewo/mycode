var haveUploadImg = false;
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
		url : 'userArticle?type=' + GlobalVar.type + '&uid=' + GlobalVar.uid
				+ '&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				$(".loading").remove();
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

function initImg(img) {
	haveUploadImg = true;
	cutter.reload("showImg?img=" + img);
	$("#purviews").show();
}

function saveCutImg() {
	if (haveUploadImg) {
		$("#cutSave").hide();
		$("#loadImg").show();
		var data = cutter.submit();
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : 'avatarServlet?type=cut',// 请求的action路径
			data : {
				x1 : data.x,
				y1 : data.y,
				width : data.w,
				height : data.h
			},
			success : function(data) {
				if (data.result == "success") {
					$("#avatarIcon").attr("src",
							GlobalVar.url + "upload/avatar/" + data.imgUrl);
				} else {
					alert("系统异常稍后再试");
				}
				haveUploadImg = false;
				$("#cutarea").children("div").remove();
				$("#purviews").hide();
				$("#cutSave").show();
				$("#loadImg").hide();
			}
		});
	} else {
		alert("请选择图片先");
	}

}