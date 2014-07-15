var changePwd = {};
$(function(){
	$('#newpwd').tooltip({
        position: 'right',
        content: '<span style="color:#fff;">密码只能是数字、字母不能超过且16个字符</span>',
        onShow: function(){
            $(this).tooltip('tip').css({
                backgroundColor: '#666',
                borderColor: '#666'
            });
        }
    });
});
changePwd.saveInfo = function(){
	var oldpwd = $("#oldpwd").val().trim();
	var newpwd = $("#newpwd").val().trim();
	var anewpwd = $("#anewpwd").val().trim();
	if(oldpwd==""){
		ulewo_common.alert("旧密码不能为空",1);
		return;
	}
	if(newpwd==""){
		ulewo_common.alert("新密码不能为空",1);
		return;
	}
	var checkPassWord = /^[0-9a-zA-Z]+$/;         //只能是数字，字母
	if(newpwd.length<6||newpwd.length>16||!checkPassWord.test(newpwd)){
		ulewo_common.alert("密码不符合规范",1);
		return;
	}
	if(newpwd!=anewpwd){
		ulewo_common.alert("两次输入的密码不一致",1);
		return;
	}
	$("#saveBtn").linkbutton('disable');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#pwd_form").serialize(),
		url : global.realPath+"/manage/save_pwd.action",// 请求的action路径
		success : function(data) {
			$("#saveBtn").linkbutton('enable');
			if(data.result=="200"){
				ulewo_common.tipsInfo("密码修改成功",1);
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}