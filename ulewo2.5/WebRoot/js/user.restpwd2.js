$(function(){
	$("#rest_btn_2").bind("click",restpwd);
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	restpwd();
	    }  
	};
});

// 重置密码
function restpwd() {
	var checkPassWord = /^[0-9a-zA-Z]+$/;         //只能是数字，字母
	var obj = $("#rest_btn_2");
	if(obj.attr("disable")=="disable"){
		return;
	}
	var newpwd = $("#newpwd").val().trim();
	var renewpwd = $("#renewpwd").val().trim();
	var code = $("#code").val().trim();
	
	if(newpwd==""){
		warm("show","新密码不能为空");
		return;
	}
	if(newpwd.length<6||newpwd.length>16||!checkPassWord.test(newpwd.trim())){
		warm("show","密码不符合规范");
		return;
	}
	if(newpwd!=renewpwd){
		warm("show","两次密码输入不一致");
		return;
	}
	if(code==""){
		warm("show","验证码不能为空");
		return;
	}
	//清除错误信息
	warm("hide","");
	//登陆等待
	btnLoading(obj,"重置密码中<img src='"+global.realPath+"/images/load.gif' width=12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			account:account,
			activationCode:activationCode,
			pwd:newpwd,
			code:code,
			"time" : new Date()
		},
		url : global.realPath+"/user/resetpwd.do",// 请求的action路径
		success : function(data) {
			btnLoaded(obj,"重置密码");
			if (data.result == "200") {// 
				info("密码重置成功<a href='"+global.realPath+"/login'>立即登录</a>",false);
			}else{
				warm("show",data.msg);
				refreshImage();
			}
		}
	});
}
