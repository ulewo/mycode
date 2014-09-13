var toolbar = {};
toolbar.msgFlag = 0
$(function(){
	
	$(".tool-bar").css({"top":($(window).height()-300)/2,"display":"block"});
	
	$(document).click(function() {
		if($("#tool-bar-con").css("width")!="900px"){
			toolbar.closeToolbarCon();
		}
	});
	$(".tool-bar-item").bind("click",function(){
		if($(this).attr("id")=="gototop"){
			return;
		}
		//获取信息
		toolbar.getInfo($(this));
		var width = "400px";
		if($(this).attr("id")=="tool-post-topic"&&global.userId!=""){
			width = "900px";
		}
		if($(this).attr("class").indexOf("cur-bar-item")!=-1){
			toolbar.closeToolbarCon();
			return;
		}
		$(".tool-bar-item").removeClass("cur-bar-item");
		$(this).addClass("cur-bar-item");
		$("#tool-bar-con").css({"width":width});
		$("#tool-bar-con").show();
	});
	$("#tool-bar-con").click(function(event) {
		event.stopPropagation();
	});
	$(".tool-bar").bind("click",function(event){
		event.stopPropagation();
	});
	$(".tool-bar-item").hover(function(){
		$(this).addClass("tool-bar-item-hover");
	}, function() {
		$(this).removeClass("tool-bar-item-hover");
	});
	$("#bar-con-close a").click(function(){
		toolbar.closeToolbarCon();
	});
	toolbar.loadNotice();
});
toolbar.loadNotice = function() {
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
					$("#notice-info-count").text(data.count);
					$("#notice-info-count").show();
					setInterval(function() {
						if(toolbar.msgFlag==0){
							$("#notice-info-count").hide();
							toolbar.msgFlag = 1;
						}else if(toolbar.msgFlag==1){
							$("#notice-info-count").show();
							toolbar.msgFlag = 0;
						}
					}, 500); 
				}
			}
		});
	}
}

//获取信息
toolbar.getInfo = function(curObj){
	$(".tool-bar-con-sub").hide();
	var barCon = $("#"+curObj.attr("id")+"-con");
	barCon.show();
	if(global.userId==""){
		return;
	}
	if(curObj.attr("id")=="tool-user"){
		if(barCon.html()==""){
			toolbar.getUserInfo(barCon);
		}
	}else if(curObj.attr("id")=="tool-post-topic"){
		if(window.location.href.indexOf("group")!='-1'){
			cancelAdd();
		}
		$("#fastpost").attr("src",global.realPath +"/goFastPostTopic.action?"+new Date());
		//fastPost.showAddForm();
		//fastPost.loadGroup();
		$("#bar-con-loading").hide();
	}else if(curObj.attr("id")=="tool-group"){
		if(barCon.html()==""){
			toolbar.getGroupInfo(barCon);
		}
	}else if(curObj.attr("id")=="tool-notice"){
		if(barCon.html()==""){
			toolbar.getNotice(barCon);
		}
	}
}

