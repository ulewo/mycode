function btnLoading(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","disable");
}

function btnLoaded(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","");
}

function warm(type,msg){
	if(type=="show"){
		$(".result_info").css({"display":"inline-block"});
		$("#warm_icon").removeClass("info");
		$("#warm_icon").css({"display":"inline-block","color":"red"});
		$("#warm_info").html(msg);
	}else if(type=="hide"){
		$('.result_info').hide();
	}
}

function tipsInfo(msg){
	setTimeout(function(){
		var tips;
		if($("#tips")[0]==null){
			tips = $("<div class='tips' id='tips'></div>").appendTo($("body"));
			$("<div class='tipscon'><span>"+msg+"</span><img src='"+global.realPath+"/images/good.png' width='15'></div>").appendTo(tips);
			var width = parseInt($(document.body).outerWidth(true));
			var tipswidth = tips.width();
			tips.css({"left":(width-tipswidth)/2});
		}else{
			tips = $("#tips");
			tips.show();
			tips.css({"top":"400px"});
		}
		tips.animate({top:150},1000).fadeOut(1000);
	},1);
}

function showRemoteLoad(){
	var height = $("html").height();
	var remote_shade = $("<div id='remote_load'></div>").appendTo($("body"));
	remote_shade.css({"height":height});
}

function removeRemoteLoad(){
	$("#remote_load").remove();
}

function getPageSize() {  
    var xScroll, yScroll;  
    if (window.innerHeight && window.scrollMaxY) {  
        xScroll = window.innerWidth + window.scrollMaxX;  
        yScroll = window.innerHeight + window.scrollMaxY;  
    } else {  
        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac      
            xScroll = document.body.scrollWidth;  
            yScroll = document.body.scrollHeight;  
        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari      
            xScroll = document.body.offsetWidth;  
            yScroll = document.body.offsetHeight;  
        }  
    }  
    var windowWidth, windowHeight;  
    if (self.innerHeight) { // all except Explorer      
        if (document.documentElement.clientWidth) {  
            windowWidth = document.documentElement.clientWidth;  
        } else {  
            windowWidth = self.innerWidth;  
        }  
        windowHeight = self.innerHeight;  
    } else {  
        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode      
            windowWidth = document.documentElement.clientWidth;  
            windowHeight = document.documentElement.clientHeight;  
        } else {  
            if (document.body) { // other Explorers      
                windowWidth = document.body.clientWidth;  
                windowHeight = document.body.clientHeight;  
            }  
        }  
    }         
    // for small pages with total height less then height of the viewport      
    if (yScroll < windowHeight) {  
        pageHeight = windowHeight;  
    } else {  
        pageHeight = yScroll;  
    }      
    // for small pages with total width less then width of the viewport      
    if (xScroll < windowWidth) {  
        pageWidth = xScroll;  
    } else {  
        pageWidth = windowWidth;  
    }  
    arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight);  
    return arrayPageSize;  
}  

function info(msg,autohide){
	var auto = autohide==null?true:autohide;
	$(".result_info").css({"display":"inline-block"});
	$("#warm_icon").addClass("info");
	$("#warm_icon").css({"display":"inline-block","color":"#494949"});
	$("#warm_info").html(msg);
	if(auto){
		setTimeout(function(){
			$('.result_info').fadeOut("slow");
		},2000);
	}
}

function Pager(totalPage, pageNum, page) {
	this.ulPanle = $("<ul></ul>");
	if (totalPage <= 1) {
		return;
	}
	// 起始页
	var beginNum = 0;
	// 结尾页
	var endNum = 0;

	if (pageNum > totalPage) {
		beginNum = 1;
		endNum = totalPage;
	}

	if (page - pageNum / 2 < 1) {
		beginNum = 1;
		endNum = pageNum;
	} else {
		beginNum = page - pageNum / 2 + 1;
		endNum = page + pageNum / 2;
	}

	if (totalPage - page < pageNum / 2) {
		beginNum = totalPage - pageNum + 1;
	}
	if (beginNum < 1) {
		beginNum = 1;
	}
	if (endNum > totalPage) {
		endNum = totalPage;
	}
	if (page > 1) {
		$(
				"<li><a href='javascript:loadPage(1)' class='prePage'>首&nbsp;&nbsp;页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadPage(" + (page - 1)
						+ ")'><</a></li>").appendTo(this.ulPanle);
	} 
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadPage(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a href='javascript:loadPage(" + (page + 1)
						+ ")'>></a></li>").appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadPage(" + totalPage
						+ ")' class='prePage'>尾&nbsp;&nbsp;页</a></li>").appendTo(
				this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}