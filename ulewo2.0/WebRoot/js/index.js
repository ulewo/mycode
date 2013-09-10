$(function(){
	lazyLoadImage("article_pic");
	$("#talkBtn").bind("click",saveTalk);
	loadTalk();
	$("#talkcontent").bind("focus",function(){
		if($(this).val()=="今天你吐槽了吗？"){
			$(this).val("");
			$(this).css({"color":"#494949"});
		}
	});
	$("#talkcontent").bind("blur",function(){
		if($(this).val()==""){
			$(this).val("今天你吐槽了吗？");
			$(this).css({"color":"#A9A9A9"});
		}
	});
	loadReMark();
	
	//设置今天的日期
	setTodayInfo();
})

function setTodayInfo(){
	$("#tody_time").html(YYMMDD()+"("+solarDay2()+")");
	$("#tody_festival").html(weekday()+"&nbsp;&nbsp;"+solarDay3());
}

function saveTalk(){
	if(global.userId==""){
		alert("请先登录");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	var content = $("#talkcontent").val().trim();
	if(content==""||content=="今天你吐槽了吗？"){
		warm("show","吐槽内容不能为空");
		return;
	}
	if(content.length>250){
		warm("show","吐槽内容不能超过250字符");
		return;
	}
	warm("hide","");
	btnLoading($(this),"发布中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			content:content,
			imgUrl:$("#imgUrl").val()
		},
		url : global.realPath+"/user/saveTalk.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"发布");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				info("发布成功");
				$(".noinfo").remove();
				if($(".talkitem").length>0){
					$(".talkitem").eq(0).before(new TalkItem(data.talk).item);
				}else{
					new TalkItem(data.talk).item.appendTo($("#talklist"));
				}
				$("#talkcontent").val("");
				deleteImg();
				tipsInfo("1分已到碗里");
			}
		}
	});
}

function loadTalk(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadLatestTalk",// 请求的action路径
		success : function(data) {
			if(data.result=="success"){
				var length = data.list.length;
				if(length>0){
					for(var i=0;i<length;i++){
						new TalkItem(data.list[i]).item.appendTo($("#talklist"));
					}
				}
			}
		}
	});
}


function loadReMark(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/reMarkInfo",// 请求的action路径
		success : function(data) {
			if(data.result=="success"){
				haveReMark(data.isReMark,data.myReMark,data.todayCount);
			}
		}
	});
}

var haveReMarked = false;
function haveReMark(isremark,mycount,allcount){
	this.remark = $("<div class='remark'></div>").appendTo($("#remarkcon"));
	if(isremark){
		$("<div class='remark_siged'></div>").appendTo(this.remark);
	}else{
		$("<div class='remark_sig'></div>").appendTo(this.remark).click(function(){
			var curobj = $(this);
			if(global.userId==""){
				alert("请先登录");
				return;
			}
			if(haveReMarked){
				return;
			}
			$.ajax({
				async : true,
				cache : false,
				type : 'GET',
				dataType : "json",
				url : global.realPath+"/reMark.action",// 请求的action路径
				success : function(data) {
					if(data.result=="success"){
						curobj.attr("class","remark_siged");
						var count_all = parseInt($("#count_all").html());
						var count_my  = parseInt($("#count_my").html());
						$("#count_all").html("<a href='allsgin.action' target='_blank'>"+(count_all+1)+"</a>");
						$("#count_my").html("<a href='mysgin.action' target='_blank'>"+(count_my+1)+"</a>");
						if(data.reward=="reward"){
							tipsInfo("连续7天签到送20积分");
						}else{
							tipsInfo("2分已到碗里");
						}
						haveReMarked = true;
					}else{
						alert(data.msg);
					}
				}
			});
		});
	}
	var remark_right_info = $("<div class='remark_right_info'>").appendTo(this.remark);
	var remark_info = $("<div class='remark_info'></div>").appendTo(remark_right_info);
	if(isremark){
		$("<div class='count_all'><a href='allsgin.action' target='_blank' title='今日签到'>"+allcount+"</a></div>").appendTo(remark_info);
		$("<div class='count_my'><a href='mysgin.action' target='_blank' title='我的签到'>"+mycount+"</a></div>").appendTo(remark_info);
	}else{
		$("<div class='count_all' title='今日签到' id='count_all'>"+allcount+"</div>").appendTo(remark_info);
		$("<div class='count_my' title='我的签到' id='count_my'>"+mycount+"</div>").appendTo(remark_info);
	}
}

