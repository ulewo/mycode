var curIndex;
var msgFlag = 0;
$(function(){
	$("#searchInput").bind("keydown",function(event){
		var ev = document.all ? window.event : event;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	search();
	    }  
	});
	
	var head_box_timer = "";
	var cura = "";
	
	$(".top_user_table a.h_m").bind("mouseover",function(){
			if(head_box_timer!=null){
				clearTimeout(head_box_timer);
			}
			$(".top_user_table a.h_m").removeClass("over");
			$(this).addClass("over");
			cura = $(this);
			var left = $(this).offset().left;
			var boxwidth = parseInt($("#head_box").css("width"));
			$("#head_box").show();
			$("#head_box").css({"left":left-boxwidth+57});
			$("#head_box").html("<img src='"+global.realPath+"/images/load.gif' style='margin-top:10px'>");
			var index = cura.attr("index");
			curIndex = index;
			if(index==0){
				getUserInfo(index);
			}else if(index==1){
				getWoWoInfo(index);
			}
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
				if(focusList!=null&&focusList.length>0&&data.result=="200"){
					var friend;
					for(var i=0,length=focusList.length;i<length;i++){
						friend = focusList[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/user/'+friend.friendUserId+'" target="_blank" title="'+friend.friendName+'"><img src="'+global.realPath+'/upload/'+friend.friendIcon+'"></a></div>').appendTo(box_focus_list);
					}
					$('<div class="clear"></div>').appendTo(box_focus_list);
				}else{
					$("<div class='noinfo'>你没有关注的人</div>").appendTo(box_focus_list);
				}
				$('<div class="box_fans_tit">粉丝</div>').appendTo("#head_box");
				var box_fans_list = $('<div id="box_fans_list"></div>').appendTo("#head_box");
				if(fansList!=null&&fansList.length>0&&data.result=="200"){
					var friend;
					for(var i=0,length=fansList.length;i<length;i++){
						friend = fansList[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/user/'+friend.friendUserId+'" target="_blank" title="'+friend.friendName+'"><img src="'+global.realPath+'/upload/'+friend.friendIcon+'"></a></div>').appendTo(box_fans_list);
					
					}
					$('<div class="clear"></div>').appendTo(box_fans_list);
				}else{
					$("<div class='noinfo'>你没有粉丝</div>").appendTo(box_fans_list);
				}
				$('<div class="box_set_tit">个人设置</div>').appendTo("#head_box");
				var box_set = $('<div class="box_set"></div>').appendTo("#head_box");
				$('<a href="'+global.realPath+'/user/'+global.userId+'" target="_blank">个人中心</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/main#userIcon" target="_blank">修改头像</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/main#userInfo" target="_blank">修改个人信息</a>').appendTo(box_set);
				$('<a href="'+global.realPath+'/manage/main#newBlog" target="_blank">写博客</a>').appendTo(box_set);
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
				if(joinedGroups!=null&&joinedGroups.length>0&&data.result=="200"){
					var group;
					for(var i=0,length=joinedGroups.length;i<length;i++){
						group = joinedGroups[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/group/'+group.gid+'" target="_blank" title="'+group.groupName+'"><img src="'+global.realPath+'/upload/'+group.groupIcon+'"></a></div>').appendTo(box_joinwowo_list);
					}
					$('<div class="clear"></div>').appendTo(box_joinwowo_list);
				}else{
					$("<div class='noinfo'>你没有加入窝窝</div>").appendTo(box_joinwowo_list);
				}
				$('<div class="box_createwowo_tit">创建的窝窝</div>').appendTo("#head_box");
				var box_createwowo_list = $('<div id="box_createwowo_list"></div>').appendTo("#head_box");
				if(createdGroups!=null&&createdGroups.length>0&&data.result=="200"){
					var group;
					for(var i=0,length=createdGroups.length;i<length;i++){
						group = createdGroups[i];
						$('<div class="box_item_img"><a href="'+global.realPath+'/group/'+group.gid+'" target="_blank" title="'+group.groupName+'"><img src="'+global.realPath+'/upload/'+group.groupIcon+'"></a></div>').appendTo(box_createwowo_list);
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
			if (data.code == "200") {
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
	var url = global.realPath + "/manage/noticeCount.action";
	if (global.userId != "") {
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : url,// 请求的action路径
			success : function(data) {
				if (parseInt(data.count) > 0) {
					$("<span id='msgcount'>"+data.count+"</span>").appendTo($("#noticCon"));
					setInterval(function() {
						if(msgFlag==0){
							$("#msgcount").css("background","#FF0000");
							msgFlag = 1;
						}else if(msgFlag==1){
							$("#msgcount").css("background","#FFFFFF");
							msgFlag = 0;
						}
					}, 500); 
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

function search(keyword){
	if(keyword==null){
		keyword = $("#searchInput").val().trim();
	}
	if(keyword==""){
		alert("请输入关键字");
		return;
	}else{
		if(keyword==""){
			alert("请输入关键字");
			return;
		}else{
			if(global.type==""){
				global.type = "T";
			}
			keyword = encodeURI(encodeURI(keyword)); 
			
			document.location.href=global.realPath+"/search?type="+ global.type+"&q="+keyword;
		}
	}
}
