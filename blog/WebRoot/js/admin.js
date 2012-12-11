function loadInfo(id){
	initItem();
	if(id!=""){
		setEditInfo(id);
	}
}
function initItem(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'item',// 
		success : function(data) {
			var items =data.list;
			var select = $("<select name='itemId' id='itemId'></select>").appendTo("#item");
			for(var i = 0;i<items.length;i++){
				$("<option value='"+items[i].id+"'>"+items[i].itemName+"</option>").appendTo(select);
			}
		}
	});
}
//更新设置信息
function setEditInfo(id){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'adminArticle?method=admin_edite&id='+id,
		success : function(data) {
			var article = data.article;
			$("#title").val(article.title);
			$("#tag").val(article.tags);
			editor.setContent(article.content);
			editor.html(article.content);
			var itemOptions = $("#itemId").children();
			for(var i=0;i<itemOptions.length;i++){
				if($(itemOptions[i]).val()==article.itemId){
					$(itemOptions[i]).attr("selected",true);
				}
			}
		}	
	});
}

function loadItem(){
	$("#con").html("");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'adminItem',// 
		success : function(data) {
			var items =data.list;
			for(var i = 0;i<items.length;i++){
				var item = new ArticleItem(items[i]);
				item.asHtml().appendTo($("#con"));
			}
		}
	});
}

function search(){
	var itemId = $("#itemId").find("option:selected").val();
	GlobalVar.itemId = itemId;
	loadArticle(GlobalVar.page);
}

function loadArticle(page){
	GlobalVar.page = page;
	$("#con").html("");
	$(".pageArea").html("");
	$("<div class='lodding'>加载中.....</div>").appendTo($("#con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : 'adminArticle?method=admin_list&page='+page+'&itemId='+GlobalVar.itemId,
		success : function(data) {
			$(".lodding").remove();
			var articles = data.list;
			for(var i=0;i<articles.length;i++){
				var panle = new ArticlePanle(articles[i]);
				var tr = $(panle.asHtml());
				if(i%2!=0){
					tr.css({"background":"#F5F5F5"});
				}
				tr.appendTo("#con");
			}
			new Pager(data.pageTotal, 10,page).asHtml().appendTo(
					$(".pageArea"));
		}
	});
}

function ArticlePanle(article){
	this.id = article.id;
	this.th = $("<div class='article_th article_tr'></div>");
	$("<div class='article_tit'>"+article.title+"</div>").appendTo(this.th);
	$("<div class='article_time'>"+article.postTime.substring(0,19)+"</div>").appendTo(this.th);
	$("<div class='article_re'>"+article.readCount+"/"+article.reCount+"</div>").appendTo(this.th);
	var articleOp = $("<div class='article_op'></div>").appendTo(this.th);
	$("<a href='admin_add.jsp?id="+this.id+"' class='btn'>修改</a>").appendTo(articleOp);
	$("<a href='javascript:void(0)' class='btn'>删除</a>").bind("click",{id:this.id},this.delArticle).appendTo(articleOp);
}
ArticlePanle.prototype = {
	asHtml:function(){
		return this.th;
	},
	delArticle:function(event){
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : 'adminArticle?method=admin_delete&id='+event.data.id,
			success : function(data) {
				loadArticle(GlobalVar.page);
			}
		});
	}
}
//添加一个栏目
function addItem(){
	var obj = new Object();
	obj.id="";
	obj.itemName="";
	obj.rang="";
	var item = new ArticleItem(obj);
	item.asHtml().appendTo($("#con"));
}
function ArticleItem(item){
	this.id = item.id;
	this.th = $("<div class='article_th article_tr'></div>");
	$("<div class='article_tit'><input type='text' value='"+item.itemName+"'></div>").appendTo(this.th);
	$("<div class='article_time article_item'><input type='text' value='"+item.rang+"'></div>").appendTo(this.th);
	var articleOp = $("<div class='article_op'></div>").appendTo(this.th);
	$("<a href='javascript:void(0)' class='btn'>保存</a>").bind("click",{id:item.id},this.saveItem).appendTo(articleOp);
	$("<a href='javascript:void(0)' class='btn'>删除</a>").bind("click",{id:this.id},this.delItem).appendTo(articleOp);
}
ArticleItem.prototype = {
		asHtml:function(){
			return this.th;
		},
		delItem:function(event){
			$.ajax({
				async : true,
				cache : false,
				type : 'GET',
				dataType : "json",
				url : 'adminItem?method=admin_delete&id='+event.data.id,
				success : function(data) {
					loadItem();
				}
			});
		},
		saveItem:function(event){
			var itemName = $(this).parent().parent().children().eq(0).children().eq(0).val();
			var rang = $(this).parent().parent().children().eq(1).children().eq(0).val();
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data:{
					id:event.data.id,
					itemName :itemName,
					rang :rang
				},
				url : 'adminItem?method=admin_save',
				success : function(data) {
					loadItem();
				}
			});
		}
	}

function initImg(img){
	$("#images").show();
	var img_con = $("<div class='img_panle'></div>").appendTo($("#imagecon"));
	$("<div><img src='upload/"+img+"' width='60' height='60'/></div>").appendTo(img_con);
	$("<div><a href='javascript:insertImgSingle(\""+img+"\")'>插入</a></div>").appendTo(img_con);
}

function insertImgSingle(imgUrl){
	editor.insertHtml("<img src='upload/"+imgUrl+"' class='imageborder'></br></br>");
}
