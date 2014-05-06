var groupManageNotice = {};

groupManageNotice.editGroupNotice = function(){
	var groupNotice = $("#group_notice").val().trim();
	if(groupNotice.length>300){
		ulewo_common.alert("窝窝公告不能超过300字符",1);
		return;
	}
	$("#saveBtn").linkbutton('disable');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_form").serialize(),
		url : global.realPath+"/groupmanage/saveGroupNotice.action",// 请求的action路径
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