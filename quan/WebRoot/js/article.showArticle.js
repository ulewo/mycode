function subReForm(userId, id, gid) {
	var reUserName = $("#reUserName").val();
	var recontent = $("#reContent").val();
	var checkCode = $("#checkCode").val();
	var authorId = $("#authorId").val();
	var articleTit = $("#articleTit").val();
	if (userId == "" && reUserName.trim() == "") {
		art.dialog.tips("请填写用户名");
		return;
	}

	if (recontent.trim() == "") {
		art.dialog.tips("请填写回复内容");
		return;
	}
	if (userId == "" && checkCode == "") {
		art.dialog.tips("请填写验证码");
		return;
	}

	if (recontent.trim().length > 500) {
		art.dialog.tips("内容超过500字符，请重新输入");
		return;
	}
	$("#subBtn").attr("disabled", true);
	$("<img src='../images/loading.gif' width='20'>")
			.appendTo($("#subBtn_con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"articleid" : id,
			"reUserName" : reUserName,
			"reContent" : recontent,
			"checkCode" : checkCode,
			"gid" : gid,
			"authorId" : authorId,
			"title" : articleTit
		},
		url : 'addReArticle.jspx',// 请求的action路径
		success : function(data) {
			callBackReArticle(data, userId);
			$("#reCount").html(parseInt($("#reCount").html()) + 1);
			clearValue();
			$("#subBtn").attr("disabled", false);
			$("#subBtn_con").children("img").remove();
		}
	});
}

function callBackReArticle(data, userId) {
	if (data.reArticle == "checkCodeErr") {
		art.dialog.tips("验证码错误");
		refreshcode();
		return;
	}
	var articleId = data.reArticle.id;
	var reUserName = data.reArticle.authorName;
	var authorid = data.reArticle.authorid;
	var reContent = data.reArticle.content;
	var reTime = data.reArticle.reTime.substring(0, 16);
	var user = data.reArticle.author;
	var reUserImg = "";
	if (user != null) {
		reUserImg = data.reArticle.author.userLittleIcon;
		reUserName = data.reArticle.author.userName;
	}
	var recon_con = $("<div class='recon_con'><div>").appendTo($("#reCon"));
	if (authorid == "") {
		$(
				"<div class='recon_img'><img src='../upload/default.gif' width='50'><div>")
				.appendTo(recon_con);
	} else {
		$(
				"<div class='recon_img'><a href='../user/userInfo.jspx?userId="
						+ authorid + "'><img src='../upload/" + reUserImg
						+ "' width='50' border='0'></a><div>").appendTo(
				recon_con);
	}
	var recon_info = $("<div class='recon_info'></div>").appendTo(recon_con);
	$("<div class='clear'></div>").appendTo(recon_con);
	var recon_info_re = $("<div class='recon_info_re'></div>").appendTo(
			recon_info);
	$("<div class='recon_info_con'>" + reContent + "</div>").appendTo(
			recon_info);
	if (authorid != "") {
		reUserName = "<a href='../user/userInfo.jspx?userId=" + authorid + "'>"
				+ reUserName + "</a>";
	}
	$(
			"<div class='recon_info_info'>" + "<span class='info_lou'>"
					+ ($(".recon_con").length) + "楼</span>"
					+ "<span class='info_name'>" + reUserName + "</span>"
					+ "<span class='info_time'>发表时间：" + reTime + "</span>"
					+ "</div>").appendTo(recon_info_re);
	if (userId != "") {
		recon_info_info_op = $(
				"<div class='recon_info_info_op'>"
						+ "<span><a href='javascript:void(0)' onclick='quote("
						+ data.reArticle.id
						+ ")'>回复</a></span><span class='re_op_d'>" +
						// "<a href='####'>删除</a>" +
						"</span>" + "</div>").appendTo(recon_info_re);
	}

}

function clearValue() {
	$("#reUserName").val("");
	$("#reContent").val("");
	$("#checkCode").val("");
}
function quote(id) {
	document.location.href = "quote.jspx?id=" + id;
}

function showAboutArticle(keyWord, gid) {
	$("<img src='../images/loading.gif' id='loadImg'>").appendTo(
			$("#about_con"));
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"keyWord" : keyWord,
			"gid" : gid
		},
		url : 'aboutArticles.jspx',// 请求的action路径
		success : function(data) {
			$(".about_con").children("#loadImg").remove();
			var articles = data.articleList;
			if (articles.length > 0) {
				for ( var i = 0; i < articles.length; i++) {
					if ($("#title_con").html() != articles[i].title) {
						$(
								"<div class='about_con_info'><a href='post.jspx?id="
										+ articles[i].id + "'>"
										+ articles[i].title + "</a></div>")
								.appendTo($("#about_con"));
					}
				}
			} else {
				$("<div class='about_con_info'>没有找到相关记录</div>").appendTo(
						$("#about_con"));
			}

		}
	});
}

