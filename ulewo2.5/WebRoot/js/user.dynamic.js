dynamic.page = 1;
$(function(){
	dynamic.loadDynamic(dynamic.page);
	$("#loadmorebtn").click(function() {
		dynamic.page =dynamic.page+1;
		dynamic.loadDynamic(dynamic.page);
	});
});
dynamic.loadDynamic = function(page){
	$("#loading").show();
	$("#loadmorebtn").hide();
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + '/user/dynamic?page='+page+'&userId=' + dynamic.userId,// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.resultCode = "200"){
				var list = data.result.list;
				var length = list.length;
				for ( var i = 0; i < length; i++) {
					new DynamicItem(list[i]).appendTo($("#dynamicList"));
				}
				if (data.result.page.pageTotal > dynamic.page) {
					$("#loadmorebtn").css({
						"display" : "block"
					});
				} else {
					$("#loadmorebtn").hide();
				}
				dynamic.page = data.result.page.page;
			}else{
				alert(data.msg);
			}
		}
	});
}


function DynamicItem(data){
	this.item = $("<div class='dy-item'></div>");
	var iconCon = $("<div class='icon-con'></div>").appendTo(this.item);
	var iconLeft = $("<div class='icon-left'><img src='"+global.realPath+"/upload/"+data.userIcon+"'/></div>").appendTo(iconCon);
	var iconRight = $("<div class='icon-right'></div>").appendTo(iconCon);
	var rightName = $("<div class='right-name'></div>").appendTo(iconRight);
	var nameLink = $("<a href='"+global.realPath+"/user/"+data.userId+"' target='_blank'>"+data.userName+"</a>").appendTo(rightName);
	var realPath = "";
	if(data.type=="T"){
		$("<span>&nbsp;&nbsp;发表文章&nbsp;&nbsp;</span><a href='"+global.realPath+"/group/"+data.gid+"/topic/"+data.id+"' target='_blank'>"+data.title+"</a>").appendTo(rightName);
	}else if(data.type=="B"){
		$("<span>&nbsp;&nbsp;发表博文&nbsp;&nbsp;</span><a href='"+global.realPath+"/user/"+data.userId+"/blog/"+data.id+"' target='_blank'>"+data.title+"</a>").appendTo(rightName);
	}else if(data.type=="L"){
		realPath = global.realPath+"/upload/";
		$("<span>&nbsp;&nbsp;发表吐槽&nbsp;&nbsp;</span>").appendTo(rightName);
	}else if(data.type=="W"){
		$("<span>&nbsp;&nbsp;创建了窝窝&nbsp;&nbsp;</span>").appendTo(rightName);
	}
	var rightTime = $("<div class='right-tiem'><span>"+data.showCreateTime+"</span><span>&nbsp;&nbsp;评论("+data.commments+")</span></div>").appendTo(iconRight);
	var summary = data.summary;
	if(data.type=='L'){
		for ( var emo in emotion_data) {
			summary = summary.replace(emo, "&nbsp;<img src='" + global.realPath
					+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
		}
	}
	var summary = $("<div class='summary'>"+summary+"</div>").appendTo(this.item);
	var imagesArray = data.imagesArray;
	if(imagesArray!=null){
		var imagUrl = "";
		var imageCon = $("<div class='imageCon'></div>").appendTo(this.item);
		var image;
		var length = imagesArray.length;
		if(length >=5){
			length = 5
		}
		for(var i=0;i<length;i++){
			image = imagesArray[i];
			if(data.type=="T"||data.type=="B"){
				imagUrl = realPath+image.substring(0,image.lastIndexOf("small")-1);
			}else if(data.type=="L"){
				imagUrl = realPath+image;
			}
			$("<a href='"+imagUrl+"' target='_blank'><img src='"+realPath+image+"'></a><span>&nbsp;&nbsp;</span>").appendTo(imageCon);
		}
	}
	return this.item;
}