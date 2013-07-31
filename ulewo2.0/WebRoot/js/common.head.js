var curIndex = 0;
$(function(){
	$("#searchInput").bind("keydown",function(event){
		var ev = document.all ? window.event : event;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	search();
	    }  
	});
	
	var head_box_timer = "";
	var cura = "";
	$(".head_con ul.head_right li a.h_m").each(function(index){
		$(this).bind("mouseover",function(){
			if(head_box_timer!=null){
				clearTimeout(head_box_timer);
			}
			$(".head_con ul.head_right li a").removeClass("over");
			$(this).addClass("over");
			cura = $(this);
			var left = $(this).offset().left;
			var boxwidth = parseInt($("#head_box").css("width"));
			$("#head_box").show();
			$("#head_box").css({"left":left-boxwidth+48});
			$("#head_box").html("<img src='"+global.realPath+"/images/load.gif' style='margin-top:10px'>");
			curIndex = index;
			if(index==0){
				getWoWoInfo(index);
			}else if(index==1){
				getNoticeList(index);
			}else if(index==2){
				getUserInfo(index);
			}
		});
	}).bind("mouseout",function(){
		var obj = $(this);
		head_box_timer = setTimeout(function(){
			$("#head_box").hide();
			obj.removeClass("over");
		},10);
		
	});
	$("#head_box").bind("mouseover",function(){
		clearTimeout(head_box_timer);
		$(this).show();
		cura.addClass("over");
	}).bind("mouseout",function(){
			cura.removeClass("over");
			$("#head_box").hide();
	});
	
	loadNotice();
	
});

//获取用户信息
function getUserInfo(myIndex){
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/user/loadFansFocus.action",// 请求的action路径
		success : function(data) {
			if(myIndex==curIndex){
				var focusList = data.focusList;
				var fansList = data.fansList;
				$("#head_box").empty();
				$('<div class="box_focus_tit">关注</div>').appendTo("#head_box");
				var box_focus_list = $('<div id="box_focus_list"></div>').appendTo("#head_box");
				if(focusList.length>0){
					var friend;
					for(var i=0,length=focusList.length;i<length;i++){
						friend = focusList[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/user/'+friend.friendId+'" target="_blank" title="'+friend.friendName+'"><img src="'+global.realPath+'/upload/'+friend.friendIcon+'"></a></div>').appendTo(box_focus_list);
					}
					$('<div class="clear"></div>').appendTo(box_focus_list);
				}else{
					$("<div class='noinfo'>你没有关注的人</div>").appendTo(box_focus_list);
				}
				$('<div class="box_fans_tit">粉丝</div>').appendTo("#head_box");
				var box_fans_list = $('<div id="box_fans_list"></div>').appendTo("#head_box");
				
				if(fansList.length>0){
					var friend;
					for(var i=0,length=fansList.length;i<length;i++){
						friend = fansList[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/user/'+friend.friendId+'" target="_blank" title="'+friend.friendName+'"><img src="'+global.realPath+'/upload/'+friend.friendIcon+'"></a></div>').appendTo(box_fans_list);
					
					}
					$('<div class="clear"></div>').appendTo(box_fans_list);
				}else{
					$("<div class='noinfo'>你没有粉丝</div>").appendTo(box_fans_list);
				}
				$('<div class="box_set_tit">个人设置</div>').appendTo("#head_box");
				var box_set = $('<div class="box_set"></div>').appendTo("#head_box");
				$('<a href="'+global.realPath+'/user/'+global.userId+'" target="_blank">个人中心</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/user_icon" target="_blank">修改头像</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/userinfo" target="_blank">修改个人信息</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/new_blog" target="_blank">写博客</a>').appendTo(box_set);
			}
		}
	});
}