function refreshcode() {
	$("#checkCodeImage").attr("src",
			"../common/image.jsp?rand =" + Math.random());
}


function loadReComment(){
	var rePanel;
	for(var i=0;i<10;i++){
		var data = new Object();
		data.content="sdfas短发散发阿斯蒂芬阿斯蒂芬阿阿斯蒂芬爱的";
		data.time="2012-12-23";
		data.userName = "王五";
		rePanel = new RePanel(data);
		rePanel.asHtml().appendTo($("#recomment"));
		for(var j=0;j<3;j++){
			new SubRePanel(data).asHtml().appendTo(rePanel.reComent_Con);
		}
	}
}

function RePanel(data){
	this.outerHeight = $("<div class='outerHeight'></div>");
	//头像
	this.ui_avatar = $("<div class='ui_avatar'><img src='../upload/defaultsmall.gif' width='30'></div>").appendTo(this.outerHeight);
	//内容
	this.reComent_Con = $("<div class='reComent_Con'></div>").appendTo(this.outerHeight);
	$("<div class='clear'></div>").appendTo(this.outerHeight);
	this.comments_content = $("<div class='comments_content'></div>").appendTo(this.reComent_Con);
	$("<a href=''>张三</a>:<span>"+data.content+"</span>").appendTo(this.comments_content);
	this.comments_op = $("<div class='comments_op'></div>").appendTo(this.comments_content);
	$("<span class='com_op_time'>"+data.time+"</span>").appendTo(this.comments_op);
	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(this.comments_op).bind("click",{data:data},this.showReForm);
	$("<div class='coment_sub_panel'></div>").appendTo(this.comments_content);
}
RePanel.prototype={
	asHtml : function() {
		return this.outerHeight;
	},
	showReForm:function(event){
		$("#recoment_form_panel").remove();
		var data = event.data.data
		$(this).parent().parent().parent().children(".comtent_sub").last().after(new Recoment_form_panel(data).recoment_form_panel);
		
	}
}
function SubRePanel(data){
	this.comment_sub = $("<div class='comtent_sub'></div>");
	this.ui_avatar = $("<div class='ui_avatar'><img src='../upload/defaultsmall.gif' width='30'></div>").appendTo(this.comment_sub);
	this.comments_content_sub = $("<div class='comments_content_sub'></div>").appendTo(this.comment_sub);
	$("<div class='clear'></div>").appendTo(this.comment_sub);
	$("<a href=''>王五</a>&nbsp;回复&nbsp;<a href=''>赵六</a>:<span>"+data.content+"</span>").appendTo(this.comments_content_sub);
	this.comments_op_sub = $("<div class='comments_op_sub'></div>").appendTo(this.comments_content_sub);
	$("<span class='com_op_time'>"+data.time+"</span>").appendTo(this.comments_op_sub);
	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(this.comments_op_sub).bind("click",{data:data},this.showReForm);;
}
SubRePanel.prototype={
	asHtml : function() {
		return this.comment_sub;
	},
	showReForm:function(event){
		$("#recoment_form_panel").remove();
		var data = event.data.data;
		$(this).parent().parent().parent().parent().children(".comtent_sub").last().after(new Recoment_form_panel(data).recoment_form_panel);
	}
}

function Recoment_form_panel(data){
	this.recoment_form_panel = $("<div class='recoment_form_panel' id='recoment_form_panel'></div>");
	$("<div class='comment_form_at'><a href='javasccript:void(0)'>@"+data.userName+"</a></div>").appendTo(this.recoment_form_panel);
	var form_name = $("<div class='comment_form_name'></div>").appendTo(this.recoment_form_panel);
	var name_input = $("<input type='text' value='请输入用户名'>").appendTo(form_name).bind("focus",this.mouseIn).bind("blur",this.mouseOut);
	$("<div class='comment_form_textarea'><textarea></textarea></div>").appendTo(this.recoment_form_panel);
	this.checkCode_area = $("<div class='comment_form_panel'></div>").appendTo(this.recoment_form_panel);
	$("<div class='comment_checkcode'><input type='text'></div>").appendTo(this.checkCode_area);
	$("<div class='comment_checkcode_img'><a href='JavaScript:refreshcode();' onfocus='this.blur();'><img id='checkCodeImage' src='../common/image.jsp' border='0'></a></div>").appendTo(this.checkCode_area);
	$("<div class='comment_checkcode_link'><a href='javascript:refreshcode()'>换一张</a></div>").appendTo(this.checkCode_area);
}
Recoment_form_panel.prototype={
	mouseIn:function(){
		var value = $(this).val();
		if(value=="请输入用户名"){
			$(this).val("");
			$(this).css("color","#000000");
		}
	},
	mouseOut:function(){
		var value = $(this).val();
		if(value==""){
			$(this).css("color","#9B9B9B");
			$(this).val("请输入用户名");
		}
	}
}