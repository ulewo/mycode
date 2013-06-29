var editor;
$(function(){
	$("#new_article_p").bind("click",showAddForm);
	$("#sub_article_btn").bind("click",addArticle);
});

function showAddForm(){
	$(this).hide();
	warm("hide","");
	$("#add_article").show();
	$("#article_title").val("");
	$("#article_keyword").val("");
	$("#attached_file_name").val("");
	$("#attached_file").val("");
	$("#content").val("");
	$("#uploadFrame").show();
	$(".file_con").remove();
	if(editor==null){
		var width = $("#add_article").outerWidth(true);
		window.UEDITOR_CONFIG.initialFrameWidth = parseInt(width-20);
		editor = new UE.ui.Editor();
		editor.render("editor");
	}
	editor.ready(function(){
	        editor.setContent("");
	});
}
/*********取消***********/
function cancelAdd(){
	$("#new_article_p").show();
	$("#add_article").hide();
}

/*********附件上传回调函数***********/
function showFile(filePath){
	$("#attached_file").val(filePath);
	$("<div class='file_con'><a href='javascript:void(0)'>"+$("#attached_file_name").val()+"</a>&nbsp;&nbsp;<a href='javascript:deleteFile()'>删除</a><img src='"+global.realPath+"/images/load.gif' id='file_deleting'></div>").appendTo($("#file_upload"));
	$("#uploadFrame").hide();
}

function setFileName(fileName){
	$("#attached_file_name").val(fileName);
}

function deleteFile(){
	$("#file_deleting").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			userId:global.userId,
			fileName:$("#attached_file").val(),
			"time" : new Date()
		},
		url : global.realPath+"/group/deleteFile.action",// 请求的action路径
		success : function(data) {
			$("#file_deleting").hide();
			if(data.rsult="success"){
				$(".file_con").remove();
				$("#uploadFrame").show();
			}else{
				alert("删除失败");
			}
		}
	});
}
/********提交**********/
function addArticle(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var article_title = $("#article_title").val().trim();
	var article_item = $("#article_item").val();
	var article_keyword = $("#article_keyword").val().trim();
	var content = editor.getContentTxt().trim();
	var mark =  $("#mark").val().trim();
	if(article_title==""){
		warm("show","标题不能为空");
		return;
	}
	
	if(article_title.length>150){
		warm("show","标题不能超过150");
		return;
	}
	
	if(article_item==""){
		warm("show","请选择分类");
		return;
	}
	
	if(article_keyword.length>150){
		warm("show","关键字不能超过150");
		return;
	}
	if(!mark.isNumber()){
		warm("show","下载积分只能是数字");
		return;
	}
	if(content==""){
		warm("show","内容不能为空");
		return ;
	}else{
		$("#content").val(editor.getContent());
	}
	warm("hide","");
	btnLoading($(this),"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#articleform").serialize(),
		url : global.realPath+"/group/addArticle.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#sub_article_btn"),"发表帖子");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				cancelAdd();
				$(".noinfo").remove();
				if($(".article_item").length>0){
					$(".article_item").eq(0).before(new ArticleItem(data.article).article_item);
				}else{
					new ArticleItem(data.article).article_item.appendTo($("#article_item_list"));
				}
				tipsInfo("5分已到碗里");
			}
		}
	});
}

function ArticleItem(article){
	this.article_item = $("<div class='article_item'></div>");
	var article_tit = $("<div class='article_tit'>").appendTo(this.article_item);
	
	var article_title=$("<div class='article_title'>").appendTo(article_tit);
	$("<a href='"+global.realPath+"/group/"+article.gid+"/topic/"+article.id+"'>"+article.title+"</a>").appendTo(article_title);
	$("<span class='recount'>"+article.reNumber+"/"+article.readNumber+"</span>").appendTo(article_title);
	
	var article_title=$("<div class='article_author'>").appendTo(article_tit);
	$("<a href='"+global.realPath+"/user/"+article.authorId+"'>"+article.authorName+"</a>").appendTo(article_title);
	$("<span class='article_posttime'>发表于&nbsp; "+article.postTime+"</span>").appendTo(article_title);
	
	$("<div class='clear'></div>").appendTo(article_tit);
	
	$("<div class='article_summary'>"+article.summary+"</div>").appendTo(this.article_item);
	
	if(article.image!=""){
		$("<div class='article_attachedimg'><a href='"+global.realPath+"/group/"+article.gid+"/topic/"+article.id+"'><img src='"+article.image+"' style='max-width:150px;'/></a></div>").appendTo(this.article_item);
	}
	
	
}
/*******图片上传回调函数*******/
function initImg(imageUrls){
	if(imageUrls.length>0){
		$("#faceImg").val(imageUrls[0].url);
	}
}

