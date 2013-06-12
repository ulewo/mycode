$(function(){
	$("#editBtn").bind("click",editGroup);
});

function editGroup(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var group_notice = $("#group_notice").val().trim();
	warm("hide","");
	btnLoading($(this),"修改中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_info").serialize(),
		url : global.realPath+"/groupManage/"+global.gid+"/editNotice.action",// 请求的action路径
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