function btnLoading(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","disable");
}

function btnLoaded(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","");
}

function warm(type,msg){
	if(type=="show"){
		$(".result_info").css({"display":"inline-block"});
		$("#warm_icon").removeClass("info");
		$("#warm_icon").css({"display":"inline-block","color":"red"});
		$("#warm_info").text(msg);
	}else if(type=="hide"){
		$('.result_info').hide();
	}
}

function info(msg){
	$(".result_info").css({"display":"inline-block"});
	$("#warm_icon").addClass("info");
	$("#warm_icon").css({"display":"inline-block","color":"#494949"});
	$("#warm_info").text(msg);
	setTimeout(function(){
		$('.result_info').fadeOut("slow");
	},2000);
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
				"<li><a href='javascript:loadPage(1)' class='prePage'>首&nbsp;&nbsp;页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadPage(" + (page - 1)
						+ ")'><</a></li>").appendTo(this.ulPanle);
	} 
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadPage(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a href='javascript:loadPage(" + (page + 1)
						+ ")'>></a></li>").appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadPage(" + totalPage
						+ ")' class='prePage'>尾&nbsp;&nbsp;页</a></li>").appendTo(
				this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}