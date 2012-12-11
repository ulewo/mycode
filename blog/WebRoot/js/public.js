$(function(){
	$(window).bind(
			"scroll",
			function(event) {
				// 滚动条到网页头部的 高度，兼容ie,ff,chrome
				var scrollTop = document.documentElement.scrollTop
						+ document.body.scrollTop;
				if (scrollTop > 323) {
					$("#right_main").css({
						"position" : "fixed"
					});
				} else {
					$("#right_main").css({
						"position" : "relative"
					});
				}

			});
});
function loadItem(itemId){
	$("#item").html("");
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'item',// 
		success : function(data) {
			var item =  {};
			item.id="0";
			item.itemName = "全部文章";
			item.articleCount = "0";
			var allItem = new ArticleItem(item);
			if(item.id==itemId||itemId==""){
				allItem.getItemNamePanle().addClass("itemnameselect");
			}
			allItem.asHtml().appendTo($("#item"));
			var items =data.list;
			var count = 0;
			for(var i = 0;i<items.length;i++){
				var articleItem = new ArticleItem(items[i])
				if(items[i].id==itemId){
					articleItem.getItemNamePanle().addClass("itemnameselect");
				}
				articleItem.asHtml().appendTo($("#item"));
				count = count+items[i].articleCount;
			}
		allItem.setCount(count);
		}
	});
}

function loadRecord(time){
	$("#record").html("");
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : 'record',// 
		success : function(data) {
			var records =data.list;
			for(var i = 0;i<records.length;i++){
				var recordItem = new RecordItem(records[i]);
				if(records[i].retime==time){
					recordItem.getItemNamePanle().addClass("itemnameselect");
				}
				recordItem.asHtml().appendTo($("#record"));
			}
		}
	});
}

function ArticleItem(item){
	this.itemPanel = $("<div class='index_item'></div>");
	this.index_itemname = $("<div class='index_itemname'><a href='article?itemId="+item.id+"'>"+item.itemName+"</a></div>").appendTo(this.itemPanel);
	this.countSpan = $("<div class='index_articlecount'>("+item.articleCount+")</div>").appendTo(this.itemPanel);
	$("<div class='clear_float'></div>").appendTo(this.itemPanel);
}

ArticleItem.prototype = {
	asHtml:function(){
		return this.itemPanel;
	},
	getItemNamePanle:function(){
		return this.index_itemname;
	},
	setCount:function(count){
		this.countSpan.html("("+count+")");
	}
	
}

function RecordItem(item){
	this.recordPanel = $("<div class='index_item'></div>");
	this.index_itemname =$("<div class='index_itemname'><a href='article?time="+item.retime+"'>"+item.time+"</a></div>").appendTo(this.recordPanel);
	$("<div class='index_articlecount'>("+item.acount+")</div>").appendTo(this.recordPanel);
	$("<div class='clear_float'></div>").appendTo(this.recordPanel);
}

RecordItem.prototype = {
	asHtml:function(){
		return this.recordPanel;
	},getItemNamePanle:function(){
		return this.index_itemname;
	}
}

function setNameInfo(){
	var value = $(this).val();
	if(value==""||value=="姓名"){
		$(this).val("姓名");
		$(this).css({"color":"#666666"});
	}
}


function clearNameInfo(){
	var value = $(this).val();
	if(value==""||value=="姓名"){
		$(this).val("");
		$(this).css({"color":"#494949"});
	}
}

function clearContentInfo(){
	var value = $(this).val();
	if(value==""||value=="请输入你想说的"){
		$(this).val("");
		$(this).css({"color":"#494949"});
	}
}

function setContentInfo(){
	var value = $(this).val();
	if(value==""||value=="请输入你想说的"){
		$(this).val("请输入你想说的");
		$(this).css({"color":"#666666"});
	}
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
