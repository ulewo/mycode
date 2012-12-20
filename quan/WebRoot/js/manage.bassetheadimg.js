$(function(){
	loadSysAvatar();
	$(".sys_head_img").click(function(){
		$(".sys_head_img").attr("class","sys_head_img");
		if($("#sys_img").attr("checked")){
			var img = $(this).children().eq(0).attr("src");
			var imgVa = $(this).children().eq(0).attr("value"); 
			$(this).attr("class","sys_head_img sys_head_img2");
			$("#headImg").attr("src",img);
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
			$(".sys_head_img").attr("class","sys_head_img");
			$(".sys_logo").attr("class","sys_logo");
		}else{
			$("#iframupload").contents().find("#upload").attr("disabled",true);
			$(".sys_logo").attr("class","sys_logo sys_logo2");
		}
	});
})


function getheadIconImage(imageName){
	$("#headImg").attr("src","../upload/"+imageName);
	$("#groupicon").val(imageName);
}

function loadSysAvatar(){
	for(var i=1;i<=5;i++){
		$("<div class='sys_head_img'><img src='../upload/syshead/"+i+".jpg'width='700' value='syshead/"+i+".jpg'></div>").appendTo($("#sys_logo"))
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
			"groupHeadIcon":groupicon,
			"date":new Date()
		},
		url : 'updateHeadImag.jspx',// 请求的action路径
		success : function(data) {
			if(data.result=="ok"){
				art.dialog.tips('保存成功');
			}else{
				art.dialog.tips('保存失败');
			}
			$("#load").css({ "display": "none"});
			$("#save").css({ "display": "block"});
		   setTimeout(hiddenInfo,1500)
		}
	});
}

function hiddenInfo(){
	$("#head_con_info").html("");
}
