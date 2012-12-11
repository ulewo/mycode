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