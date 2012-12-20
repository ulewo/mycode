	function showInfor(userId){
		//$("#userDiv").attr("class","userDivHover");
		$("#userContent").css("display","block");
		showUserInfor(userId);
	}
	function hidenUserCon(){
		$("#userContent").css('display','none')
	}

	function showUserInfor(userId){
		joinGroup(userId);
		myGroup(userId);
	}
	
		//创建群组
	function createGroup(userId){
		if(userId!=""){
			document.location.href="../manage/createGroup.jspx";
		}else{
			alert("请先登录");
			login();
		}
	}
	
	//我的群组
	function myGroup(userId){
		$("#umyGroup").html("<img src='../images/loading.gif'/>");
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"userId" : userId
			},
			url: '../userspace/myGroupsJosn.jspx',//请求的action路径
			success:function(data){
				var html = "<div class='groupLine'>";
				for(var i=0;i<data.length;i++){
					html = html+"<a href=../group/group.jspx?groupNumber="+data[i].groupNumber+">"+data[i].groupName+"</a>"
					if(i%2!=0){
							html = html+"</div>";
						if(i<data.length-1){
							html = html+"<div class='groupLine'>";
						}
					}
				}
				$("#umyGroup").html(html);
			}
		});
	}
	
	//参与的群组
	function joinGroup(userId){
		$("#ujoinGroup").html("<img src='../images/loading.gif'/>");
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"userId" : userId
			},
			url: '../userspace/joinGroupsJson.jspx',//请求的action路径
			success:function(data){
				var html = "<div class='groupLine'>";
				for(var i=0;i<data.length;i++){
					html = html+"<a href=../group/group.jspx?groupNumber="+data[i].groupNumber+">"+data[i].groupName+"</a>"
					if(i%2!=0){
							html = html+"</div>";
						if(i<data.length-1){
							html = html+"<div class='groupLine'>";
						}
					}
				}
				$("#ujoinGroup").html(html);
			}
		});
	}
	function login(){
		createFilter();
		var IETop = $( window ).scrollTop()+100;
        $("#login").css({"top":IETop});
        var left = (parseInt($(window).width())- parseInt($("#login").css("width").split("px")[0]))/2;
        $("#login").css({"left":left});
        $("#login").fadeIn("slow");
        $("#password").val("");
        $("#checkCode").val("");
	}
	
	function loginDo(){
	if($("#email").val()==""){
		alert("请输入登录邮箱");
		$("#subbut").attr("disabled",false);
		return;
	}else if($("#password").val()==""){
		alert("请输入密码");
		$("#subbut").attr("disabled",false);
		return;
	}else if($("#checkCode").val()==""){
		alert("验证码不能为空");
		return;
	}
	$.ajax({
			async : false,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"email" : $("#email").val(),
				"password":$("#password").val(),
				"checkCode":$("#checkCode").val(),
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

function refreshImage(){
	$("#codeImage").attr("src","../common/image.jsp?rand ="+Math.random());
}	
	
	function createFilter(){
		$("body").append("<div id='filterDiv' style='background:black;position:absolute;top:0px;left:0px;z-index:2;opacity:0.2'></div>");
		var xScroll = $(window).width();   
		var yScroll =(document.documentElement.scrollHeight>document.documentElement.clientHeight)?document.documentElement.scrollHeight:document.documentElement.clientHeight;
		$("#filterDiv").css({ "width": xScroll+"px", "height": yScroll+"px",'opacity':'0.2'});
	}
	
	function closeWind(){
		$("#login").fadeOut("slow");
		$("#filterDiv").remove();
	}