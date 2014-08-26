var toolbar = {};
$(function(){
	$(document).click(function() {
		toolbar.closeToolbarCon();
	});
	$(".tool-bar-item").bind("click",function(){
		if($(this).attr("id")=="gototop"){
			return;
		}
		//获取信息
		toolbar.getInfo($(this));
		var width = "300px";
		if($(this).attr("id")=="tool-post-topic"&&global.userId!=""){
			width = "900px";
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
		$("#tool-bar-con").hide();
	});
});

//获取信息
toolbar.getInfo = function(curObj){
	$(".tool-bar-con-sub").hide();
	if(global.userId==""){
		$("#bar-con-loading").hide();
		$("<a href=''>登陆</a>").appendTo(curObj);
		$("<a href=''>注册</a>").appendTo(curObj);
		return;
	}
	var barCon = $("#"+curObj.attr("id")+"-con");
	barCon.show();
	if(curObj.attr("id")=="tool-user-info"){
		if(barCon.html()==""){
			toolbar.getUserInfo(barCon);
		}
	}else if(curObj.attr("id")=="tool-user"){
		if(barCon.html()==""){
			
		}
	}else if(curObj.attr("id")=="tool-post-topic"){
		if(barCon.html()==""){
			$("#bar-con-loading").show();
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "html",
				url : global.realPath+"/goFastPostTopic.action",// 请求的action路径
				success : function(data) {
					$("#bar-con-loading").hide();
					barCon.html(data);
				}
			});
		}
	}else if(curObj.attr("id")=="tool-group"){
		if(barCon.html()==""){
			toolbar.getUserInfo(barCon);
		}
	}else if(curObj.attr("id")=="tool-notice"){
		if(barCon.html()==""){
			
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
		url : global.realPath + "/user/loadFansFocus.action",// 请求的action路径
		success : function(data) {
			$("#bar-con-loading").hide();
			var focusList = data.focusList;
			var fansList = data.fansList;
			barCon.empty();
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
			$('<a href="'+global.realPath+'/user/'+global.userId+'" target="_blank">个人中心</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#userIcon" target="_blank">修改头像</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#userInfo" target="_blank">修改个人信息</a>').appendTo(box_set);
			$('<a href="'+global.realPath+'/manage/main#newBlog" target="_blank">写博客</a>').appendTo(box_set);
		}
	});
}

toolbar.getUserInfo = function(barCon){
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

toolbar.closeToolbarCon = function(){
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

$(window).bind("scroll",function(){
    var scollPos =  toolbar.scollPostion();
    if(scollPos.top>200){
   	 $("#gototop").show();
    }else{
   	 $("#gototop").hide();
    }
    
});

