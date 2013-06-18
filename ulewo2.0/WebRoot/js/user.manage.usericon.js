var cutter = new jQuery.UtrialAvatarCutter(
		{
			//主图片所在容器ID
			content : "picture_original",
			
			//缩略图配置,ID:所在容器ID;width,height:缩略图大小
			purviews : [{id:"picture_200",width:80,height:80}],
			
			//选择器默认大小
			selector : {width:80,height:80}
		}
	);

$(function(){
	cutter.init();
	$("#saveBtn").bind("click",saveImg);
})


function showImg(imgUrl){
	$("#imgarea").show();
	$("#myImg").attr(global.realPath+"/"+imgUrl);
	cutter.reload(global.realPath+"/upload/"+imgUrl);
}

function saveImg(){
		if($(this).attr("disable")=="disable"){
			return;
		}
		var data = cutter.submit();
		if (data.s=="") {
			alert("请先选择图片");
			return;
		}
		var imgPath = data.s;
		var start = imgPath.lastIndexOf("upload");
		var userIcon = imgPath.substring(start,data.s.lastIndexOf("?"));
		warm("hide","");
		btnLoading($(this),"保存中<img src='"+global.realPath+"/images/load.gif' width='12'>");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				"img" : userIcon,
				"x1" : data.x,
				"y1" : data.y,
				"width" : data.w,
				"height" : data.h,
				"date" : new Date()
			},
			url : 'saveUserIcon.action',// 请求的action路径
			success : function(data) {
				btnLoaded($("#saveBtn"),"保存设置");
				$("#imgarea").hide();
				cutter.os="";
				if(data.result=="fail"){
					warm("show",data.message);
					return;
				}else{
					info("保存成功");
					$("#user_icon").attr("src",data.userIcon);
				}
			}
		});
}

