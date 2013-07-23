var GlobalVar = {};
GlobalVar.url = "";
GlobalVar.timeRange = "";
GlobalVar.type = "";
GlobalVar.shadow = "";
$(function() {
	$(window).bind(
			"scroll",
			function(event) {
				// 滚动条到网页头部的 高度，兼容ie,ff,chrome
				var scrollTop = document.documentElement.scrollTop
						+ document.body.scrollTop;
				if (scrollTop > 185) {
					$("#right_con").css({
						"position" : "fixed"
					});
				} else {
					$("#right_con").css({
						"position" : "relative"
					});
				}

			});
	$(document).click(function(event) {
		$('.face_panel').eq(0).hide();
	});
	$("#top_menue_first").bind("mouseover", function() {
		var position = $(this).position();
		$("#time_select").css({
			"left" : position.left + 1,
			"top" : position.top + 40
		})
		$('#time_select').show();
	});
	$(document).click(function(event) {
		$('#time_select').hide();
	});
	$(".face").bind("click", {
		textarea : $("#re_textarea")
	}, showFace);
	loadArticle(1);
})
function ArticlePanel(article) {
	this.articlePanel = $("<div class='articlePanel'>");// 最外层的div
	// 用户信息 时间 标签
	this.topPanel = $("<div class='top'>").appendTo(this.articlePanel);
	if (article.uid != "") {// 如果用户信息不为空就展示用户信息
		$("<div class='top_user_avator'>").html(
				"<img src='" + GlobalVar.url + article.avatar + "'/>")
				.appendTo(this.topPanel);
		$("<div class='top_user_name'>").html(
				"<a href='user?uid=" + article.uid + "'>" + article.userName
						+ "</a>").appendTo(this.topPanel);
	}
	// 时间
	$("<div class='top_user_time'>").html(article.postTime.substring(0, 19))
			.appendTo(this.topPanel);
	// 详情
	$(
			"<div class='top_user_name'><a href='detail?id=" + article.id
					+ "'>详情</a></div>").appendTo(this.topPanel);
	// 标签
	this.tagArea = $("<div class='tagArea'></div>").appendTo(this.topPanel); // 标签
	var tags = article.tags;
	for ( var i = 0; i < tags.length; i++) {
		$("<a href='##'>" + tags[i] + "</a>").appendTo(this.tagArea);
	}

	$("<div class='clear'></div>").appendTo(this.topPanel);
	this.contentPanel = $("<div class='content'>" + article.content + "</div>") // 内容
	.appendTo(this.articlePanel);
	// 如果有图片
	if (article.imgUrl != "") {
		this.imgArea = $("<div class='imgArea'></div>").appendTo(
				this.articlePanel);
		this.img = $(
				"<img src='" + GlobalVar.url + "upload/small/" + article.imgUrl
						+ "' class='small hover'/>").attr("type", "small")
				.appendTo(this.imgArea);
		this.img.bind("click", function() {
			if ($(this).attr("type") == "small") {
				var _this = $(this);
				_this.attr("src", "images/load.gif");
				var browser = getBrowserType();
				var img = new Image();
				img.src = GlobalVar.url + "upload/big/" + article.imgUrl;
				if (browser == "IE") {
					if (img.onreadystatechange != null) {
						img.onreadystatechange = function() {
							if (img.readyState == "complete") {
								_this.attr("src", img.src);
								_this.attr("type", "big");
								_this.removeClass("small");
								_this.addClass("big");
							}
						}
					} else {
						_this.attr("src", img.src);
						_this.attr("type", "big");
						_this.removeClass("small");
						_this.addClass("big");
					}

				} else if (browser == "others") {
					img.onload = function() {
						if (img.complete) {
							_this.attr("src", img.src);
							_this.attr("type", "big");
							_this.removeClass("small");
							_this.addClass("big");
						}
					}
				}
			} else if ($(this).attr("type") == "big") {
				$(this).attr("src",
						GlobalVar.url + "upload/small/" + article.imgUrl);
				$(this).attr("type", "small");
				$(this).removeClass("big");
				$(this).addClass("small");
			}

		});
	}
	if (article.videoUrl != "" && article.medioType == "V") {
		$(
				"<div><embed src='"
						+ article.videoUrl
						+ "' quality='high' width='600' height='500' align='middle' allowScriptAccess='always' allowFullScreen='true' mode='transparent' type='application/x-shockwave-flash'></embed></div>")
				.appendTo(this.articlePanel);
	}
	this.opArea = $("<div class='opArea'>").appendTo(this.articlePanel);
	this.countArea = $("<div class='countArea'></div>").appendTo(this.opArea);// 顶，踩
	this.up = $(
			"<a href='javascript:void(0)' class='up' title=" + article.up + ">"
					+ article.up + "</a>").appendTo(this.countArea);
	this.down = $(
			"<a href='javascript:void(0)' class='down' title=" + article.down
					+ ">" + article.down + "</a>").appendTo(this.countArea);

	// 分享
	this.shareCon = $("<div class='sharecon'>").appendTo(this.opArea);
	$("<span class='sharetit'>分享:</span>").appendTo(this.shareCon);
	this.qqshare = $(
			"<a href='javascript:void(0)' class='qqshare' title='分享到QQ空间'></a>")
			.appendTo(this.shareCon).bind("click", {
				article : article,
				type : "qqspace"
			}, share);
	this.qqwbshare = $(
			"<a href='javascript:void(0)' class='qqwbshare' title='分享到腾讯微博'></a>")
			.appendTo(this.shareCon).bind("click", {
				article : article,
				type : "qqwb"
			}, share);
	this.sinawbshare = $(
			"<a href='javascript:void(0)' class='sinawbshare' title='分享到新浪微博'></a>")
			.appendTo(this.shareCon).bind("click", {
				article : article,
				type : "sinawb"
			}, share);
	this.renrenshear = $(
			"<a href='javascript:void(0)' class='renrenshare' title='分享到人人网'></a>")
			.appendTo(this.shareCon).bind("click", {
				article : article,
				type : "renren"
			}, share);

	// 回复
	this.replyCon = $("<div class='replyCon'>").appendTo(this.opArea);
	this.reply = $("<a href='javascript:void(0)' onfocus='this.blur()'>评论</a>")
			.appendTo(this.replyCon);
	this.reCountNum = $("<span>" + article.reCount + "</span>").appendTo(
			this.replyCon);
	$("<div class='clear'>").appendTo(this.opArea);

	// 评论区域
	this.replayCon = $("<div class='replayCon'>").attr("ishide", "hide")
			.appendTo(this.articlePanel);
	// 评论发布区
	this.replayform = $("<div class='replayform'>").appendTo(this.replayCon);
	this.textarea = $(
			"<textarea class='re_textarea' id='re_textarea' onselect='setCaret(this);'onclick='setCaret(this);'onkeyup='setCaret(this);'></textarea>")
			.appendTo(this.replayform);
	this.replay_op = $("<div class='replay_op'></div>").appendTo(
			this.replayform);
	;
	this.face_link = $(
			"<a href='javascript:void(0)' onfocus='this.blur()'>表情</a>")
			.appendTo(this.replay_op);
	this.face_link.bind("click", {
		textarea : this.textarea
	}, showFace);
	this.reBtn = $("<a href='javascript:void(0)' class='reBtn'>发表</a>")
			.appendTo(this.replay_op);
	$("<div class='clear'></div>").appendTo(this.replay_op);
	// 评论
	this.replaylist = $("<div>评论加载中.......</div>").appendTo(this.replayCon);

	// 如果已经操作就把按钮灰色
	if (article.haveOper) {
		this.up.removeClass("up");
		this.up.addClass("upno");
		this.down.removeClass("down");
		this.down.addClass("downno");
	} else {// 否则添加点击事件
		this.up.bind("click", {
			articleid : article.id,
			type : "U",
			own : this.up,
			other : this.down
		}, this.upOrDown);

		this.down.bind("click", {
			articleid : article.id,
			type : "D",
			own : this.down,
			other : this.up
		}, this.upOrDown);
	}
	// 评论绑定点击事件
	this.reply.bind("click", {
		articleid : article.id,
		replayCon : this.replayCon,
		replaylist : this.replaylist
	}, this.replay);
	// 发布评论
	this.reBtn.bind("click", {
		replaylist : this.replaylist,
		articleid : article.id,
		textarea : this.textarea,
		reCountNum : this.reCountNum
	}, this.reArticleDo);
}

