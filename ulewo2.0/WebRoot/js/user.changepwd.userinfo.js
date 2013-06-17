$(function(){
	$("#saveBtn").bind("click",saveInfo);
})

function saveInfo(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var oldpwd = $("#oldpwd").val().trim();
	var newpwd = $("#newpwd").val().treim();
	var anewpwd = $("#anewpwd").val().trim();
	if(oldpwd==""){
		warm("show","旧密码不能为空");
		return;
	}
	
	if(newpwd==""){
		warm("show","新密码不能为空");
		return;
	}
	var checkPassWord = /^[0-9a-zA-Z]+$/;         //只能是数字，字母
	if(newpwd.length<6||newpwd.length>16||!checkPassWord.test(newpwd)){
		warm("show","密码不符合规范");
		return;
	}
	if(newpwd!=anewpwd){
		warm("show","两次输入的密码不一致");
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
		data : $("#pwd_form").serialize(),
		url : global.realPath+"/manage/save_pwd.action",// 请求的action路径
		success : function(data) {
			$("#saveBtn").attr("disable",false);
			btnLoaded($("#saveBtn"),"修改密码");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("修改成功");
			}
		}
	});
}