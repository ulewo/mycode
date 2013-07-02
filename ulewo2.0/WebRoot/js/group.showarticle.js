var editor;
$(function() {
	$(".article_detail pre").each(function () {
        var $this = $(this);
        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
            var lang = $this.attr("class").split(';')[0].split(':')[1];
            $this.attr('name', 'code');
            $this.attr('class', lang);
        }
    });
    dp.SyntaxHighlighter.HighlightAll('code');
    
	$("#new_article_p").bind("click",showAddForm);
	$("#sub_article_btn").bind("click",submitForm);
	loadPage(1);
	$("#downloadFile").bind("click",downloadFile);
})

//下载附件
function downloadFile(){
	if(global.userId==""){
		alert("请先登录");
		return;
	}
	if($(this).attr("disable")=="disable"){
		return;
	}
	btnLoading($(this),"获取资源中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	var fileId = $(this).attr("name");
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/group/downloadFile.action?fileId="+fileId,// 请求的action路径
		success : function(data) {
			btnLoaded($("#downloadFile"),"点击下载");
			if(data.result=="fail"){
				alert(data.message);
			}else{
				$("#dcount").text(parseInt($("#dcount").text())+1);
				document.location.href=global.realPath+"/group/downloadFileDo.action?fileId="+fileId;
			}
		}
	});
}

//分页
function loadPage(page){
	loadReComment(global.articleId,page);
}

function loadReComment(articleId,page) {
	var rePanel;
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/group/loadReComment?id=" + articleId+"&page="+page,// 请求的action路径
		success : function(data) {
			
			var list = data.result.list;
			var length = list.length;
			if(length>0){
				$("#recomment").empty();
				$("#pager").empty();
				var childlist;
				var childLength;
				for ( var i = 0; i < length; i++) {
					var recomment = list[i];
					if (recomment.pid == 0||recomment.pid==null) {
						rePanel = new RePanel(recomment);
						rePanel.asHtml().appendTo($("#recomment"));
					}
					childlist = list[i].childList;
					if(childlist!=null){
						childLength = childlist.length;
						for ( var j = 0; j < childLength; j++) {
							new SubRePanel(childlist[j]).asHtml().appendTo(
									rePanel.reComent_Con);
						}
					}
				}
				if(data.result.pageTotal>1){
					new Pager(data.result.pageTotal,10,data.result.page).asHtml().appendTo($("#pager"));
				}else{
					$("#pager").hide();
				}
				$(".outerHeight pre").each(function () {
			        var $this = $(this);
			        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
			            var lang = $this.attr("class").split(';')[0].split(':')[1];
			            $this.attr('name', 'code');
			            $this.attr('class', lang);
			        }
			    });
			    dp.SyntaxHighlighter.HighlightAll('code');
			}else{
				$("<div class='noinfo'>没有回复,赶紧抢沙发吧！</div>").appendTo($("#recomment"));
			}
			
		}
	});
}

function getPostion() {
	var curUrl = window.location.href;
	if (curUrl.lastIndexOf("#") != -1) {
		var type = curUrl.substr(curUrl.lastIndexOf("#") + 1);
		window.location.hash = type;
	}
}