ArticlePanel.prototype = {
	getHtml : function() {
		return this.articlePanel;
	},
	upOrDown : function(event) {
		var _this = event.data.own;
		var other = event.data.other
		var id = event.data.articleid;
		var type = event.data.type;
		if (type == "U") {
			_this.removeClass("up");
			_this.addClass("upno");
			other.removeClass("down");
			other.addClass("downno");
		} else if (type == "D") {
			_this.removeClass("down");
			_this.addClass("downno");
			other.removeClass("up");
			other.addClass("upno");
		}
		$(_this).unbind("click", this.upOrDown);
		other.unbind("click", this.upOrDown);
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			data : {
				id : id,
				type : type
			},
			dataType : "json",
			url : 'operation',// 请求的action路径
			success : function(data) {
				if (data.result == "success") {
					$(_this).attr("disabled", true);
					var count = parseInt($(_this).attr("title"))
					$(_this).html(count + 1);
					$(_this).attr("title", count + 1);
				} else if (data.result == "haveOp") {
					alert("你已经表态了");
				} else {
					alert("系统异常，请稍后再试");
				}
			}
		});
	},
	replay : function(event) {
		var replaycon = event.data.replayCon;
		var replaylist = event.data.replaylist;
		var articleId = event.data.articleid;
		replaycon.show();
		if (replaycon.attr("ishide") == "hide") {
			replaycon.attr("ishide", "show");
			$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				data : {
					articleid : articleId
				},
				dataType : "json",
				url : 'reArticleList',// 请求的action路径
				success : function(data) {
					if (data.result == "success") {
						replaylist.html("");
						for ( var i = 0; i < data.list.length; i++) {
							new ReArticle(data.list[i], i + 1).repanel
									.appendTo(replaylist);
						}
					} else {
						alert("系统异常，请稍后再试");
					}
				}
			});
		} else if (replaycon.attr("ishide") == "show") {
			replaycon.hide();
			replaycon.attr("ishide", "hide");
		}
	},
	reArticleDo : function(event) {
		var articleid = event.data.articleid;
		var replaylist = event.data.replaylist;
		var textarea = event.data.textarea;
		var reCountNum = event.data.reCountNum;
		var reCount = parseInt(reCountNum.html());
		if (textarea.val().trim() == "") {
			alert("评论内容不能为空");
			textarea.focus();
			return;
		}
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			data : {
				articleid : articleid,
				content : textarea.val().trim()
			},
			dataType : "json",
			url : 'reArticle',// 请求的action路径
			success : function(data) {
				if (data.result == "success") {
					new ReArticle(data.article,
							replaylist.children().length + 1).repanel
							.appendTo(replaylist);
					reCountNum.html(reCount + 1);
					textarea.val("");
				} else if (data.result == "frequently") {
					alert("你回复的太频繁，请稍后再试");
				} else {
					alert("系统异常，请稍后再试");
				}
			}
		});
	}
}

