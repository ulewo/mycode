var keyWord="";
$(function(){
	loadPage(1);
	$("#searchBtn").bind("click",function(){
		keyWord = $("#keyword").val();
		loadPage(1);
	});
	$("#weekHotBtn").bind("click",function(){
		$("#myform").submit();
	});
});

function loadPage(page){
	loadArticle(page,keyWord);
}

function loadArticle(page,keyWord) {
	keyWord = encodeURI(encodeURI(keyWord));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/admin/loadArticle.action?page=" + page+"&q="+keyWord,// 请求的action路径
		success : function(data) {
			
			var list = data.result.list;
			var length = list.length;
			if(length>0){
				$("#article_list").empty();
				$("#pager").empty();
				for ( var i = 0; i < length; i++) {
					var article = list[i];
					new ArticleItem(article).item.appendTo($("#article_list"));
				}
				if(data.result.pageTotal>1){
					new Pager(data.result.pageTotal,10,data.result.page).asHtml().appendTo($("#pager"));
				}else{
					$("#pager").hide();
				}
			}
		}
	});
}

function ArticleItem(data){
	this.item = $("<div class='article_item'></div>");
	var item_checkbox = $("<div class='item_checkbox'></div>").appendTo(this.item);
	$("<input type='checkBox' value='"+data.id+"'>").bind("click",{id:data.id},this.addArticle).appendTo(item_checkbox);
	var title = $("<div class='item_title'>" +
			"<a href='"+global.realPath+"/group/"+data.gid+"/topic/"+data.id+"' target='_blank'>"+data.title+"</a>" +
			"</div>").appendTo(this.item);
	if(data.image!=""){
		$("<img src='"+global.realPath+"/images/img.gif'>").appendTo(title);
	}
	$("<div class='item_time'>"+data.postTime+"</div>").appendTo(this.item);
	$("<div class='item_user'><a href='"+global.realPath+"/user/"+data.authorId+"'>"+data.authorName+"</div>").appendTo(this.item);
	$("<div class='clear'></div>").appendTo(this.item);
}

ArticleItem.prototype = {
		addArticle : function(event) {
		var id = event.data.id;
		$("<input type='text' name='ids' value="+id+" class='tagInput'>").appendTo("#myform");
	}
}