$(function(){
	$("#talkBtn").bind("click",saveTalk);
})

function saveTalk(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#talkcontent").val().trim();
	if(content==""){
		warm("show","吐槽内容不能为空");
		return;
	}
	if(content.length>250){
		warm("show","吐槽内容不能超过250字符");
		return;
	}
	warm("hide","");
	btnLoading($(this),"发布中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			content:content,
			imgurl:$("#imgUrl").val()
		},
		url : global.realPath+"/user/saveTalk.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"发布");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				/*$(".noinfo").remove();
				if($(".article_item").length>0){
					$(".article_item").eq(0).before(new ArticleItem(data.article).article_item);
				}else{
					new ArticleItem(data.article).article_item.appendTo($("#article_item_list"));
				}*/
			}
		}
	});
}