toolbar.getUserInfo = function(barCon){
	$("#bar-con-loading").show();
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/user/loadUserInfo.action",// 请求的action路径
		success : function(data) {
			$("#bar-con-loading").hide();
			var focusList = data.focusList;
			var fansList = data.fansList;
			var info  = data.userVo;
			barCon.empty();
			var bar_user_info = $("<div class='bar-user-info'></div>").appendTo(barCon);
			$("<div class='bar-user-info-image'><img src='"+global.realPath+"/upload/"+info.userIcon+"'></div>").appendTo(bar_user_info);
			var bar_user_info_con = $("<div class='bar-user-info-con'></div>").appendTo(bar_user_info);
			$("<div class='clear'></div>").appendTo(bar_user_info);
			$("<div class='bar-user-info-d'>关注："+info.focusCount+"</div>").appendTo(bar_user_info_con);
			$("<div class='bar-user-info-d'>粉丝："+info.fansCount+"</div>").appendTo(bar_user_info_con);
			$("<div class='bar-user-info-d'>积分："+info.mark+"</div>").appendTo(bar_user_info_con);
			
			$('<div class="box_focus_tit">关注</div>').appendTo(barCon);
			var box_focus_list = $('<div id="box_focus_list"></div>').appendTo(barCon);
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
			$('<div class="box_fans_tit">粉丝</div>').appendTo(barCon);
			var box_fans_list = $('<div id="box_fans_list"></div>').appendTo(barCon);
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
			$('<div class="box_set_tit">个人设置</div>').appendTo(barCon);
			var box_set = $('<div class="box_set"></div>').appendTo(barCon);
			$('<a href="'+global.realPath+'/user/'+global.userId+'">个人中心</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#userIcon">修改头像</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#userInfo">修改个人信息</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#newBlog">写博客</a>').appendTo(box_set);
		}
	});
}

toolbar.getGroupInfo = function(barCon){
	$("#bar-con-loading").hide();
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/user/loadGroup.action",// 请求的action路径
		success : function(data) {
			$("#bar-con-loading").hide();
				var createdGroups = data.createdGroups;
				var joinedGroups = data.joinedGroups;
				$(barCon).empty();
				$('<div class="box_joinwowo_tit">加入的窝窝</div>').appendTo(barCon);
				var box_joinwowo_list = $('<div id="box_joinwowo_list"></div>').appendTo(barCon);
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
				$('<div class="box_createwowo_tit">创建的窝窝</div>').appendTo(barCon);
				var box_createwowo_list = $('<div id="box_createwowo_list"></div>').appendTo(barCon);
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
				$("<a href='javascript:createWoWo()' id='box_btn_create_wowo'>创建窝窝</a>").appendTo(barCon);
		}
	});
}


toolbar.getNotice = function(barCon){
	$("#bar-con-loading").hide();
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/manage/notice.action?status=0",// 请求的action路径
		success : function(data) {
			$("#bar-con-loading").hide();
			var rows = data.rows;
			if(rows.length>0){
				for(var i=0,_len=rows.length;i<_len;i++){
					$("<div class='notice-item'><a href='"+global.realPath+"/manage/readNotice.action?id="+rows[i].id+"'>"+rows[i].title+"</a></div>").appendTo(barCon);
				}
				$("<a href='"+global.realPath+"/manage/main#notice' class='bigbtn'>进入消息中心</a>").appendTo(barCon);
			}else{
				$("<div class='noinfo' style='margin-top:200px;'>没有发现消息</div>").appendTo(barCon);
			}
			
		}
	});
}

toolbar.closeToolbarCon = function(){
	$('#tool-bar-con').hide();
	$("#tool-bar-con").css({"width":"0px"});
	$(".tool-bar-item").removeClass("cur-bar-item");
}

function closeToolbarCon(){
	$('#tool-bar-con').hide();
	$("#tool-bar-con").css({"width":"0px"});
	$(".tool-bar-item").removeClass("cur-bar-item");
}

toolbar.scollPostion = function(){//滚动条位置
	   var t, l, w, h;
	   if (document.documentElement && document.documentElement.scrollTop) {
	       t = document.documentElement.scrollTop;
	       l = document.documentElement.scrollLeft;
	       w = document.documentElement.scrollWidth;
	       h = document.documentElement.scrollHeight;
	   } else if (document.body) {
	       t = document.body.scrollTop;
	       l = document.body.scrollLeft;
	       w = document.body.scrollWidth;
	       h = document.body.scrollHeight;
	   }
	   return { top: t, left: l, width: w, height: h };
	};

	/*
$(window).bind("scroll",function(){
    var scollPos =  toolbar.scollPostion();
    if(scollPos.top>200){
   	 $("#gototop").show();
    }else{
   	 $("#gototop").hide();
    }
    
});

*/