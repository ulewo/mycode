$(function(){
	$("#saveBtn").bind("click",saveInfo);
})

function saveInfo(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var age = $("#age").val().trim();
	var address = $("#address").val();
	var work = $("#work").val().trim();
	var characters= $("#characters").val().trim();
	if(age!=""&&!age.isNumber()){
		warm("show","年龄只能是数字");
		return;
	}
	
	if(address!=""&&address.length>50){
		warm("show","居住地不能超过50字符");
		return;
	}
	
	if(work!=""&&work.length>50){
		warm("show","职业不能超过50字符");
		return;
	}
	
	if(characters.length>200){
		warm("show","个性签名不能超过200");
		return;
	}
	$(this).attr("disable",true);
	warm("hide","");
	btnLoading($(this),"修改中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#user_form").serialize(),
		url : global.realPath+"/manage/saveUserInfo.action",// 请求的action路径
		success : function(data) {
			$("#saveBtn").attr("disable",false);
			btnLoaded($("#saveBtn"),"保存修改");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("修改成功");
			}
		}
	});
	
	
}