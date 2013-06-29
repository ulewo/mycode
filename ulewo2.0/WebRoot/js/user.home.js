$(function(){
	$("#talkBtn").bind("click",saveTalk);
	loadTalk();
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
				var mark = $("#mark");
				mark.text(parseInt(mark.text())+1);
				tipsInfo("1分已到碗里");
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
		url : global.realPath+"/user/loadLatestTalk?userId="+userId,// 请求的action路径
		success : function(data) {
			if(data.result=="success"){
				var length = data.list.length;
				if(length>0){
					for(var i=0;i<length;i++){
						new TalkItem(data.list[i]).item.appendTo($("#talklist"));
					}
				}else{
					$("<div class='noinfo'>没有吐槽！</div>").appendTo($("#talklist"));
				}
			}
		}
	});
	
}
