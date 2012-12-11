var GlobalVar = {};
GlobalVar.url = "http://pic.ulewo.com/";
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
	$(".face").bind("click", showFace);

	$(".imgArea >img").bind(
			"click",
			function() {
				if ($(this).attr("type") == "small") {
					var _this = $(this);
					_this.attr("src", "images/load.gif");
					var browser = getBrowserType();
					var img = new Image();
					img.src = GlobalVar.url + "upload/big/"
							+ $(this).attr("imgUrl");
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
					$(this).attr(
							"src",
							GlobalVar.url + "upload/small/"
									+ $(this).attr("imgUrl"));
					$(this).attr("type", "small");
					$(this).removeClass("big");
					$(this).addClass("small");
				}

			});
})

function up(id, upcount, downcount, obj) {
	upOrDown(id, "U", upcount, downcount, obj);
}

function down(id, upcount, downcount, obj) {
	upOrDown(id, "D", upcount, downcount, obj);
}

function upOrDown(id, type, upcount, downcount, obj) {
	var _this = obj;
	$
			.ajax({
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
						var parent = $(_this).parent();
						parent.html("");
						var _upcount = upcount;
						var _downcount = downcount;
						if (type == "U") {
							_upcount++;
						} else if (type == "D") {
							_downcount++;
						}
						$(
								"<a href='javascript:void(0)' class='upno' title='支持'>"
										+ _upcount + "</a>").appendTo(parent);
						$(
								"<a href='javascript:void(0)' class='downno' title='反对' style='margin-left:17px;'>"
										+ _downcount + "</a>").appendTo(parent);
					} else if (data.result == "haveOp") {
						alert("你已经表态了");
					} else {
						alert("系统异常，请稍后再试");
					}
				}
			});
}

function showRe(id, obj) {
	// 通过当前链接获取到评论区域的dom对象
	var replayCon = $(obj).parent().parent().parent().children().last();
	$(replayCon).toggle();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data : {
			articleid : id
		},
		dataType : "json",
		url : 'reArticleList',// 请求的action路径
		success : function(data) {
			$(replayCon).children(".replaylist").html("");
			if (data.result == "success") {
				for ( var i = 0; i < data.list.length; i++) {
					new ReArticle(data.list[i], i + 1).repanel.appendTo($(
							replayCon).children(".replaylist"));
				}
			} else {
				alert("系统异常，请稍后再试");
			}
		}
	});
}

function addReArticle(id, obj) {
	var replayform = $(obj).parent().parent();
	var textarea = $(replayform).children("textarea");
	// replay_op>replayform>replayCon>articlePanel>opArea>replyCon
	var reCountArea = $(obj).parent().parent().parent().parent().children(
			".opArea").children(".replyCon").children("span");
	var replaylist = $(replayform).parent().children(".replaylist");
	var articleid = id;
	var reCount = parseInt($(reCountArea).html());
	if (textarea.val().trim() == "") {
		alert("评论内容不能为空");
		textarea.focus();
		return;
	}
	$
			.ajax({
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
								$(replaylist).children().length + 1).repanel
								.appendTo(replaylist);
						$(reCountArea).html(reCount + 1);
						textarea.val("");
					} else {
						alert("系统异常，请稍后再试");
					}
				}
			});
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

function showFace(event) {
	event.stopPropagation();
	$(".face_panel").remove();
	var textarea = $(this).parent().parent().children("textarea");
	var face_panel = $("<div class='face_panel'></div>").appendTo(
			$(this).parent());
	$("<div id='show_panel'><img src='' id='face_show_img'/></div>").appendTo(
			face_panel);
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
			textarea : textarea
		}, insertFace);
		num = num - 25;
	}
}
function search(searchKey) {
	searchKey = encodeURI(encodeURI(searchKey));
	document.location.href = "search?searchKey=" + searchKey;
}