function RePanel(data) {
	data.pid = data.id;
	this.outerHeight = $("<div class='outerHeight'></div>");
	$("<a name=re" + data.id + ">").appendTo(this.outerHeight);
	// 头像
	var authorIcon = data.authorIcon==""?global.realPath+"/upload/defaultsmall.gif":global.realPath+"/upload/"+data.authorIcon;
	this.ui_avatar = $("<div class='ui_avatar'><img src='"+ authorIcon + "' width='30'></div>");
	if (data.authorid != ""&&data.authorid !=null) {
		this.ui_avatar = $("<div class='ui_avatar'><a href='"+global.realPath+"/user/"
				+ data.authorid
				+ "'><img src='"+ authorIcon+ "' width='30' border='0'></a></div>");
	}
	this.ui_avatar.appendTo(this.outerHeight);
	// 内容
	this.reComent_Con = $("<div class='reComent_Con'></div>").appendTo(
			this.outerHeight);
	$("<div class='clear'></div>").appendTo(this.outerHeight);
	this.comments_content = $("<div class='comments_content'></div>").appendTo(
			this.reComent_Con);
	
	if (data.authorid != ""&&data.authorid !=null) {
		$(
				"<a href='"+global.realPath+"/user/" + data.authorid + "'>"
						+ data.authorName
						+ "</a>:<span class='comment_content_word'>" + data.content
						+ "</span>").appendTo(this.comments_content);
	}else{
		$("<span style='color:#9B9B9B'>"+data.authorName+"</span>:<span class='comment_content_word'>" + data.content+"</span>").appendTo(this.comments_content);
	}
	
	this.comments_op = $("<div class='comments_op'></div>").appendTo(
			this.comments_content);

	if (data.sourceFrom == "A") {
		$("<span class='com_op_time'>" + data.reTime + "(来自:android客户端)</span>")
				.appendTo(this.comments_op);
	} else {
		$("<span class='com_op_time'>" + data.reTime + "</span>").appendTo(
				this.comments_op);
	}
	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op).bind("click", {
		data : data
	}, this.showReForm);
	$("<div class='coment_sub_panel'></div>").appendTo(this.comments_content);
}
RePanel.prototype = {
	asHtml : function() {
		return this.outerHeight;
	},
	showReForm : function(event) {
		$("#recoment_form_panel").remove();
		var data = event.data.data;
		var reComent_Con = $(this).parent().parent().parent();
		if (reComent_Con.children(".comtent_sub").length > 0) {
			reComent_Con.children(".comtent_sub").last().after(
					new Recoment_form_panel(data).recoment_form_panel);
		} else {
			reComent_Con
					.append(new Recoment_form_panel(data).recoment_form_panel);
		}
	}
}
function SubRePanel(data) {
	this.comment_sub = $("<div class='comtent_sub'></div>");
	$("<a name=re" + data.id + ">").appendTo(this.comment_sub);
	this.ui_avatar = $(
			"<div class='ui_avatar'><img src='"+global.realPath +"/upload/"+data.authorIcon
					+ "' width='30'></div>").appendTo(this.comment_sub);
	this.comments_content_sub = $("<div class='comments_content_sub'></div>")
			.appendTo(this.comment_sub);
	$("<div class='clear'></div>").appendTo(this.comment_sub);
	$(
			"<a href='"+global.realPath+"/user/"
					+ data.authorid
					+ "'>"
					+ data.authorName
					+ "</a>&nbsp;回复&nbsp;<a href='"+global.realPath+"/user/"
					+ data.atUserId + "'>" + data.atUserName
					+ "</a>:<span class='comment_content_word'>" + data.content
					+ "</span>").appendTo(this.comments_content_sub);
	this.comments_op_sub = $("<div class='comments_op_sub'></div>").appendTo(
			this.comments_content_sub);

	if (data.sourceFrom == "A") {
		$("<span class='com_op_time'>" + data.reTime + "(来自:android客户端)</span>")
				.appendTo(this.comments_op_sub);
	} else {
		$("<span class='com_op_time'>" + data.reTime + "</span>").appendTo(
				this.comments_op_sub);
	}

	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op_sub).bind("click", {
		data : data
	}, this.showReForm);
	;
}
SubRePanel.prototype = {
	asHtml : function() {
		return this.comment_sub;
	},
	showReForm : function(event) {
		$("#recoment_form_panel").remove();
		var data = event.data.data;
		$(this).parent().parent().parent().parent().children(".comtent_sub")
				.last()
				.after(new Recoment_form_panel(data).recoment_form_panel);
	}
}

