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
	$("#myImg").attr(global.realPath+"/"+imgUrl);
	cutter.reload(global.realPath+"/upload/"+imgUrl);
}

function saveImg(){
		var data = cutter.submit();
		if (data.s=="") {
			alert("请先选择图片");
			return;
		}
		var imgPath = data.s;
		
		var start = imgPath.lastIndexOf("upload");
		var userIcon = imgPath.substring(start,data.s.lastIndexOf("?"));
		alert(userIcon);
		alert(data.x);
		alert(data.y);
		return;
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				"userId" : userId,
				"userIcon" : userIcon,
				"x1" : data.x,
				"y1" : data.y,
				"width" : data.w,
				"height" : data.h,
				"imgtype" : 0,
				"date" : new Date()
			},
			url : 'updateUserIcon.jspx',// 请求的action路径
			success : function(data) {
				if (data.result == "error") {
					art.dialog.tips('保存失败');
				} else {
					art.dialog.tips('保存成功');
					setTimeout("location.reload()", 1000);
				}
				$("#groupicon").val("");
				$("#load").css({
					"display" : "none"
				});
				$("#save").css({
					"display" : "block"
				});
			}
		});
}