function ReArticle(reArticle, num) {
	var avatar = GlobalVar.url + "upload/avatar/" + reArticle.avatar;
	var uid = reArticle.uid;
	var userName = reArticle.userName;
	var content = reArticle.content;
	if (uid == "") {
		userName = "匿名";
		avatar = "images/default.gif";
	}
	this.repanel = $("<div class='re_panel'>");
	this.imgArea = $(
			"<div class='re_img_area'><img src='" + avatar
					+ "'width='30'></div>").appendTo(this.repanel);
	this.recontent = $("<div class='re_content'>").appendTo(this.repanel);
	if (uid == "") {
		$("<span class='re_username'>" + userName + "</span>").appendTo(
				this.recontent);
	} else {
		$(
				"<a href='user?uid=" + uid + "' class='re_username'>"
						+ userName + "</a>").appendTo(this.recontent);
	}
	$("<span class='re_content_body'>" + content + "</span>").appendTo(
			this.recontent);
	$("<div class='clear'></div>").appendTo(this.recontent);
	this.louarea = $("<div class='lou_area'>" + num + "</div>").appendTo(
			this.repanel);
	$("<div class='clear'>").appendTo(this.repanel);
}

// 分享代码
/* title是标题，rLink链接，site分享内容，pic分享图片路径 */
/* 新浪微博 */
function share(event) {
	var type = event.data.type;
	var article = event.data.article;
	var title = article.content.substring(0, 150);
	var rLink = realPath + "detail?id=" + article.id;
	var site = title;
	var pic = realPath + "upload/big/" + article.imgUrl;
	if (type == "qqspace") {// qq空间
		shareQzone(title, rLink, title, site, pic);
		// '测试标题','http://www.ps-css.com','测试内容','转贴网站','图片地址'
	} else if (type == "qqwb") {// qq微博
		shareToWb(title, rLink, site, pic);
	} else if (type == "sinawb") {// 新浪微博
		shareTSina(title, rLink, site, pic);
	} else if (type == "renren") {// 分享到人人网
		shareRR(title, rLink, title, pic);
	}
}

