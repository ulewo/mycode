$(function(){
	loadSysAvatar();
	$(".sys_icon").click(function(){
		$(".sys_icon").attr("class","sys_icon");
		if($("#sys_img").attr("checked")){
			var img = $(this).children().eq(0).attr("src");
			var imgVa = $(this).children().eq(0).attr("name"); 
			$(this).attr("class","sys_icon sys_icon2");
			$("#imgcon").attr("src",img);
			$("#groupicon").val(imgVa);
		}
		
	});
	
	$("#upload").change(function(){
		$("#imgcon").attr("src",$("#upload").val());
	});
	
	$(".tit_con").click(function(){
		$(".iconType").attr("checked",false);
		$(this).children().eq(0).children().eq(0).attr("checked",true);
		if($(this).children().eq(0).children().eq(0).attr("id")=="cus_img"){
			$("#iframupload").contents().find("#upload").attr("disabled",false);
			$(".sys_icon").attr("class","sys_icon");
			$(".sys_logo").attr("class","sys_logo");
		}else{
			$("#iframupload").contents().find("#upload").attr("disabled",true);
			$(".sys_logo").attr("class","sys_logo sys_logo2");
		}
	});

	$(".tit_con_user").click(function(){
		$(".iconType").attr("checked",false);
		$(this).children().eq(0).children().eq(0).attr("checked",true);
		if($(this).children().eq(0).children().eq(0).attr("id")=="cus_img"){
			$("#iframupload").contents().find("#upload").attr("disabled",false);
			$(".sys_icon").attr("class","sys_icon");
			$(".sys_logo").attr("class","sys_logo");
			$("#imgCut").css("display","inline-block");
			$("#sys_logo").css("display","none");
		}else{
			$("#iframupload").contents().find("#upload").attr("disabled",true);
			$(".sys_logo").attr("class","sys_logo sys_logo2");
			$("#imgCut").css("display","none");
			$("#sys_logo").css("display","inline-block");
		}
	});
	
})


function getImage(imageName){
	$("#imgcon").attr("src","../upload/"+imageName);
	$("#groupicon").val(imageName);
}

function loadSysAvatar(){
	for(var i=1;i<=24;i++){
		$("<div class='sys_icon'><img src='../upload/sysicon/"+i+".gif' width='60'' name='sysicon/"+i+".gif'></div>").appendTo($("#sys_logo"))
	}
	$("<div class='clear'></div>").appendTo($("#sys_logo"))
}

function saveAvatar(gid){
	$("#load").css({ "display": "block"});
	$("#save").css({ "display": "none"});
	var groupicon = $("#groupicon").val();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"gid" : gid,
			"groupIcon":groupicon,
			"date":new Date()
		},
		url : 'updateLogo.jspx',// 请求的action路径
		success : function(data) {
			if(data.result=="ok"){
				art.dialog.tips('保存成功');
			}else{
				art.dialog.tips('保存失败');
			}
			$("#load").css({ "display": "none"});
			$("#save").css({ "display": "block"});
		}
	});
}
