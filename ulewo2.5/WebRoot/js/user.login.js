$(function(){
	$("#login_btn").bind("click",login);
	$("#remember_login").bind("click",function(){
		var check = $("#remember_check").attr("checked");
		if (check) {
			$("#remember_check").attr("checked", false);
		} else {
			$("#remember_check").attr("checked", true);
		}
	});
	
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	login();
	    }  
	};
});

// 登录
function login() {
	var obj = $("#login_btn");
	if(obj.attr("disable")=="disable"){
		return;
	}
	var account = $("#account").val().trim();
	var pwd = $("#pwd").val().trim();
	var code = $("#code").val().trim();
	
	var isAutoLogin = $("#remember_check").attr("checked");
	var autoLogin = "N";
	if (isAutoLogin) {
		autoLogin = "Y";
	}
	if(account==""){
		warm("show","账号不能为空");
		return;
	}
	if(pwd==""){
		warm("show","密码不能为空");
		return;
	}
	if(code==""){
		warm("show","验证码不能为空");
		return;
	}
	//清除错误信息
	warm("hide","");
	//登陆等待
	btnLoading(obj,"登录中<img src='images/load.gif' width=12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			account:account,
			password:pwd,
			checkCode:code,
			autoLogin:autoLogin,
			"time" : new Date()
		},
		url : global.realPath+"/user/login.do",// 请求的action路径
		success : function(data) {
			btnLoaded($("#login_btn"),"立即登录");
			if (data.code == "200") {// 登录成功
				document.location.href = redirectUrl;
			}else{
				warm("show",data.msg);
				refreshImage();
			}
		}
	});
}

