var userName="";
$(function(){
	loadPage(1);
	$("#searchBtn").bind("click",function(){
		userName = $("#userName").val();
		loadPage(1);
	});
});

function loadPage(page){
	loadUser(page,userName);
}

function loadUser(page,keyWord) {
	userName = encodeURI(encodeURI(userName));
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/admin/loadUser.action?page=" + page+"&userName="+userName,// 请求的action路径
		success : function(data) {
			var list = data.result.list;
			var length = list.length;
			if(length>0){
				$("#user_list").empty();
				$("#pager").empty();
				for ( var i = 0; i < length; i++) {
					var user = list[i];
					new User(user).item.appendTo($("#user_list"));
				}
				if(data.result.pageTotal>1){
					new Pager(data.result.pageTotal,10,data.result.page).asHtml().appendTo($("#pager"));
				}else{
					$("#pager").hide();
				}
			}
		}
	});
}

function User(data){
	this.item = $("<div class='user_item'></div>");
	$("<div class='item_checkbox'><input type='checkBox' value='"+data.userId+"'></div>").appendTo(this.item);
	$("<div class='item_icon'>" +
			"<a href='"+global.realPath+"/user/"+data.userId+"' target='_blank'><img src='"+global.realPath+"/upload/"+data.userLittleIcon+"'></a>" +
			"</div>").appendTo(this.item);
	var info = $("<div class='info'></div>").appendTo(this.item);
	$("<div class='username'><a href='"+global.realPath+"/user/"+data.userId+"' target='_blank'>"+data.userName+"</a></div>").appendTo(info);
	var timeinfo = $("<div class='timeinfo'></div>").appendTo(info);
	$("<span>注册时间："+data.registerTime+"</span>").appendTo(timeinfo);
	$("<span class='previsttime'>上次登录时间："+data.previsitTime+"</span>").appendTo(timeinfo);
	$("<div class='clear'></div>").appendTo(this.item);
}