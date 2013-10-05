var editor;
//var s = {"highlightJsUrl":window.UEDITOR_HOME_URL+"third-party/SyntaxHighlighter/shCore.js","highlightCssUrl":window.UEDITOR_HOME_URL+"third-party/SyntaxHighlighter/shCoreDefault.css"}
$(function() {
	$("#new_article_p").bind("click",showAddForm);
	$("#sub_article_btn").bind("click",submitForm);
	$("#downloadFile").bind("click",downloadFile);
	$("#op_favorite a").live("click",function(){
		if(global.userId==""){
			alert("请先登录");
			return;
		}
		if($(this).attr("disable")=="disable"){
			return;
		}
		btnLoading($(this),"收藏中.....");
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				partId:global.gid,
				articleId:global.articleId,
				type : "A",
				title : $("#article_title").text()
			},
			url : global.realPath+"/user/favoriteArticle.action",// 请求的action路径
			success : function(data) {
				if (data.result == "fail") {
					btnLoaded($("#op_favorite a"),"我要收藏");
					alert(data.message);
				} else {
					$("#favoriteCount").text(parseInt($("#favoriteCount").text())+1);
					$("#op_favorite").html('<span>已收藏</span>');
				}
			}
		});
	});
	lazyLoadImage("article_detail");
	$(".article_detail pre").each(function () {
        var $this = $(this);
        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
            var lang = $this.attr("class").split(';')[0].split(':')[1];
            $this.attr('name', 'code');
            $this.attr('class', lang);
        }
    });
    dp.SyntaxHighlighter.HighlightAll('code');
	loadPage(1);
	checkFavorite(global.articleId,"A");
	
	$(".share a").each(function(index){
		$(this).bind("click",function(){
			dispatche(index);
		});
	});
	
	$("#dcount").bind("click",fetchAttachedUser);
	$(document).click(function(){
		$("#attachedUser").hide();
	});
	$("#attachedUser").click(function(event){
		event.stopPropagation();
	});
});

/** *************插入表情*************** */
function showEmotion() {
	$("#pm_emotion_cnt").show();
}
	

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

