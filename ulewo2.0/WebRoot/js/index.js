$(function(){
	$("#talkBtn").bind("click",saveTalk);
	loadTalk();
	$("#talkcontent").bind("focus",function(){
		if($(this).val()=="今天你吐槽了吗？"){
			$(this).val("");
			$(this).css({"color":"#494949"});
		}
	});
	$("#talkcontent").bind("blur",function(){
		if($(this).val()==""){
			$(this).val("今天你吐槽了吗？");
			$(this).css({"color":"#A9A9A9"});
		}
	});
})

function saveTalk(){
	if(global.userId==""){
		alert("请先登录");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#talkcontent").val().trim();
	if(content==""||content=="今天你吐槽了吗？"){
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
			imgUrl:$("#imgUrl").val()
		},
		url : global.realPath+"/user/saveTalk.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"发布");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("发布成功");
				$(".noinfo").remove();
				if($(".talkitem").length>0){
					$(".talkitem").eq(0).before(new TalkItem(data.talk).item);
				}else{
					new TalkItem(data.talk).item.appendTo($("#talklist"));
				}
				$("#talkcontent").val("");
				deleteImg();
			}
		}
	});
}

function loadTalk(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadLatestTalk",// 请求的action路径
		success : function(data) {
			if(data.result=="success"){
				var length = data.list.length;
				if(length>0){
					for(var i=0;i<length;i++){
						new TalkItem(data.list[i]).item.appendTo($("#talklist"));
					}
				}
			}
		}
	});
}
