// 文章管理
function loadArticle(page, type) {
	$("#con").html("");
	$(".pageArea").html("");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'admin?method=article&type=' + type + '&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				for ( var i = 0; i < data.list.length; i++) {
					new AdminArticle(data.list[i]).getHtml().appendTo("#con");
				}
				new Pager(data.pageTotal, 10, page).asHtml().appendTo(
						$(".pageArea"));
			} else {
				alert("系统异常稍后再试");
			}
		}
	});
}
function AdminArticle(article) {
	this.articlePanle = $("<div class='article_panle'>");
	this.contentPanle = $(
			"<div class='content_panle'>" + article.content + "</div>")
			.appendTo(this.articlePanle);
	this.imgPanle = $("<div></div>").appendTo(this.contentPanle);
	// 发布时间
	$("<div class='post_time'>" + article.postTime + "</div>").appendTo(
			this.articlePanle);
	// 来自哪里
	var from = article.sourceFrom;
	if (from == "Q") {
		from = "糗事百";
	} else if (from == "H") {
		from = "哈哈";
	} else if (from == "P") {
		from = "捧腹";
	} else {
		from = "友吧中国";
	}
	$("<div class='from'>" + from + "</div>").appendTo(this.articlePanle);

	// 顶、踩数量
	$("<div class='up_down'>" + article.up + "/" + article.down + "</div>")
			.appendTo(this.articlePanle);
	// 操作区域
	this.opPanle = $("<div class='op_panel'></div>")
			.appendTo(this.articlePanle);
	// 图片
	if (article.imgUrl != "") {
		$("<a href='javascript:void(0)'>图片</a>").appendTo(this.opPanle).bind(
				"click", {
					panle : this.imgPanle,
					img : article.imgUrl
				}, this.showImg);
	}
	this.del = $("<a href='javascript:void(0)'>删除</a>").appendTo(this.opPanle)
			.bind("click", {
				id : article.id,
				panle : this.articlePanle
			}, this.deleteArticle);
	var status = article.status;
	if (status == "Y") {
		statusName = "取消审核";
	} else if (status == "N") {
		statusName = "审核";
	}
	this.audit = $(
			"<a href='javascript:void(0)' title='" + article.status + "'>"
					+ statusName + "</a>").appendTo(this.opPanle).bind("click",
			{
				id : article.id
			}, this.auditArticle);
	$("<div class='clear'></div>").appendTo(this.articlePanle);
}
AdminArticle.prototype = {
	getHtml : function() {
		return this.articlePanle;
	},
	deleteArticle : function(event) {
		var id = event.data.id;
		var conPanle = event.data.panle;
		if (confirm("是否将此条言信息删除?")) {
			$.ajax({
				async : true,
				cache : false,
				type : 'GET',
				dataType : "json",
				url : 'admin?method=del&id=' + id,// 请求的action路径
				success : function(data) {
					if (data.result == "success") {
						conPanle.remove();
					} else {
						alert("系统异常稍后再试");
					}
				}
			});
		}
	},
	auditArticle : function(event) {
		var audit = $(this);
		var id = event.data.id;
		var status = audit.attr("title");
		var sendStatsu = "Y";
		if (status == "Y") {
			sendStatsu = "N";
		}
		if (status == "N") {
			sendStatsu = "Y";
		}
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : 'admin?method=audit&status=' + sendStatsu + '&id=' + id,// 请求的action路径
			success : function(data) {
				if (data.result == "success") {
					if (sendStatsu == "Y") {
						audit.html("取消审核");
						audit.attr("title", "Y");
					} else if (sendStatsu == "N") {
						audit.html("审核");
						audit.attr("title", "N");
					}
				} else {
					alert("系统异常稍后再试");
				}
			}
		});
	},
	showImg : function(event) {
		var panle = event.data.panle;
		if (panle.html() == "") {
			panle.show();
			var img = event.data.img;
			$("<img src='upload/big/" + img + "'>").appendTo(panle);
		} else {
			panle.hide();
		}

	}
}

// 查询全部
function queryAll() {
	i = 1;
	queryType = "all";
	loadArticle(1, queryType);
}

function queryAudity() {
	i = 1;
	queryType = "audity";
	loadArticle(1, queryType);
}

function queryAuditn() {
	i = 1;
	queryType = "auditn";
	loadArticle(1, queryType);
}

// 用户管理
function userList(page) {
	if (page == 1) {
		$("#con").html("");
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'admin?method=user&page=' + page,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				for ( var i = 0; i < data.list.length; i++) {
					new UserPanel(data.list[i]).getHtml().appendTo("#con");
				}
			} else {
				alert("系统异常稍后再试");
			}
		}
	});
}

// 用户对象
function UserPanel(user) {
	this.reArticlePanle = $("<div class='reArticle_panle'>");
	this.contentPanle = $("<div class='recontent_panle'>").appendTo(
			this.reArticlePanle).html(user.userName);
	this.opPanle = $("<div class='re_op_panle'>").appendTo(this.reArticlePanle);
	var clear = $("<div class='clear'>").appendTo(this.reArticlePanle);
	this.del = $("<a href='javascript:void(0)'>删除</a>").appendTo(this.opPanle);
}
UserPanel.prototype = {
	getHtml : function() {
		return this.reArticlePanle;
	}
}
