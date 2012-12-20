$(function(){
	$(".atr").mouseover(function(){
		$(this).css({background:"#F7F7F7"});
	});
	
	$(".atr").mouseout(function(){
		$(this).css({background:"#FFFFFF"});
	});
});

//加入群组
function addGroup(userId,gid){
	if(userId==""){
		art.dialog.open('../user/login.jsp',
		    {title: '用户登录', width: 500, height: 300});
	}else{
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"gid" : gid,
				"time":new Date()
			},
			url: 'addGroup.jspx',//请求的action路径
			success:function(data){
				if(data.msg=="nologin"){
					art.dialog.tips("请先登录!");
				}
				if(data.msg=="isMemeber"){
					art.dialog.tips("你已经是成员了!");
				}else if(data.msg=="addOk"){
					art.dialog.tips("申请成功!");
				}else if(data.msg=="addNeedCheck"){
					art.dialog.tips("申请成功，需要等待群主审核");
				}else {
					art.dialog.tips("出现错误，稍后重试");
				}
			}
		});
	}
}

//添加文章
var addArticle = function(userId,gid){
	if(userId!=""){
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"gid" : gid,
				"time":new Date()
			},
			url: 'checkUserPerm.jspx',//请求的action路径
			success:function(data){
				if(data.msg=="havePerm"){
					document.location.href="addArticle.jspx?gid="+gid;
				}else if(data.msg=="nologin"){
					art.dialog.open('../user/login.jsp',{title: '用户登录', width: 500, height: 300});
				}else if(data.msg=="noPerm"){
					art.dialog.tips("只有成员才能发帖哦");
				}else{
					art.dialog.tips("出现错误，稍后重试");
				}
			}
		});
	}else{
		art.dialog.open('../user/login.jsp',
		    {title: '用户登录', width: 500, height: 300});
	}
}

//添加回复
var addReArticle = function(userId,id,groupNumber){
	if(userId!=""){
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"groupNumber" : groupNumber,
				"time":new Date()
			},
			url: 'checkUserPerm.jspx',//请求的action路径
			success:function(data){
				if(data[0].msg=="havePerm"){
					document.location.href="reArticle.jspx?id="+id;
				}else{
					alert("请先加入该群组");
				}
			}
		});
	}else{
		alert("请先登录");
		login();
	}
}
//引用
var quoted = function(userId,id,groupNumber){
	if(userId!=""){
		$.ajax({
			async : true,
			cache:false,
			type: 'POST',
			dataType : "json",
			data : {
				"groupNumber" : groupNumber,
				"time":new Date()
			},
			url: 'checkUserPerm.jspx',//请求的action路径
			success:function(data){
				if(data[0].msg=="havePerm"){
					document.location.href="quoted.jspx?id="+id;
				}else{
					alert("请先加入该群组");
				}
			}
		});
	}else{
		alert("请先登录");
		document.location.href = "../login.jsp";
	}
}

function viewArticle(){
		$("#viewcon").html($("#content").val());
		createViewFilter();
		var IETop = $( window ).scrollTop()+100;
        $("#view").css({"top":IETop});
        var left = (parseInt($(window).width())- parseInt($("#view").css("width").split("px")[0]))/2;
        $("#view").css({"left":left});
        $("#view").fadeIn("slow");
}

function createViewFilter(){
		$("body").append("<div id='filterViewDiv' style='background:black;position:absolute;top:0px;left:0px;z-index:2;opacity:0.2'></div>");
		var xScroll = $(window).width(); 
		var yScroll =(document.documentElement.scrollHeight>document.documentElement.clientHeight)?document.documentElement.scrollHeight:document.documentElement.clientHeight;
		$("#filterViewDiv").css({ "width": xScroll+"px", "height": yScroll+"px",'opacity':'0.2'});
}

function closeView(){
		$("#view").fadeOut("slow");
		$("#filterViewDiv").remove();
	}