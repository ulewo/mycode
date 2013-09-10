function reMarkP(userId,usericon,name,time){
	this.mark = $("<div class='mark_con'></div>");
	$("<div class='mark_icon'><a href='"+global.realPath+"/user/"+userId+"' target='_blank'><img src='"+ global.realPath + "/upload/"
			+usericon+"' width='60'/></a></div>").appendTo(this.mark);
	$("<div class='mark_time'><a href='"+global.realPath+"/user/"+userId+"' target='_blank'>"+name+"</a></div>").appendTo(this.mark);
	$("<div class='mark_time'>"+time+"</div>").appendTo(this.mark);
}

function loadMySin(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/myReMark.action",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.result=="success"){
				var size = data.list.length;
				if(size>0){
					for(var i=0;i<size;i++){
						new reMarkP(data.list[i].userId,data.list[i].userIcon,data.list[i].userName,data.list[i].markTime).mark.appendTo($("#remarkist"));
					}
					$("<div class='clear'></div>").appendTo($("#remarkist"));
				}else{
					$("<div class='noinfo'>你没有签到记录</div>").appendTo($("#remarkist"));
				}
			}else{
				$("<div class='noinfo'>"+data.msg+"</div>").appendTo($("#remarkist"));
			}
		}
	});
}

function loadAllSin(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/todayReMark.action",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.result=="success"){
				var size = data.list.length;
				if(size>0){
					for(var i=0;i<size;i++){
						new reMarkP(data.list[i].userId,data.list[i].userIcon,data.list[i].userName,data.list[i].markTime).mark.appendTo($("#remarkist"));
					}
					$("<div class='clear'></div>").appendTo($("#remarkist"));
				}else{
					$("<div class='noinfo'>今天没有签到记录，速度抢沙发</div>").appendTo($("#remarkist"));
				}
			}else{
				$("<div class='noinfo'>"+data.msg+"</div>").appendTo($("#remarkist"));
			}
		}
	});
}