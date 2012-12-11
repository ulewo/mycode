// 评论管理
function reArticleList(page) {
	$("#con").html("");
	$(".pageArea").html("");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'admin?method=rearticle&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				for ( var i = 0; i < data.list.length; i++) {
					new ReArticle(data.list[i]).getHtml().appendTo("#con");
				}
				new Pager(data.pageTotal, 10, page).asHtml().appendTo(
						$(".pageArea"));
			} else {
				alert("系统异常稍后再试");
			}
		}
	});
}

function loadArticle(page) {
	AdminGloblParam.page = page;
	reArticleList(page);
}

// 评论对象
function ReArticle(rearticle) {
	this.reArticlePanle = $("<div class='reArticle_panle'>");
	this.contentPanle = $("<div class='recontent_panle'>").appendTo(
			this.reArticlePanle).html(rearticle.content);
	this.opPanle = $("<div class='re_op_panle'>").appendTo(this.reArticlePanle);
	var clear = $("<div class='clear'>").appendTo(this.reArticlePanle);
	this.del = $("<a href='javascript:void(0)'>删除</a>").appendTo(this.opPanle);
}

ReArticle.prototype = {
	getHtml : function() {
		return this.reArticlePanle;
	}
}
