$(function(){
	$("#editBtn").bind("click",editGroup);
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
	btnLoading($(this),"修改中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_info").serialize(),
		url : global.realPath+"/groupManage/"+global.gid+"/editGroup.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#editBtn"),"确认修改");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("修改成功");
			}
		}
	});
}