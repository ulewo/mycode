$(function(){
	rollImage();
});
function rollImage(){
	var len  = $(".rightcon .titinf").length;
		var index = 0;
		var adTimer;
		$(".rightcon .titinf").mouseover(function(){
			index = $(".rightcon .titinf").index(this);
			showImage(index);
		}).eq(0).mouseover();

		$(".rightcon").hover(function(){
		  clearInterval(adTimer);
		},function(){
			 adTimer = setInterval(function(){
				index++;
				if(index==len){index=0;}
				showImage(index);
			  } , 3000);
		}).trigger("mouseleave");
}
function showImage(index){
	    $("#img").css("display","none");
	    $("#url").attr("href","group/post.jspx?id="+imagearry[index].split("|")[1]);
		$("#img").attr("src","upload/"+imagearry[index].split("|")[0]);
        $("#img").fadeIn("slow");
       $(".rightcon").stop(true,false).animate({backgroundPosition: '0px '+50*index+'px'},500);
}

function indexlogin(){
	if($("#iemail").val()==""){
		alert("请输入登录邮箱");
		return;
	}else if($("#ipassword").val()==""){
		alert("请输入密码");
		return;
	}else if($("#icheckCode").val()==""){
		alert("验证码不能为空");
		return;
	}
	$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"email" : $("#iemail").val(),
				"password":$("#ipassword").val(),
				"checkCode":$("#icheckCode").val(),
				"time":new Date()
			},
			url: 'login.jspx',//请求的action路径
			success:function(data){
				if(data[0].msg=="success"){
					alert("登陆成功");
					document.location.reload();
				}else if(data[0].msg=="error"){
					alert("用户名或密码错误");
					refreshImage();
					return 
				}else if(data[0].msg=="checkcodeerror"){
					alert("验证码错误");
					refreshImage();
					return 
				}else{
					alert("出现异常，请稍候再试");
					refreshImage();
					return 
				}
			}
		});
}
function refreshindexImage(){
	$("#indexcodeImage").attr("src","common/image.jsp?rand ="+Math.random());
}