/* QQ空间 */
function shareQzone(title, rLink, summary, site, pic) {
	window
			.open(
					'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title='
							+ encodeURIComponent(title) + '&url='
							+ encodeURIComponent(rLink) + '&summary='
							+ encodeURIComponent(summary) + '&site='
							+ encodeURIComponent(site) + '&pics='
							+ encodeURIComponent(pic), '_blank',
					'scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes')
}
/* 腾讯微博 */
function shareToWb(title, rLink, site, pic) {
	window
			.open('http://v.t.qq.com/share/share.php?url='
					+ encodeURIComponent(rLink) + '&title=' + encodeURI(title)
					+ '&appkey=' + encodeURI(site) + '&pic=' + encodeURI(pic),
					'_blank',
					'scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes')
}
/* 新浪微博 */
function shareTSina(title, rLink, site, pic) {
	window
			.open('http://service.weibo.com/share/share.php?title='
					+ encodeURIComponent(title) + '&url='
					+ encodeURIComponent(rLink) + '&appkey='
					+ encodeURIComponent(site) + '&pic='
					+ encodeURIComponent(pic), '_blank',
					'scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes')
}

/* 人人 */
function shareRR(title, rLink, summary, pic) {
	window
			.open('http://share.renren.com/share/buttonshare.do?title='
					+ encodeURIComponent(title) + '&link='
					+ encodeURIComponent(rLink) + '&description='
					+ encodeURIComponent(summary) + '&pic='
					+ encodeURIComponent(pic), '_blank',
					'scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes');
}

function getBrowserType() {
	var browser = navigator.userAgent.indexOf("MSIE") > 0 ? 'IE' : 'others';
	return browser;
}

function faceOver(event) {
	var num = event.data.num;
	var img = "images/face/t_000" + num + ".gif";
	if (num >= 10) {
		img = "images/face/t_00" + num + ".gif";
	}

	$(this).addClass("face_single_hover");
	var left = $(this).position().left;
	if (left < 184) {
		$("#show_panel").css({
			"left" : "305px"
		});
	} else {
		$("#show_panel").css({
			"left" : "10px"
		});
	}
	$("#show_panel").show();
	$("#face_show_img").attr("src", img);
}

function faceOut() {
	$(this).removeClass("face_single_hover");
	$("#show_panel").hide();
}

function insertFace(event) {
	var num = event.data.num;
	var img = "[face:000" + num + "]";
	if (num >= 10) {
		img = "[face:00" + num + "]";
	}
	insertAtCaret(event.data.textarea[0], img);
	// insertAtCaret(img);
	$(".face_panel").eq(0).hide();
	event.data.textarea.focus();
}

function setCaret(textObj) {
	if (textObj.createTextRange) {
		textObj.caretPos = document.selection.createRange().duplicate();
	}
}
function insertAtCaret(textObj, textFeildValue) {
	if (document.all) {
		if (textObj.createTextRange && textObj.caretPos) {
			var caretPos = textObj.caretPos;
			caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == '   ' ? textFeildValue
					+ '   '
					: textFeildValue;
		} else {
			textObj.value = textFeildValue;
		}
	} else {
		if (textObj.setSelectionRange) {
			var rangeStart = textObj.selectionStart;
			var rangeEnd = textObj.selectionEnd;
			var tempStr1 = textObj.value.substring(0, rangeStart);
			var tempStr2 = textObj.value.substring(rangeEnd);
			textObj.value = tempStr1 + textFeildValue + tempStr2;
		}
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
				"<li><a href='javascript:loadArticle(1)' class='prePage'>第一页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + (page - 1)
						+ ")'>上一页</a></li>").appendTo(this.ulPanle);
	} else {
		$("<li><span>第一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>上一页</span></li>").appendTo(this.ulPanle);
	}
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadArticle(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a href='javascript:loadArticle(" + (page + 1)
						+ ")'>下一页</a></li>").appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + totalPage
						+ ")' class='prePage'>最后页</a></li>").appendTo(
				this.ulPanle);
	} else {
		$("<li><span>下一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>最后页</span></li>").appendTo(this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}
function showShadow() {
	if (GlobalVar.shadow == "") {
		GlobalVar.shadow = $("<div class='shadow'></div>").appendTo($("body"));
	} else {
		$(GlobalVar.shadow).show();
	}
	var height = $(document).height();
	$(GlobalVar.shadow).css({
		"height" : height + "px"
	});
}

function showFace(event) {
	event.stopPropagation();
	$(".face_panel").remove();
	var face_panel = $("<div class='face_panel'></div>").appendTo(
			$(this).parent());
	var show_panel = $(
			"<div id='show_panel'><img src='' id='face_show_img'/></div>")
			.appendTo(face_panel);
	var panel_ul = $("<ul></ul>").appendTo(face_panel);
	var num = 0;
	for ( var i = 1; i <= 40; i++) {
		var li = $(
				"<li class='face_single' style='background-position: left "
						+ num + "px;'></li>").appendTo(panel_ul);
		li.bind("mouseover", {
			num : i
		}, faceOver);
		li.bind("mouseout", faceOut);
		li.bind("click", {
			num : i,
			textarea : event.data.textarea
		}, insertFace);
		num = num - 25;
	}
}