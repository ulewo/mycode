var GlobalVar = {};
GlobalVar.page=1;
function Pager(totalPage, pageNum, page) {
	this.ulPanle = $("<ul></ul>");
	if (totalPage <= 1) {
		return;
	}
	// 起始页
	var beginNum = 0;
	// 结尾页
	var endNum = 0;

	if (pageNum > totalPage) {
		beginNum = 1;
		endNum = totalPage;
	}

	if (page - pageNum / 2 < 1) {
		beginNum = 1;
		endNum = pageNum;
	} else {
		beginNum = page - pageNum / 2 + 1;
		endNum = page + pageNum / 2;
	}

	if (totalPage - page < pageNum / 2) {
		beginNum = totalPage - pageNum + 1;
	}
	if (beginNum < 1) {
		beginNum = 1;
	}
	if (endNum > totalPage) {
		endNum = totalPage;
	}
	if (page > 1) {
		$(
				"<li><a href='javascript:loadArticle(1)' class='prePage'>第一页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + (page - 1)
						+ ")'>上一页</a></li>").appendTo(this.ulPanle);
	} else {
		$("<li><span>第一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>上一页</span></li>").appendTo(this.ulPanle);
	}
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadArticle(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a href='javascript:loadArticle(" + (page + 1)
						+ ")'>下一页</a></li>").appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + totalPage
						+ ")' class='prePage'>最后页</a></li>").appendTo(
				this.ulPanle);
	} else {
		$("<li><span>下一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>最后页</span></li>").appendTo(this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}