var _add = art.dialog.defaults;
_add["drag"]=true;
_add['lock'] = true;
_add['fixed'] = true;
_add['yesText'] = 'yes';
_add['noText'] = 'no';

function commend(id){
	art.dialog({
	title:'推荐文章',
	content:'<div style="padding:5px;"><iframe src="imageUpload.jsp" style="width: 100%; height:40px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-color: initial; border-image: initial; "></iframe><div>' +
			'<div style="padding:5px;">首页：<select id="u8part"><option value="rollimage">滚动图片</option><option value="groupthing">推荐文章</option><option value="latest">最新文章</option></select></div>' +
			'<div style="padding-bottom:10px;margin-top:5px"><div class="bbtn1" style="float:none"><a href="javascript:commendArticle('+id+')">推荐文章</a></div></div>' +
			'<input type="hidden" name="image" id="uploadImg">',
	fixed: true,
	width:"360px"
	}).content().className="aui_content aui_state_full";
}

function getImage(imgName){
	$("#uploadImg").val(imgName);
}


function commendArticle(id){
	var img = $("#uploadImg").val();
	var subcode = $("#u8part").val();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"id" : id,
			"image":img,
			"subCode":subcode,
			"sysCode":"index",
			"date":new Date()
		},
		url : 'commendArticle.jspx',// 请求的action路径
		success : function(data) {
			if(data.msg=="ok"){
				art.dialog.tips('推荐成功');
				location.reload();
			}else{
				art.dialog.tips('推荐失败');
			}
		}
	});
}