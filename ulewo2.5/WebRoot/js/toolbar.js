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
		getInfo($(this));
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
});

//获取信息
function getInfo(curObj){
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
			
		}
	}else if(curObj.attr("id")=="tool-notice"){
		if(barCon.html()==""){
			
		}
	}
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

