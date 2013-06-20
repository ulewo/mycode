$(function(){
	$("#article_item").bind("change",function(){
		document.location.href=global.realPath+"/groupManage/"+global.gid+"/group_article?itemId="+$(this).val()+"&page="+page;
		$(this).val();
	});
	$("#setTop").bind("click",function(){
		doOp(0);
	});
	$("#cancelTop").bind("click",function(){
		doOp(1);
	});
	$("#setGood").bind("click",function(){
		doOp(2);
	});
	$("#cancelGood").bind("click",function(){
		doOp(3);
	});
	
	$("#deleteArticle").bind("click",function(){
		if(confirm("确定要删除此条记录吗？")){
			doOp(4);
		}
		
	});
});


function doOp(type) {
	if ($(".checkId:checked").length < 1) {
		alert("请选择要操作的主题");
		return 
	}
	$("#opType").val(type);
	$("#subform").submit();
}
