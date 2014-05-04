function reMarkP(user){
	this.mark = $("<div class='mark_con_user'></div>");
	$("<div class='mark_icon_user'><a href='"+global.realPath+"/user/"+user.userId+"' target='_blank'><img src='"+ global.realPath + "/upload/"
			+user.userIcon+"' width='60'/></a></div>").appendTo(this.mark);
	$("<div class='mark_time_user'><a href='"+global.realPath+"/user/"+user.userId+"' target='_blank'>"+user.userName+"</a></div>").appendTo(this.mark);
	$("<div class='mark_time_user'>积分："+user.mark+"</div>").appendTo(this.mark);
	$("<div class='mark_time_user'>加入时间："+user.showRegisterTime+"</div>").appendTo(this.mark);
}

function loadAllUser(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadUsersByMarks",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.result=="200"){
				var size = data.list.length;
				if(size>0){
					for(var i=0;i<size;i++){
						new reMarkP(data.list[i]).mark.appendTo($("#remarkist"));
					}
					$("<div class='clear'></div>").appendTo($("#remarkist"));
				}
			}
		}
	});
}