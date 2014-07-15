var groupManageBaseInfo = {};

groupManageBaseInfo.editGroup = function(){
	var group_name = $("#group_name").val().trim();
	var group_desc = $("#group_desc").val().trim();
	if(group_name==""){
		ulewo_common.alert("窝窝名称不能为空",1);
		return;
	}
	if(group_name.length>50){
		ulewo_common.alert("窝窝名称不能超过50字符",1);
		return;
	}
	if(group_desc==""){
		ulewo_common.alert("窝窝简介不能为空",1);
		return;
	}
	if(group_desc.length>500){
		ulewo_common.alert("窝窝简介不能超过500字符",1);
		return;
	}
	$("#saveBtn").linkbutton('disable');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_form").serialize(),
		url : global.realPath+"/groupmanage/saveGroup.action",// 请求的action路径
		success : function(data) {
			$("#saveBtn").linkbutton('enable');
			if(data.result=="200"){
				ulewo_common.tipsInfo("保存成功",1);
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}