$(function(){
	$("#article_item").bind("change",function(){
		document.location.href=global.realPath+"/groupManage/"+global.gid+"/manage/group_article?itemId="+$(this).val()+"&page="+page;
		$(this).val();
	});
	$("#setTop").bind("click",function(){
		setTop(1);
	});
	$("#cancelTop").bind("click",function(){
		setTop(0);
	});
	$("#setGood").bind("click",function(){
		setGood(1);
	});
	$("#cancelGood").bind("click",function(){
		setGood(0);
	});
	$("#setTitle").bind("click",function(){
		setTitle();
	});
	$("#deleteArticle").bind("click",function(){
		deleteArticle();
	});
});


function checkSelect() {
	if ($(".checkId:checked").length < 1) {
		alert("请选择要操作的主题");
		return false;
	}
	return true;
}

function setTop(type){
	
}
