$(function(){
	$("#editBtn").bind("click",editGroup);
	loadCategory();
});

function editGroup(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var group_name = $("#group_name").val().trim();
	var group_desc = $("#group_desc").val().trim();
	if(group_name==""){
		warm("show","窝窝名称不能为空");
		return;
	}
	if(group_name.length>50){
		warm("show","窝窝名称不能超过50字符");
		return;
	}
	if(group_desc==""){
		warm("show","窝窝简介不能为空");
		return;
	}
	if(group_desc.length>500){
		warm("show","窝窝简介不能超过500字符");
		return;
	}
	warm("hide","");
	btnLoading($(this),"创建中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_info").serialize(),
		url : global.realPath+"/groupmanage/creteGroup.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#editBtn"),"创建窝窝");
			if(data.result=="200"){
				ulewo_common.tipsInfo("创建成功");
				setTimeout(function(){
					document.location.href=global.realPath+"/group/"+data.group.gid;
				},1500);
			}else{
				warm("show",data.msg);	
			}
		}
	});
}
var groupCategory;
function loadCategory(){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_info").serialize(),
		url : global.realPath+"/loadGroupCategory",// 请求的action路径
		success : function(data) {
			if(data.result=="200"){
					var select = $("#cate").bind("change",function(){
						var sub = $("#sub").empty();
						var children;
						for(var i=0,length=data.groupCateGroy.length;i<length;i++){
							if(data.groupCateGroy[i].groupCategoryId==$(this).children('option:selected').val()){
								children = data.groupCateGroy[i].children;
								break;
							}
						}
						for(var i=0,length=children.length;i<length;i++){
							$("<option value='"+children[i].groupCategoryId+"'>"+children[i].name+"</option>").appendTo(sub);
						}
					});
				for(var i=0,length=data.groupCateGroy.length;i<length;i++){
					$("<option value='"+data.groupCateGroy[i].groupCategoryId+"'>"+data.groupCateGroy[i].name+"</option>").appendTo(select);
				}
				var children = data.groupCateGroy[0].children;
				for(var i=0,length=children.length;i<length;i++){
					$("<option value='"+children[i].groupCategoryId+"'>"+children[i].name+"</option>").appendTo(sub);
				}
			}else{
				warm("show",data.msg);	
			}
		}
	});
}
