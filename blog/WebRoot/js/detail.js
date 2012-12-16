function addReArticle(articleId){
	var userName = $("#userName").val();
	var content = $("#content").val();
	if(userName==""||userName=="姓名"){
		alert("留下大名，告诉我你是谁吧");
		$("#userName").focus();
		return;
	}
	if(content==""||content=="请输入你想说的"){
		alert("好歹写点东西吧");
		$("#content").focus();
		return;
	}
	var quote = "";
	if($("#quote_panle").html()!=null){
		quote = $("#quote_panle").html();
	}
	
	$("#subBtn").hide();
	$("#load").show();
	$.ajax({
		async : true,
		cache : true,
		type : 'POST',
		dataType : "json",
		data:{
			userName:userName,
			quote:quote,
			content:content,
			articleId:articleId
		},
		url : 'reArticle?method=add',// 
		success : function(data) {
			new ReArticlePanle(data.reArticle).asHtml().appendTo($("#rearticle_list"));
			 $("#userName").val("姓名");
			 $("#userName").css({"color":"#666666"});
			 $("#content").val("请输入你想说的");
			 $("#content").css({"color":"#666666"});
			 $("#subBtn").show();
			 $("#load").hide();
			 $("#quote_panle").remove();
		}
	});
}

function loadReArticles(articleId){
	$("<div>评论加载中.....</div>").appendTo($("#rearticle_list"));
	$.ajax({
		async : true,
		cache : true,
		type : 'POST',
		dataType : "json",
		url : 'reArticle?articleId='+articleId,// 
		success : function(data) {
			$("#rearticle_list").html("");
			var reArticles =data.list;
			for(var i = 0;i<reArticles.length;i++){
				new ReArticlePanle(reArticles[i]).asHtml().appendTo($("#rearticle_list"));
			}
		}
	});
}

function ReArticlePanle(reArticle){
	this.reArticleCon = $("<div class='rearticle_tr'></div>");
	var re_name_time = $("<div class='re_name_time'></div>").appendTo(this.reArticleCon);
	var adminstyle="";
	if(reArticle.type=="A"){
		adminstyle = "adminstyle";
	}
	$("<div class='re_name "+adminstyle+"'>"+reArticle.userName+"</div>").appendTo(re_name_time);
	$("<div class='re_time'>"+reArticle.postTime.substring(0,19)+"</div>").appendTo(re_name_time);
	$("<a href='javascript:void(0)'>引用</a>").bind("click",this.quote).appendTo($("<div class='re_time'></div>").appendTo(re_name_time));
	$("<div class='re_con'>"+reArticle.content+"</div>").appendTo(this.reArticleCon);
}
ReArticlePanle.prototype={
	asHtml:function(){
		return this.reArticleCon;
	},
	quote:function(){
		$("#quote_panle").remove();
		var _this = $(this);
		var name = $(this).parent().parent().children().eq(0).html();
		var time = $(this).parent().parent().children().eq(1).html();
		var content = $(this).parent().parent().parent().children().eq(1).html();
		var quote_panle = $("<div id='quote_panle'></div>");
		$("#userName").before(quote_panle);
		var quote = $("<div class='quote'></div>").appendTo(quote_panle);
		var quote_tit = $("<div class='quote_tit'></div>").appendTo(quote);
		$("<img src='images/qbar_iconb24.gif'><span>引用</span>").appendTo(quote_tit);
		$("<span class='quote_name'>"+name+"</span>").appendTo(quote_tit);
		$("<span>在"+time+"的发表</span>").appendTo(quote_tit);
		$("<div class='quote_content'>"+content+"</div>").appendTo(quote);
	}
}

var share_title = "(分享自 你乐我 ulewo.com)";

function dispatche(_type, content, imgUrl, id) {
	var _data = {};
	_data.content = content;
	_data.imgUrl = imgUrl;
	_data.id = id;
	switch (_type) {
	case 0:
		// 0=分享到QQ空间
		share_qzone(_data)
		break;
	case 1:
		// 1=分享到腾讯微博
		share_tx_wb(_data)
		break;
	case 2:
		// 2分享到新浪
		share_sina_wb(_data)
		break;
	case 3:
		// 3分享到人人网
		share_renren(_data);
		break;
	default:
		window.location = _data.wbUrl;
		break;
	}
}
// 分享到QQ空间
function share_qzone(data) {
	var param = [];
	data.content = data.content + share_title;
	var wburl = "http://blog.ulewo.com/detail?id=" + data.id;
	var _url = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?";
	param[0] = "url=" + encodeURIComponent(wburl);
	param[1] = "title=" + data.content;
	param[2] = "pics="+ data.imgUrl;
	param[3] = "summary=" + data.content;
	_url += param.join("&");
	forward(_url);
}

// 分享到腾讯微博
function share_tx_wb(data) {
	var param = [];
	var wburl = "http://blog.ulewo.com/detail?id=" + data.id;
	data.content = data.content + share_title;
	var _url = "http://share.v.t.qq.com/index.php?";
	param[0] = "c=share";
	param[1] = "a=index";
	param[2] = "url=" + wburl;
	param[3] = "pic="+ data.imgUrl;
	param[4] = "title=" + data.content;
	_url += param.join("&");
	forward(_url);
}
// 分享到新浪微博
function share_sina_wb(data) {
	var param = [];
	var wburl = "http://blog.ulewo.com/detail?id=" + data.id;
	param[0] = "pic="+ data.imgUrl;
	param[1] = "title=" + data.content;
	param[2] = "url=" + wburl;
	var _url = "http://service.t.sina.com.cn/share/share.php?";
	_url += param.join("&");
	forward(_url);
}

// 分享到人人网
function share_renren(data) {
	var param = [];
	var wburl = "http://blog.ulewo.com/detail?id=" + data.id;
	param[0] = "resourceUrl=" + wburl;
	param[1] = "description=" + data.content;
	param[2] = "title=" + data.content;
	param[3] = "images="+ data.imgUrl;
	var _url = "http://widget.renren.com/dialog/share?";
	_url += param.join("&");
	forward(_url);
}

function forward(_url) {
	window.open(_url);
}