function Recoment_form_panel(data) {
	this.recoment_form_panel = $("<div class='recoment_form_panel' id='recoment_form_panel'></div>");
	var comment_form_at = $("<div class='comment_form_at'></div>").appendTo(
			this.recoment_form_panel);
	$("<a href='javasccript:void(0)'>@" + data.authorName + "</a>").appendTo(
			comment_form_at);
	$("<span><img src='"+global.realPath+"/images/delete.png'></span>")
			.appendTo(comment_form_at).bind("click", function() {
				$("#recoment_form_panel").remove();
			});
	this.textarea = $("<textarea></textarea>").appendTo(
			$("<div class='comment_form_textarea'></div>").appendTo(
					this.recoment_form_panel));
	this.checkCode_area = $("<div class='comment_form_panel'></div>").appendTo(
			this.recoment_form_panel);
	/*
	 * this.checkCode = $("<input type='text'>").appendTo( $("<div
	 * class='comment_checkcode'></div>").appendTo( this.checkCode_area)); $( "<div
	 * class='comment_checkcode_img'><a href='JavaScript:refreshcode();'
	 * onfocus='this.blur();'><img id='checkCodeImage'
	 * src='../common/image.jsp' border='0' height='22'></a></div>")
	 * .appendTo(this.checkCode_area); $( "<div class='comment_checkcode_link'><a
	 * href='javascript:refreshcode()'>换一张</a></div>")
	 * .appendTo(this.checkCode_area);
	 */
	var comment_checkcode_rebtn = $(
			"<div class='comment_checkcode_rebtn'></div>").appendTo(
			this.checkCode_area);

	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		data : data,
		reCotent : this.textarea
	}, this.subReComent).appendTo(comment_checkcode_rebtn);
	$("<img src='"+global.realPath+"/images/load.gif' style='display:none'>").appendTo(
			comment_checkcode_rebtn);
	if (global.userId == "") {
		var shade = $("<div class='shade' id='shade'></div>").appendTo(
				this.recoment_form_panel);
		var shadeLogin = $(
				"<div class='shadeLogin'>回复，请先 <a href='javascript:goto_login()'>登录</a>&nbsp;&nbsp;<a href='javascript:goto_register()'>注册</a></div>")
				.appendTo(shade);
		shade.css({
			"width" : "520px",
			"height" : "115px",
			"left" : "-5px",
			"top" : "23px"
		});
		shadeLogin.css({
			"marginTop" : "40px"
		});
	}
}
Recoment_form_panel.prototype = {
	subReComent : function(event) {
		var data = event.data.data;
		data.reCotent = event.data.reCotent.val();
		var pid = data.pid;
		var atUserId = data.authorid;
		var atUserName = data.authorName;
		var reCotent = data.reCotent;
		if (reCotent == "") {
			alert("请填写回复内容");
			return;
		}
		if (reCotent.trim().length > 500) {
			alert("内容超过500字符，请重新输入");
			return;
		}
		// 防止重复提交禁止提交按钮
		var _thispaernt = $(this).parent();
		_thispaernt.children().eq(0).hide();
		_thispaernt.children().eq(1).show();
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				"articleId" : global.articleId,
				"content" : reCotent,
				"gid" : global.gid,
				"atUserId" : atUserId,
				"atUserName" : atUserName,
				"pid" : pid
			},
			url : global.realPath+"/group/addSubReComment.action",// 请求的action路径
			success : function(data) {
				_thispaernt.children().eq(0).show();
				_thispaernt.children().eq(1).hide();
				if(data.result=="fail"){
					alert(data.message);
					return;
				}
				$("#recoment_form_panel").before(
						new SubRePanel(data.reArticle).asHtml());
				$("#recoment_form_panel").remove();
				tipsInfo("2分已到碗里");
			}
		});
	}
}


function callBackReArticle(data, userId) {
	if (data.msg == "nologin") {
		alert("请首先登录");
		return;
	} else if (data.msg == "contentError") {
		alert("输入内容为空，或者内容太长");
		return;
	}
	clearValue();
	var rePanel = new RePanel(data.reArticle);
	rePanel.asHtml().appendTo($("#recomment"));
}

function clearValue() {
	$("#reContent").val("");
	$("#checkCode").val("");
}

/** ********相关文章************ */
function showAboutArticle(keyWord, gid) {
	$("<img src="+global.realPath+"'/images/load.gif' id='loadImg'>").appendTo($("#about_con"));
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


/***************回复*******************/
function showAddForm(){
	$(this).hide();
	warm("hide","");
	$("#add_article").show();
	$("#content").val("");
	if(editor==null){
		var width = $("#add_article").outerWidth(true);
		window.UEDITOR_CONFIG.initialFrameWidth = parseInt(width-20);
		editor = new UE.ui.Editor();
		editor.render("editor");
	}
	editor.ready(function(){
	        editor.setContent("");
	});
}

/*********取消***********/
function cancelAdd(){
	$("#new_article_p").show();
	$("#add_article").hide();
}

function submitForm(){
	var subBtn = $("#sub_article_btn");
	if(subBtn.attr("disable")=="disable"){
		return;
	}
	var content = editor.getContentTxt().trim();
	if(content==""){
		warm("show","内容不能为空");
		return ;
	}else{
		$("#content").val(editor.getContent());
	}
	warm("hide","");
	btnLoading(subBtn,"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#articleform").serialize(),
		url : global.realPath+"/group/addReComment.action",// 请求的action路径
		success : function(data) {
			btnLoaded(subBtn,"发表回复");
			if(data.result=="fail"){
				warm("show",data.message);
			}else{
				cancelAdd();
				$(".noinfo").remove();
				new RePanel(data.reArticle).asHtml().appendTo($("#recomment"));
				tipsInfo("2分已到碗里");
			}
		}
	});
}