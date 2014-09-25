$(function(){
	lazyLoadImage("article_pic");
	setEmotions();
	$("#talkBtn").bind("click",saveBlast);
	//loadBlast();
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
	
	loadSignInInfo();
	//设置今天的日期
	setTodayInfo();
	
})

function scollPostion(){//滚动条位置
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
	}

$(window).bind("scroll",function(){
    var scollPos =  scollPostion();
    var height = 325;
    if(scollPos.top<=height){
    	$("#keepshow").css({"position":"static"});
    }else{
   	    $("#keepshow").css({"position":"fixed","top":"-8px","border":"0px"});
    }
});


function setEmotions(){
	var item_contents = $(".item_content");
	var item;
	var content;
	for(var i=0,_len=item_contents.length;i<_len;i++){
		item = item_contents.eq(i);
		content = item.html();
		for ( var emo in emotion_data) {
			content = content.replace(emo, "&nbsp;<img src='" + global.realPath
					+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
		}
		item.html(content);
	}
}
function setTodayInfo(){
	$("#tody_time").html(YYMMDD()+"("+solarDay2()+")");
	$("#tody_festival").html(weekday()+"&nbsp;&nbsp;<span style='color:#C00;font-weight:bold'>"+solarDay3()+"</span>");
}

function saveBlast(){
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
		url : global.realPath+"/user/saveBlast.action",// 请求的action路径
		success : function(data) {
			btnLoaded($("#talkBtn"),"吐槽");
			if(data.result=="200"){
				info("发布成功");
				$(".noinfo").remove();
				if($(".talkitem").length>0){
					$(".talkitem").eq(0).before(new TalkItem(data.blast).item);
				}else{
					new TalkItem(data.blast).item.appendTo($("#talklist"));
				}
				$("#talkcontent").val("");
				deleteImg();
				tipsInfo("2分已到碗里");
			}else{
				warm("show",data.msg);
			}
		}
	});
}

function loadBlast(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadLatestBlast",// 请求的action路径
		success : function(data) {
			if(data.result=="200"){
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


function loadSignInInfo(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/signInInfo",// 请求的action路径
		success : function(data) {
			var isSignIn =  0 ;
			var mySignInCount = 0;
			var allSignInCount = 0;
			if(data.result=="200"){
				isSignIn = data.isSignIn;
				mySignInCount = data.mySignInCount;
				allSignInCount = data.allSignInCount;
			}
			haveReMark(isSignIn,mySignInCount,allSignInCount);
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
				url : global.realPath+"/doSignIn.action",// 请求的action路径
				success : function(data) {
					if(data.result=="200"){
						curobj.attr("class","remark_siged");
						var count_all = parseInt($("#count_all").html());
						var count_my  = parseInt($("#count_my").html());
						$("#count_all").html("<a href='allSignIn.action' target='_blank'>"+(count_all+1)+"</a>");
						$("#count_my").html("<a href='mySignIn.action' target='_blank'>"+(count_my+1)+"</a>");
						if(data.countinueSignIn){
							tipsInfo("连续7天签到送10积分");
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
	if(global.userId!=""){
		$("<div class='count_all'><a href='allSignIn.action' id='count_all' target='_blank' title='今日签到'>"+allcount+"</a></div>").appendTo(remark_info);
		$("<div class='count_my'><a href='mySignIn.action' id='count_my' target='_blank' title='我的签到'>"+mycount+"</a></div>").appendTo(remark_info);
	}else{
		$("<div class='count_all' title='今日签到' id='count_all'>"+allcount+"</div>").appendTo(remark_info);
		$("<div class='count_my' title='我的签到' id='count_my'>"+mycount+"</div>").appendTo(remark_info);
	}
}

