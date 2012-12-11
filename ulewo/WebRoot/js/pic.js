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
		url : 'article?type=pic&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				$(".loading").remove();
				var part1 = $("<div class='part'></div>").appendTo("#con");
				var part2 = $("<div class='part'></div>").appendTo("#con");
				var part3 = $("<div class='part'></div>").appendTo("#con");
				var part4 = $("<div class='part'></div>").appendTo("#con");
				$("<div class='clear'></div>").appendTo("#con");
				for ( var i = 0; i < data.picList1.length; i++) {
					new PicArticle(data.picList1[i]).getHtml().appendTo(part1);
				}
				for ( var i = 0; i < data.picList2.length; i++) {
					new PicArticle(data.picList2[i]).getHtml().appendTo(part2);
				}
				for ( var i = 0; i < data.picList3.length; i++) {
					new PicArticle(data.picList3[i]).getHtml().appendTo(part3);
				}
				for ( var i = 0; i < data.picList4.length; i++) {
					new PicArticle(data.picList4[i]).getHtml().appendTo(part4);
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

function PicArticle(article) {
	this.single = $("<div class='single'></div>");
	$("<div class='single_top'></div>").appendTo(this.single);
	this.single_center = $("<div class='single_center'></div>").appendTo(
			this.single);
	$(
			"<div class='pic_detail'><a href='detail?id=" + article.id
					+ "' target='_blank'>查看详情</a></div>").appendTo(
			this.single_center);
	$("<div class='single_bottom'></div>").appendTo(this.single);
	this.single_img_con = $("<div class='single_img'></div>").appendTo(
			this.single_center);
	this.single_a = $(
			"<a href='javascript:showPic(\"" + GlobalVar.url + "upload/big/"
					+ article.imgUrl + "\")'></a>").appendTo(
			this.single_img_con);
	this.single_img = $("<img src='images/load.gif'>").appendTo(this.single_a);
	var browser = getBrowserType();
	var img = new Image();
	var _sigle_img = this.single_img;
	img.src = GlobalVar.url + "upload/big/" + article.imgUrl;
	if (browser == "IE") {
		if (img.onreadystatechange != null) {
			img.onreadystatechange = function() {
				if (img.readyState == "complete") {
					_sigle_img.attr("src", img.src);
				}
			}
		} else {
			_sigle_img.attr("src", img.src);
		}
	} else if (browser == "others") {
		img.onload = function() {
			if (img.complete) {
				_sigle_img.attr("src", img.src);
			}
		}
	}
	this.single_con = $(
			"<div class='single_con'>" + article.content.substring(0, 30)
					+ "</div>").appendTo(this.single_center);
	this.read_info = $(
			"<div class='readInfo'><span class='re_time'>"
					+ article.postTime.substring(0, 19)
					+ "</span><span class='re_count'><a href='detail?id="
					+ article.id + "' target='_blank'>评论</a><span>("
					+ article.reCount + ")</span></span></div>").appendTo(
			this.single_center);
}
PicArticle.prototype = {
	getHtml : function() {
		return this.single;
	}
}

function getBrowserType() {
	var browser = navigator.userAgent.indexOf("MSIE") > 0 ? 'IE' : 'others';
	return browser;
}

function showPic(imgUrl) {
	var img = new Image();
	img.src = imgUrl;
	var dialog = $("<div class='dialogPic'></div>").appendTo($("body"));
	$("<div class='closeOp'><a href='javascript:void(0)'></a></div>").bind(
			"click", function() {
				$(GlobalVar.shadow).hide();
				$(".dialogPic").remove();
			}).appendTo(dialog);
	$("<div><img src='" + img.src + "'></div>").appendTo(dialog);
	var left = ($(document).width() - img.width) / 2;
	var top = $(document).scrollTop();
	$(dialog).css({
		"left" : left + "px",
		"top" : top + "px"
	});
	showShadow();
}
function showShadow() {
	if (GlobalVar.shadow == "") {
		GlobalVar.shadow = $("<div class='shadow'></div>").appendTo($("body"));
	} else {
		$(GlobalVar.shadow).show();
	}
	var height = $(document).height();
	$(GlobalVar.shadow).css({
		"height" : height + "px"
	});
}

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