function preview(img, selection) {
	if (!selection.width || !selection.height)
		return;
	// 获取截取图片的高宽
	var width = $("#photo").width();
	var height = $("#photo").height();

	var scaleX = 100 / selection.width;
	var scaleY = 100 / selection.height;

	$('#preview img').css({
		width : Math.round(scaleX * width),
		height : Math.round(scaleY * height),
		marginLeft : -Math.round(scaleX * selection.x1),
		marginTop : -Math.round(scaleY * selection.y1)
	});

	$('#x1').val(selection.x1);
	$('#y1').val(selection.y1);
	$('#x2').val(selection.x2);
	$('#y2').val(selection.y2);
	$('#w').val(selection.width);
	$('#h').val(selection.height);
}

function initImage(imageName) {
	$("#groupicon").val(imageName);
	$("#photo").attr("src", "../upload/" + imageName);
	$("#preview>img").attr("src", "../upload/" + imageName);
	$("#imgCut").css({
		"display" : "block"
	});
	$("#cutarea").css("display", "inline-block");
	$("#ferret").css("display", "inline-block");
	$('img#photo').imgAreaSelect({
		x1 : 0,
		y1 : 0,
		x2 : 100,
		y2 : 100, // 以上四个参数设置初始化时选中最中间
		aspectRatio : '1:1', // 设置选择框等比例放大缩小
		fadeSpeed : 200,
		handles : true,
		onSelectChange : preview
	});
}

function saveUserIcon(userId) {
	if ($("#groupicon").val() == "") {
		alert("请先选择图片");
		return;
	}
	$("#load").css({
		"display" : "block"
	});
	$("#save").css({
		"display" : "none"
	});
	var userIcon = $("#groupicon").val();
	var x1 = $("#x1").val();
	var y1 = $("#y1").val();
	var width = $("#w").val();
	var height = $("#h").val();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"userId" : userId,
			"userIcon" : userIcon,
			"x1" : x1,
			"y1" : y1,
			"width" : width,
			"height" : height,
			"imgtype" : 0,
			"date" : new Date()
		},
		url : 'updateUserIcon.jspx',// 请求的action路径
		success : function(data) {
			if (data.result == "error") {
				art.dialog.tips('保存失败');
			} else {
				art.dialog.tips('保存成功');
				setTimeout("location.reload()", 1000);
			}
			$("#groupicon").val("");
			$("#load").css({
				"display" : "none"
			});
			$("#save").css({
				"display" : "block"
			});
		}
	});
}

function topic(userId, page) {
	if (page == "") {
		page = 1;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"userId" : userId,
			"page" : page
		},
		url : 'articles.jspx',// 请求的action路径
		success : function(data) {
			createInfor(data.rows);
			pagination(userId, data.total, data.page, data.pageTotal);
		}
	});
}

function createInfor(data) {
	$("#topictab").empty();
	// 创建列表
	for ( var i = 0; i < data.length; i++) {
		$(
				"<tr>" + "<td>" + data[i].title + "</td>" + "<td>"
						+ data[i].reNumber + "/" + data[i].readNumber + "</td>"
						+ "</tr>").appendTo("#topictab");
	}
}

function pagination(userId, totalNumber, nowPage, pageCount) {
	$("#topicPage").empty();
	var curPage = 1; // 当前页
	var pageTotal = 0; // 总页数
	var pageNum = 11; // 一栏显示多少页
	var beginNum = 0; // 起始页
	var endNum = 0; // 未页
	if (nowPage != "") {
		curPage = nowPage;
	}
	if (pageCount != "") {
		pageTotal = pageCount;
	}

	if (pageNum > pageTotal) {
		beginNum = 1;
		endNum = pageTotal;
	}

	if (curPage - pageNum / 2 < 1) {
		beginNum = 1;
		endNum = pageNum;
	} else {
		beginNum = curPage - pageNum / 2;
		endNum = curPage + pageNum / 2;
	}

	if (pageTotal - curPage < pageNum / 2) {
		beginNum = pageTotal - pageNum + 1;
	}

	if (beginNum < 1) {
		beginNum = 1;
	}

	if (endNum > pageTotal) {
		endNum = pageTotal;
	}
	var tds = "<tr>";
	if (curPage > 1) {
		tds = tds
				+ "<td><a href='javascript:topic(\""
				+ userId
				+ "\",1)'>首&nbsp;&nbsp;页</a></td><td><a href='javascript:topic(\""
				+ userId + "\"," + (curPage - 1) + ")'>上一页</a></td>";
	}
	for ( var i = beginNum; i <= endNum; i++) {
		if (pageTotal > 1) {
			if (i == curPage) {
				tds = tds + "<td style='background:red'><a href='####'>" + i
						+ "</a></td>";
			} else {
				tds = tds + "<td><a href='javascript:topic(\"" + userId + "\","
						+ i + ")'>" + i + "</a></td>";
			}
		}
	}
	if (curPage < pageTotal) {
		tds = tds + "<td><a href='javascript:topic(\"" + userId + "\","
				+ (curPage + 1)
				+ ")'>下一页</a></td><td><a href='javascript:topic(\"" + userId
				+ "\"," + pageTotal + ")'>尾&nbsp;&nbsp;页</a></td>";
	}
	tds = tds + "</tr>";
	$(tds).appendTo("#topicPage");
}