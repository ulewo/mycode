function submitForm(){
	var subBtn = $("#sub_article_btn");
	if(subBtn.attr("disable")=="disable"){
		return;
	}
	var article_title = $("#article_title").val().trim();
	var article_item = $("#article_item").val();
	var article_keyword = $("#article_keyword").val().trim();
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
	
	var infoError = false;
	if(UE.utils.trim(editor.getContentTxt())==""){
		infoError = true;
		if(editor.getContent().haveContent()){
			infoError = false;
		}
	}
	if(infoError){
		warm("show","内容不能为空");
		return ;
	}
	warm("hide","");
	btnLoading(subBtn,"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#articleform").serialize(),
		url : global.realPath+"/groupManage/saveArticle.action",// 请求的action路径
		success : function(data) {
			btnLoaded(subBtn,"修改帖子");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("修改成功");
			}
		}
	});
}
