function addArticle() {
	var content = $("#content").val();
	var tag = $("#tag").val();
	var img = $("#imgUrl").val();
	var medio_type = $("input[name='medio_type']:checked").val();
	var video_url = $("#video_url").val();
	if (content.trim() == "") {
		alert("请填写内容");
		$("#content").focus();
		return;
	}
	if ($("#agreement").attr("checked") != "checked") {
		alert("同意投稿需知才能投稿");
		return;
	}
	$("#subCon").html("<img src='images/loading.gif'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data : {
			content : content,
			img : img,
			tag : tag,
			medio_type : medio_type,
			video_url : video_url
		},
		dataType : "json",
		url : 'addArticle',// 请求的action路径
		success : function(data) {
			if (data.result == "success") {
				if (data.loginType == "login") {
					alert("发布成功");
					document.location.href = "new";
				} else if (data.loginType == "nologin") {
					alert("发布成功,我们会尽快审核");
					document.location.href = "new";
				}
			} else if (data.result == "videonotfound") {
				alert("视频没有找到，请输入正确的视频地址");
			} else {
				alert("系统异常，发布失败，你可以稍等片刻之后再来发布");
			}
			$("#subCon").html(
					"<a href='javascript:addArticle()' class='subtn'>投递</a>");
		}
	});
}
function initImg(imgUrl) {
	$("#imgUrl").val(imgUrl);
}

function setMedioType(obj) {
	var value = $(obj).val();
	if (value == "P") {
		$("#img_tag").show();
		$("#video_tag").hide();
	} else if (value == "V") {
		$("#video_tag").show();
		$("#img_tag").hide();
	}
}