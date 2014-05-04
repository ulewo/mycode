$(function(){
	$("#rest_btn").bind("click",restpwd);
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	restpwd();
	    }  
	};
});

// 重置密码
function restpwd() {
	var obj = $("#rest_btn");
	if(obj.attr("disable")=="disable"){
		return;
	}
	var account = $("#account").val().trim();
	var code = $("#code").val().trim();
	
	if(account==""){
		warm("show","账号不能为空");
		return;
	}
	if(code==""){
		warm("show","验证码不能为空");
		return;
	}
	//清除错误信息
	warm("hide","");
	//登陆等待
	btnLoading(obj,"发送重置密码连接中<img src='images/load.gif' width=12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			account:account,
			code:code,
			"time" : new Date()
		},
		url : global.realPath+"/user/sendRestPwd.do",// 请求的action路径
		success : function(data) {
			btnLoaded(obj,"给我发送重置密码连接");
			if (data.result == "200") {// 
				info("重置密码连接已经发送到"+data.address,false);
			}else{
				warm("show",data.msg);
				refreshImage();
			}
		}
	});
}
