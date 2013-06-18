$(function(){
	$("#registerBtn").bind("click",register);
})

function register(){
	if($(this).attr("disable")=="disable"){
		return;
	}
	var checkEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;// 验证email
	var checkUserName = /^[\w\u4e00-\u9fa5]+$/; //只能是数字，字母，下划线，中文。
	var checkPassWord = /^[0-9a-zA-Z]+$/;         //只能是数字，字母
	
	var userName =  $("#userName").val().trim();
	var email = $("#email").val().trim();
	var pwd = $("#pwd").val().trim();
	var repwd = $("#repwd").val().trim();
	var code = $("#code").val().trim();
	
	if(userName==""){
		warm("show","用户名不能为空");
		return;
	}
	if(userName.realLength()<1||userName.realLength()>20||!checkUserName.test(userName.trim())){
		warm("show","用户名不符合规范");
		return;
	}
	if(email==""){
		warm("show","邮箱不能为空");
		return;
	}
	if(email.realLength()<1||email.realLength()>50||!checkEmail.test(email.trim())){
		warm("show","邮箱不符合规范");
		return;
	}
	if(pwd==""){
		warm("show","密码不能为空");
		return;
	}
	if(pwd.length<6||pwd.length>16||!checkPassWord.test(pwd.trim())){
		warm("show","密码不符合规范");
		return;
	}
	if(repwd!=pwd){
		warm("show","两次输入的密码不一致");
		return;
	}
	if(code==""){
		warm("show","验证码不能为空");
		return;
	}
	//清除错误信息
	warm("hide","");
	//登陆等待
	btnLoading($(this),"注册中<img src='images/load.gif' width=12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#registerForm").serialize(),
		url : global.realPath+"/user/register.do",// 请求的action路径
		success : function(data) {
			btnLoaded($("#registerBtn"),"注册新用户");
			if (data.result == "success") {// 注册成功
				info("注册成功");
				setTimeout(function(){
					document.location.href = global.realPath+"/user/"+data.userId;
				},2000);
			}else{
				warm("show",data.message);
				refreshImage();
			}
		}
	});
}
