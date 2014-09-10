function btnLoading(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","disable");
}

function lazyLoadImage(imageBody){
	/*$("."+imageBody+" img").lazyload({ 
		placeholder: global.realPath+"/images/imgloading.gif", 
		effect: fadeIn, 
		failurelimit: 10 
	})
	*/
}

function loadImag(myImage){
    var img = new Image();  
    var imgSrc = myImage.attr("name");
    if(imgSrc==null||imgSrc.indexOf("imgloading")!=-1){
    	imgSrc=myImage.attr("src");
    	myImage.attr("src",global.realPath+"/images/imgloading.gif");
    }
    img.src = imgSrc;
    var browser = getBrowserType();  
    if (browser == "IE") { 
    	if(img.readyState == "complete"){
    		myImage.attr("src",img.src);  
    		return;
    	}
        img.onreadystatechange = function() {  
            if (img.readyState == "complete") {  
                myImage.attr("src",img.src);  
            }  
        }
    } else if (browser == "others") {  
    	if(img.complete){
    		myImage.attr("src",img.src);  
    		return;
    	}
        img.onload = function() {  
            if (img.complete) {  
            	 myImage.attr("src",img.src);  
            }  
        }
    }  
}  

function checkFavorite(articleid,type){
	$.ajax({
		async: true,
		cache: false,
		type: 'GET',
		dataType: "json",
		url: global.realPath + "/user/checkFavorite?articleId=" + articleid + "&type=" + type,
		// 请求的action路径
		success: function(data) {
			if (data.result == "200") {
				$("#favoriteCount").text(data.haveFavoriteCount);
				if (data.haveFavorite) {
					$("#op_favorite").html('<span>已收藏</span>');
				} else {
					$("#op_favorite").html('<a href="javascript:void(0)">我要收藏</a>');
				}
			}else{
				$("#op_favorite").html('<a href="javascript:void(0)">我要收藏</a>');
			}
		}
	});
}

function btnLoaded(obj,html){
	$(obj).html(html);
	$(obj).attr("disable","");
}

function getBrowserType() {  
    var browser = navigator.userAgent.indexOf("MSIE") > 0 ? 'IE' : 'others';  
    return browser;  
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

function warm4fast(type,msg){
	if(type=="show"){
		$(".result_info4fast").css({"display":"inline-block"});
		$("#warm_icon4fast").removeClass("info");
		$("#warm_icon4fast").css({"display":"inline-block","color":"red"});
		$("#warm_info4fast").html(msg);
	}else if(type=="hide"){
		$('.result_info4fast').hide();
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
			tips.find(".tipscon").find("span").text(msg);
			tips.show();
			tips.css({"top":"400px"});
		}
		tips.animate({top:150},1000).fadeOut(1000);
	},1);
}
/**********遮罩加载中************/
function showLoading(msg){
	var body_height =  $(window).height();
	var body_width = $(window).width();
	var remote_shade = "";
	var loading_con = "";
	if($("#remote_load")[0]==null){
		remote_shade = $("<div id='remote_load'></div>").appendTo($("body"));
		loading_con = $("<div class='loading_con' id='loading_con'></div>").appendTo($("body"));
	}else{
		remote_shade = $("#remote_load");
		loading_con = $("#loading_con");
	}
	remote_shade.show();
	loading_con.show();
	loading_con.empty();
	var loading_info = $("<div class='loading_info'></div>").appendTo(loading_con);
	$("<span class='loading_msg'>"+msg+"</span>").appendTo(loading_info);
	$("<span class='loading_icon'><img src='"+global.realPath+"/images/load.gif'></span>").appendTo(loading_info);
	remote_shade.css({"height":body_height});
	var tipswidth = loading_con.width();
	loading_con.css({"left":(body_width-tipswidth)/2,"top":(body_height-30)/2});
}

function removeLoad(){
	$("#remote_load").hide();
	$("#loading_con").hide();
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