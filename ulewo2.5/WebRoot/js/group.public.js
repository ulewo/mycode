$(function(){
	$("#joinBtn").live("click",joinGroup);
	$("#existBtn").live("click",existGroup);
})

function joinGroup(){
	if(global.userId==""){
		alert("请先登录！");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	btnLoading($(this),"加入中<img src='"+global.realPath+"/images/load.gif' width='12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/group/joinGroup.action?gid="+global.gid,// 请求的action路径
		success : function(data) {
			if(data.result=="200"){
				$("#joinBtn").remove();
				if(data.memberType=='Y'){
					$("#memberCount").text(parseInt($("#memberCount").text())+1);
					$("#group_joinstatus").empty();
					$("<span class='joined'>已加入|<a href='javascript:void(0)' id='existBtn'>退出</a></span>").appendTo($("#group_joinstatus"));
				}else if(data.memberType=='N'){
					$("<span class='apply'>已申请加入,等待管理员的审核</span>").appendTo($("#group_joinstatus"));
				}
			}else{
				alert(data.msg);
			}
		}
	});
}

function existGroup(){
	if(global.userId==""){
		alert("请先登录！");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	btnLoading($(this),"退出<img src='"+global.realPath+"/images/load.gif' width='12'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/group/existGroup.action?gid="+global.gid,// 请求的action路径
		success : function(data) {
			$("#existBtn").remove();
			$("#joined").remove();
			if(data.result=="200"){
				$("#group_joinstatus").empty();
				$("#memberCount").text(parseInt($("#memberCount").text())-1);
				$("<a href='javascript:void(0)' class='btn' id='joinBtn'>+立即加入</a>").appendTo($("#group_joinstatus"));
			}else{
				alert(data.msg);
			}
		}
	});
}