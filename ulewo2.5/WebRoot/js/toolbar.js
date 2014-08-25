var toolbar = {};
$(function(){
	$(document).click(function() {
		toolbar.closeToolbarCon();
	});
	$(".tool-bar-item").bind("click",function(){
		if($(this).attr("id")=="gototop"){
			return;
		}
		var width = "300px";
		if($(this).attr("id")=="tool-post-topic"){
			width = "900px";
		}
		$(".tool-bar-item").removeClass("cur-bar-item");
		$(this).addClass("cur-bar-item");
		$("#tool-bar-con").show();
		$("#tool-bar-con").animate({
	    width:width
	  },500);
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