//获取附件下载的人数
function fetchAttachedUser(event){
	event.stopPropagation();
	$("#attachedUser").empty();
	var attachedId = $(this).attr("name");
	var left = $(this).offset().left;
	var top = $(this).offset().top;
	$("#attachedUser").css({"left":(left-100),"top":(top+15),"display":"block"});
	$("<div class='loading'><img src='"+global.realPath+"/images/load.gif'/></div>").appendTo($("#attachedUser"));
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/group/fetchAttachedUsers.do?attachedId="+attachedId,// 请求的action路径
		success : function(data) {
			$(".loading").remove();
			if(data.result=="fail"){
				alert(data.message);
				$("#attachedUser").hide();
			}else{
				var list = data.list;
				if(list.length>0){
					for(var i=0,length = list.length;i<length;i++){
						$("<div class='attarchedUserCon'><a href='"+global.realPath+"/user/"+list[i].userId+"' title='"+list[i].userName+"' target='_blank'><img src='"+global.realPath+"/upload/"+list[i].userIcon+"'/></a></div>").appendTo($("#attachedUser"));
					}
				}else{
					$("<div class='noinfo' style='padding-bottom:5px;'>目前没人下载</div>").appendTo($("#attachedUser"));
				}
				
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
				//根据回复定位回复位置
				setPostion();
				$(".outerHeight pre").each(function () {
			        var $this = $(this);
			        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
			            var lang = $this.attr("class").split(';')[0].split(':')[1];
			            $this.attr('name', 'precode');
			            $this.attr('class', lang);
			        }
			    });
			   dp.SyntaxHighlighter.HighlightAll('precode');
				//uParse("div .outerHeight",s);
			}else{
				$("<div class='noinfo'>没有回复,赶紧抢沙发吧！</div>").appendTo($("#recomment"));
			}
			
		}
	});
}
//获取回复的位置
function setPostion(){
	var url = window.location.href;
	var index_ = url.lastIndexOf("#");
	if(index_!=-1){
		var reId = url.substring(url.lastIndexOf("#")+1);
		var top = $("a[name='re"+reId+"']").offset().top-50;
		window.scrollTo(0,top);
	}
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
				+ "'><img src='"+ authorIcon+ "' width='40' border='0'></a></div>");
	}
	this.ui_avatar.appendTo(this.outerHeight);
	// 内容
	this.reComent_Con = $("<div class='reComent_Con'></div>").appendTo(
			this.outerHeight);
	$("<div class='clear'></div>").appendTo(this.outerHeight);
	this.comments_content = $("<div class='comments_content'></div>").appendTo(
			this.reComent_Con);
	var name_p = $("<div class='name_p'></div>").appendTo(this.comments_content);
	if (data.authorid != ""&&data.authorid !=null) {
		$(
				"<a href='"+global.realPath+"/user/" + data.authorid + "'>"
						+ data.authorName
						+ "</a>").appendTo(name_p);
	}else{
		$("<span style='color:#9B9B9B'>"+data.authorName+"</span>").appendTo(name_p);
	}
	
	if (data.sourceFrom == "A") {
		$("<span class='com_op_time'>" + data.reTime + "(来自:android客户端)</span>")
				.appendTo(name_p);
	} else {
		$("<span class='com_op_time'>" + data.reTime + "</span>").appendTo(
				name_p);
	}
	
	$("<div class='comment_content_word'>" + data.content+"</div>").appendTo(this.comments_content);
	
	this.comments_op = $("<div class='comments_op'></div>").appendTo(
			this.comments_content);

	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op).bind("click", {
		data : data
	}, this.showReForm);
	$("<div class='clear'></div>").appendTo(this.comments_op);
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
	var content = data.content;
	for ( var emo in emotion_data) {
		content = content.replace(emo, "&nbsp;<img src='" + global.realPath
				+ "/images/emotions/" + emotion_data[emo] + "'>&nbsp;");
	}
	this.comment_sub = $("<div class='comtent_sub'></div>");
	$("<a name=re" + data.id + ">").appendTo(this.comment_sub);
	this.ui_avatar = $(
			"<div class='ui_avatar'><img src='"+global.realPath +"/upload/"+data.authorIcon
					+ "' width='40'></div>").appendTo(this.comment_sub);
	this.comments_content_sub = $("<div class='comments_content_sub'></div>")
			.appendTo(this.comment_sub);
	$("<div class='clear'></div>").appendTo(this.comment_sub);
	var name_p = $("<div class='name_p'></div>").appendTo(this.comments_content_sub);
	$(
			"<a href='"+global.realPath+"/user/"
					+ data.authorid
					+ "'>"
					+ data.authorName
					+ "</a><span class='re_icon'></span><span class='at_name'>" + data.atUserName
					+ "</span>").appendTo(name_p);
	if (data.sourceFrom == "A") {
		$("<span class='com_sub_op_time'>" + data.reTime + "(来自:android客户端)</span>")
				.appendTo(name_p);
	} else {
		$("<span class='com_sub_op_time'>" + data.reTime + "</span>").appendTo(
				name_p);
	}
	$("<div class='comment_content_word_sub'>"+ content+"</div>").appendTo(this.comments_content_sub);
	
	this.comments_op_sub = $("<div class='comments_op_sub'></div>").appendTo(
			this.comments_content_sub);

	$("<a href='javascript:void(0)' class='com_op_link'>回复</a>").appendTo(
			this.comments_op_sub).bind("click", {
		data : data
	}, this.showReForm);
	
	$("<div class='clear'></div>").appendTo(this.comments_op_sub);
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
	this.textarea = $("<textarea id='talkcontent'></textarea>").appendTo(
			$("<div class='comment_form_textarea'></div>").appendTo(
					this.recoment_form_panel));
	this.checkCode_area = $("<div class='comment_form_panel'></div>").appendTo(
			this.recoment_form_panel);
	$('<div class="talkop"><a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a></div>').appendTo(this.checkCode_area);
	var comment_checkcode_rebtn = $(
			"<div class='comment_checkcode_rebtn'></div>").appendTo(
			this.checkCode_area);
	$("<div class='clear'></div>").appendTo(this.checkCode_area);
	$("<a href='javascript:void(0)'>回复</a>").bind("click", {
		data : data,
		reCotent : this.textarea
	}, this.subReComent).appendTo(comment_checkcode_rebtn);
	$("<img src='"+global.realPath+"/images/load.gif' style='display:none'>").appendTo(
			comment_checkcode_rebtn);
	createEmotion().appendTo(this.checkCode_area);
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


