var isHaveImg = false;
$(function(){
	$("#saveBtn").bind("click",submitForm);
})

function submitForm(){
	var subBtn = $("#saveBtn");
	if(subBtn.attr("disable")=="disable"){
		return;
	}
	var title = $("#title").val().trim();
	var itemId = $("#itemId").val();
	var keyword = $("#keyword").val().trim();
	var content = editor.getContentTxt().trim();
	
	if(title==""){
		warm("show","标题不能为空");
		return;
	}
	
	if(title.length>150){
		warm("show","标题不能超过150");
		return;
	}
	
	if(itemId==""){
		warm("show","请选择分类");
		return;
	}
	
	if(keyword.length>150){
		warm("show","关键字不能超过150");
		return;
	}
	if(content==""){
		warm("show","内容不能为空");
		return ;
	}else{
		$("#content").val(editor.getContent());
	}
	warm("hide","");
	btnLoading(subBtn,"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#blogform").serialize(),
		url : global.realPath+"/manage/saveblog.action",// 请求的action路径
		success : function(data) {
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				if($("#blogId")[0]==null){
					tipsInfo("5分已到碗里");
					setTimeout(function(){
						btnLoaded(subBtn,"发表博文");
						document.location.href=global.realPath+"/user/"+global.userId+"/blog";
					},2000);
				}else{
					document.location.href=global.realPath+"/user/"+global.userId+"/blog";
				}
				
			}
		}
	});
}
/********编辑器图片上传回调函数**********/
function initImg(imageUrls){
	if(imageUrls!=null){
		for(var i=0;i<imageUrls.length;i++){
			$("<input type='hidden' name='image' value="+imageUrls[i]+">").appendTo($("#blogform"));
		}
	}
}