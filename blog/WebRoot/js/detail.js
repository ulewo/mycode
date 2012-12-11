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