function createEmotion(){
	var pm_emotion="";
	if($("#pm_emotion_cnt")[0]!=null){
		pm_emotion = $("#pm_emotion_cnt");
	}else{
		pm_emotion = $('<div class="pm_emotion" id="pm_emotion_cnt">');
		var pm_emotion_panel = $('<div class="pm_emotion_panel"></div>').appendTo(pm_emotion);
		var pm_emotions_bd = $('<div class="pm_emotions_bd" style="width:300px;"></div>').appendTo(pm_emotion_panel);
		$('<a href="javascript:void(0)" title="[围观]"><img src="'+global.realPath+'/images/emotions/wg_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[威武]"><img src="'+global.realPath+'/images/emotions/vw_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[给力]"><img src="'+global.realPath+'/images/emotions/geili_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[浮云]"><img src="'+global.realPath+'/images/emotions/fuyun_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[奥特曼]"><img src="'+global.realPath+'/images/emotions/otm_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[兔子]"><img src="'+global.realPath+'/images/emotions/rabbit_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[熊猫]"><img src="'+global.realPath+'/images/emotions/panda_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[飞机]"><img src="'+global.realPath+'/images/emotions/travel_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[冰棍]"><img src="'+global.realPath+'/images/emotions/ice.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[干杯]"><img src="'+global.realPath+'/images/emotions/cheer.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[orz]"><img src="'+global.realPath+'/images/emotions/orz1_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[囧]"><img src="'+global.realPath+'/images/emotions/j_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[风扇]"><img src="'+global.realPath+'/images/emotions/fan.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[呵呵]"><img src="'+global.realPath+'/images/emotions/smile.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[嘻嘻]"><img src="'+global.realPath+'/images/emotions/tooth.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[哈哈]"><img src="'+global.realPath+'/images/emotions/laugh.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[爱你]"><img src="'+global.realPath+'/images/emotions/love.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[晕]"><img src="'+global.realPath+'/images/emotions/dizzy.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[泪]"><img src="'+global.realPath+'/images/emotions/sad.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[馋嘴]"><img src="'+global.realPath+'/images/emotions/cz_thumb.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[抓狂]"><img src="'+global.realPath+'/images/emotions/crazy.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[哼]"><img src="'+global.realPath+'/images/emotions/hate.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[抱抱]"><img src="'+global.realPath+'/images/emotions/bb_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[可爱]"><img src="'+global.realPath+'/images/emotions/tz_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[怒]"><img src="'+global.realPath+'/images/emotions/angry.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[汗]"><img src="'+global.realPath+'/images/emotions/sweat.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[困]"><img src="'+global.realPath+'/images/emotions/sleepy.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[害羞]"><img src="'+global.realPath+'/images/emotions/shame_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[睡觉]"><img src="'+global.realPath+'/images/emotions/sleep_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[钱]"><img src="'+global.realPath+'/images/emotions/money_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[偷笑]"><img src="'+global.realPath+'/images/emotions/hei_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[酷]"><img src="'+global.realPath+'/images/emotions/cool_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[衰]"><img src="'+global.realPath+'/images/emotions/cry.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[吃惊]"><img src="'+global.realPath+'/images/emotions/cj_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[闭嘴]"><img src="'+global.realPath+'/images/emotions/bz_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[鄙视]"><img src="'+global.realPath+'/images/emotions/bs2_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[挖鼻屎]"><img src="'+global.realPath+'/images/emotions/kbs_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[花心]"><img src="'+global.realPath+'/images/emotions/hs_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[鼓掌]"><img src="'+global.realPath+'/images/emotions/gz_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[失望]"><img src="'+global.realPath+'/images/emotions/sw_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[思考]"><img src="'+global.realPath+'/images/emotions/sk_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[生病]"><img src="'+global.realPath+'/images/emotions/sb_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[亲亲]"><img src="'+global.realPath+'/images/emotions/qq_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[怒骂]"><img src="'+global.realPath+'/images/emotions/nm_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[太开心]"><img src="'+global.realPath+'/images/emotions/mb_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[懒得理你]"><img src="'+global.realPath+'/images/emotions/ldln_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[右哼哼]"><img src="'+global.realPath+'/images/emotions/yhh_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[左哼哼]"><img src="'+global.realPath+'/images/emotions/zhh_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[嘘]"><img src="'+global.realPath+'/images/emotions/x_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[委屈]"><img src="'+global.realPath+'/images/emotions/wq_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[吐]"><img src="'+global.realPath+'/images/emotions/t_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[可怜]"><img src="'+global.realPath+'/images/emotions/kl_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[打哈气]"><img src="'+global.realPath+'/images/emotions/k_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[黑线]"><img src="'+global.realPath+'/images/emotions/h_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[顶]"><img src="'+global.realPath+'/images/emotions/d_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[疑问]"><img src="'+global.realPath+'/images/emotions/yw_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[握手]"><img src="'+global.realPath+'/images/emotions/ws_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[耶]"><img src="'+global.realPath+'/images/emotions/ye_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[good]"><img src="'+global.realPath+'/images/emotions/good_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[弱]"><img src="'+global.realPath+'/images/emotions/sad_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[不要]"><img src="'+global.realPath+'/images/emotions/no_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[ok]"><img src="'+global.realPath+'/images/emotions/ok_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[赞]"><img src="'+global.realPath+'/images/emotions/z2_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[来]"><img src="'+global.realPath+'/images/emotions/come_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[蛋糕]"><img src="'+global.realPath+'/images/emotions/cake.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[心]"><img src="'+global.realPath+'/images/emotions/heart.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[伤心]"><img src="'+global.realPath+'/images/emotions/unheart.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[钟]"><img src="'+global.realPath+'/images/emotions/clock_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[猪头]"><img src="'+global.realPath+'/images/emotions/pig.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[话筒]"><img src="'+global.realPath+'/images/emotions/m_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[月亮]"><img src="'+global.realPath+'/images/emotions/moon.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[下雨]"><img src="'+global.realPath+'/images/emotions/rain.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[太阳]"><img src="'+global.realPath+'/images/emotions/sun.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[蜡烛]"><img src="'+global.realPath+'/images/emotions/lazu_org.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[礼花]"><img src="'+global.realPath+'/images/emotions/bingo_thumb.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<a href="javascript:void(0)" title="[玫瑰]"><img src="'+global.realPath+'/images/emotions/rose.gif"></a>').appendTo(pm_emotions_bd).bind("click",inserEmotion);
		$('<div style="clear:both;"></div>').appendTo(pm_emotions_bd);
		
		$(document).click(function() {
			$('#pm_emotion_cnt').hide();
		});
	}
	return pm_emotion;
}

function inserEmotion(){
	var curValue = $("#talkcontent").val();
	$("#talkcontent").val(curValue + $(this).attr("title"));
	$("#pm_emotion_cnt").hide();
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
				$(".outerHeight pre").each(function () {
			        var $this = $(this);
			        if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
			            var lang = $this.attr("class").split(';')[0].split(':')[1];
			            $this.attr('name', 'precode');
			            $this.attr('class', lang);
			        }
			    });
			   dp.SyntaxHighlighter.HighlightAll('precode');
				
				tipsInfo("2分已到碗里");
			}
		}
	});
}