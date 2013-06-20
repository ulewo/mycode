var isHaveImg = false;
$(function(){
	$("#saveBtn").bind("click",addBlog);
})

function addBlog(){
	if($(this).attr("disable")=="disable"){
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
	btnLoading($(this),"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#blogform").serialize(),
		url : global.realPath+"/manage/saveblog.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#saveBtn"),"发表博文");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				document.location.href=global.realPath+"/user/"+global.userId+"/blog";
			}
		}
	});
}
/********编辑器图片上传回调函数**********/
function initImg(imageUrls){
}