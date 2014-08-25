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
		var data = cutter.submit();
		if (data.s=="") {
			ulewo_common.alert("请先选择图片",1);
			return;
		}
		var imgPath = data.s;
		var start = imgPath.lastIndexOf("upload");
		var groupIcon = imgPath.substring(start,data.s.lastIndexOf("?"));
		ulewo_common.loading("show","正在保存头像");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				"gid":groupManageIcon.gid,
				"groupIcon" : groupIcon,
				"x1" : data.x,
				"y1" : data.y,
				"width" : data.w,
				"height" : data.h,
				"date" : new Date()
			},
			url : global.realPath+"/groupmanage/saveGroupIcon.action",// 请求的action路径
			success : function(data) {
				ulewo_common.loading();
				cutter.os="";
				if(data.result=="200"){
					ulewo_common.tipsInfo("保存成功",1);
					setTimeout(function(){
						parent.gotoTreeNode(11);
					},1500);
				}else{
					ulewo_common.alert(data.msg,2);
				}
			}
		});
}