//获取消息
function getNoticeList(myIndex){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/user/loadNotice.action",// 请求的action路径
		success : function(data) {
			if(myIndex==curIndex){
				$("#head_box").empty();
				var notice_box = $('<div class="notice_box"></div>').appendTo("#head_box");
				var noticelist = data.list;
				if(noticelist.length>0){
					var notice;
					for(var i=0,length=noticelist.length;i<length;i++){
						notice = noticelist[i];
						$("<a href='"+global.realPath+"/manage/noticeDetail?id="+notice.id+"' title="+notice.content+">"+notice.content+"</a>").appendTo(notice_box);
					}
				}else{
					$("<div class='noinfo'>没有消息</div>").appendTo(notice_box);
				}
			}
			
		}
	});
}
	
//获取窝窝信息
function getWoWoInfo(myIndex){
	
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/user/loadGroup.action",// 请求的action路径
		success : function(data) {
			if(myIndex==curIndex){
				var createdGroups = data.createdGroups;
				var joinedGroups = data.joinedGroups;
				$("#head_box").empty();
				$('<div class="box_joinwowo_tit">加入的窝窝</div>').appendTo("#head_box");
				var box_joinwowo_list = $('<div id="box_joinwowo_list"></div>').appendTo("#head_box");
				if(joinedGroups.length>0){
					var group;
					for(var i=0,length=joinedGroups.length;i<length;i++){
						group = joinedGroups[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/group/'+group.id+'" target="_blank" title="'+group.groupName+'"><img src="'+global.realPath+'/upload/'+group.groupIcon+'"></a></div>').appendTo(box_joinwowo_list);
					}
					$('<div class="clear"></div>').appendTo(box_joinwowo_list);
				}else{
					$("<div class='noinfo'>你没有加入窝窝</div>").appendTo(box_joinwowo_list);
				}
				$('<div class="box_createwowo_tit">创建的窝窝</div>').appendTo("#head_box");
				var box_createwowo_list = $('<div id="box_createwowo_list"></div>').appendTo("#head_box");
				if(createdGroups.length>0){
					var group;
					for(var i=0,length=createdGroups.length;i<length;i++){
						group = createdGroups[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/group/'+group.id+'" target="_blank" title="'+group.groupName+'"><img src="'+global.realPath+'/upload/'+group.groupIcon+'"></a></div>').appendTo(box_createwowo_list);
					}
					$('<div class="clear"></div>').appendTo(box_createwowo_list);
				}else{
					$("<div class='noinfo'>你没有创建窝窝</div>").appendTo(box_createwowo_list);
				}
				
				$("<a href='javascript:createWoWo()' id='box_btn_create_wowo'>创建窝窝</a>").appendTo("#head_box");
			}
		}
	});
}

// 登录跳转
function goto_login() {
	var redirectUrl = window.location.href;
	if (redirectUrl.indexOf("redirectUrl") == -1) {
		document.location.href =global.realPath+"/login?redirectUrl=" + redirectUrl;
	} else {
		document.location.href = window.location.href;
	}
}

function goto_register() {
	document.location.href = global.realPath+"/register";
}

function logout() {
	var redirectUrl = global.realPath;
	var url =  global.realPath + "/user/logout";
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"time" : new Date()
		},
		url : url,// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				document.location.href = redirectUrl;
			}
		}
	});
}

/**刷新验证码**/
function refreshImage() {
	$("#codeImage").attr("src", global.realPath+"/common/image.jsp?rand =" + Math.random());
}

function loadNotice() {
	var showUrl = global.realPath + "/manage/notice";
	var url = global.realPath + "/manage/noticeCount";
	if (global.userId != "") {
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : url,// 请求的action路径
			success : function(data) {
				if (parseInt(data.count) > 0) {
					$("#notice").show();
					$("#notice").text(data.count);
				}
			}
		});
	}
}

function createWoWo(){
	if (global.userId == "") {
		alert("请先登录");
		return;
	}else{
		document.location.href=global.realPath+"/createWoWo";
	}
}

function search(){
	var keyword = $("#searchInput").val().trim();
	if(keyword==""){
		alert("请输入关键字");
		return;
	}else{
		keyword = encodeURI(encodeURI(keyword)); 
		document.location.href=global.realPath+"/search?q="+keyword;
	}
}
