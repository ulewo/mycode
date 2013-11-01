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
		type : 'POST',
		dataType : "json",
		url : global.realPath + "/admin/loadArticle2.action?page=" + page+"&q="+keyWord,// 请求的action路径
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
	$("<input type='checkBox' id='"+data.id+"_input' value='"+data.id+"'>").bind("click",{id:data.id,title:data.title},this.addArticle).appendTo(item_checkbox);
	var title = $("<div class='item_title'>" +
			"<a href='"+global.realPath+"/group/"+data.gid+"/topic/"+data.id+"' target='_blank'>"+data.title+"</a>" +
			"</div>").appendTo(this.item);
	if(data.image!=""&&data.image!=null){
		$("<img src='"+global.realPath+"/images/img.gif'>").appendTo(title);
	}
	$("<div class='item_time'>"+data.postTime+"</div>").appendTo(this.item);
	$("<div class='item_user'><a href='"+global.realPath+"/user/"+data.authorId+"'>"+data.authorName+"</div>").appendTo(this.item);
	$("<div class='clear'></div>").appendTo(this.item);
}

ArticleItem.prototype = {
		addArticle : function(event) {
		var id = event.data.id;
		var title = event.data.title;
		if($(this).attr("checked")&&$("#"+id+"_tag")[0]==null){
			new TitleTag(id,title).tag.appendTo("#myform");
		}else if(!$(this).attr("checked")&&$("#"+id+"_tag")[0]!=null){
			$("#"+id+"_tag").remove();
		}
		
	}
};

function TitleTag(id,title){
	title = title.substring(0,5);
	this.tag = $("<span class='titletag' id='"+id+"_tag'></span>");
	$("<input type='hidden' name='ids' value="+id+">").appendTo(this.tag);
	$("<span class=tag_title>"+title+"</span>").appendTo(this.tag);
	$("<span class='del_tag'></span>").appendTo(this.tag).bind("click",function(){
		$(this).parent().remove();
		$("#"+id+"_input").attr("checked",false);
	});
}