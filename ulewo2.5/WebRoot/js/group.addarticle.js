$(function(){
	$("#new_article_p").bind("click",showAddForm);
	$("#sub_article_btn").bind("click",submitForm);
	$('input:radio[name="topicType"]').bind("click",showSurvey);
	$(".deleteIcon").live("click",deleteLine);
});

function showSurvey(){
	var value =$(this).val();
	if(value=="0"){
		$("#survey_con").hide();
	}else if(value=="1"){
		$("#survey_con").show();
	}
	clearSurvey();
}

function addSurveyLine(){
	if($("#surveyLine").children().length>=20){
		alert("最多只能添加20个选项!");
		return ;
	}
	$('<div class="survey_input add"><input type="text" name="surveyTitle"><a href="javascript:void(0)" class="deleteIcon"><img src="'+global.realPath+'/images/icon-del.png"></a></div>').appendTo($("#surveyLine"));
}
function deleteLine(){
	$(this).parent().remove();
}

function showAddForm(){
	$(this).hide();
	warm("hide","");
	$("#add_article").show();
	$("#article_title").val("");
	$("#article_keyword").val("");
	$("#attached_file_name").val("");
	$("#attached_file").val("");
	$("#content").val("");
	$("#uploadFrame").show();
	$(".file_con").remove();
	//清空图片
	$(".image_hidden").remove();
	editor.setContent("");
	//清除投票内容
	$("#topicType0").attr("checked",true);
	$("#topicType1").attr("checked",false);
	$("#survey_con").hide();
	clearSurvey();
}

function clearSurvey(){
	$("#surveyLine .add").remove();
	$("input:text[name='surveyTitle']").val("");
	$("#surveyType0").attr("checked",true);
	$("#surveyType1").attr("checked",false);
	$("#wdate").val("");
}

/*********取消***********/
function cancelAdd(){
	$("#new_article_p").show();
	$("#add_article").hide();
}

/*********附件上传回调函数***********/
function showFile(filePath){
	$("#attached_file").val(filePath);
	$("<div class='file_con'><a href='javascript:void(0)'>"+$("#attached_file_name").val()+"</a>&nbsp;&nbsp;<a href='javascript:deleteFile()'>删除</a><img src='"+global.realPath+"/images/load.gif' id='file_deleting'></div>").appendTo($("#file_upload"));
	$("#uploadFrame").hide();
}

function setFileName(fileName){
	$("#attached_file_name").val(fileName);
}

function deleteFile(){
	$("#file_deleting").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			userId:global.userId,
			fileName:$("#attached_file").val(),
			"time" : new Date()
		},
		url : global.realPath+"/group/deleteFile.action",// 请求的action路径
		success : function(data) {
			$("#file_deleting").hide();
			if(data.result="200"){
				$(".file_con").remove();
				$("#uploadFrame").show();
			}else{
				alert("删除失败");
			}
		}
	});
}


/********提交**********/
function submitForm(){
	var subBtn = $("#sub_article_btn");
	if(subBtn.attr("disable")=="disable"){
		return;
	}
	var article_title = $("#article_title").val().trim();
	var article_item = $("#article_item").val();
	var article_keyword = $("#article_keyword").val().trim();
	var mark =  $("#mark").val().trim();
	if(article_title==""){
		warm("show","标题不能为空");
		return;
	}
	
	if(article_title.length>150){
		warm("show","标题不能超过150");
		return;
	}
	
	if(article_item==""){
		warm("show","请选择分类");
		return;
	}
	
	if(article_keyword.length>150){
		warm("show","关键字不能超过150");
		return;
	}
	if(!mark.isNumber()){
		warm("show","下载积分只能是数字");
		return;
	}
	
	var infoError = false;
	if(UE.utils.trim(editor.getContentTxt())==""){
		infoError = true;
		if(editor.getContent().haveContent()){
			infoError = false;
		}
	}
	if(infoError&&$("#topicType").find("input[name='topicType']:checked").val()=="0"){
		warm("show","内容不能为空");
		return ;
	}
	if($("#topicType").find("input[name='topicType']:checked").val()=="1"){
		if($("#wdate").val()==""){
			warm("show","投票结束日期不能为空");
			return ;
		}
		if(ulewo_common.isStartLessThanEndDate($("#wdate").val(),ulewo_common.getCurDate())){
			warm("show","投票结束日期必须大于当天");
			return ;
		}
		var titles = $("#surveyLine").find("input[name='surveyTitle']");
		var notEmptyCount = 0;
		for(var i=0,length=titles.length;i<length;i++){
			if(titles.eq(i).val()!=""){
				notEmptyCount++;
			}
		}
		if(notEmptyCount<=1){
			warm("show","投票选项必须大于1");
			return ;
		}
		
	}
	warm("hide","");
	btnLoading(subBtn,"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#articleform").serialize(),
		url : global.realPath+"/group/addTopic.action",// 请求的action路径
		success : function(data) {
			btnLoaded(subBtn,"发表帖子");
			if(data.result=="200"){
				cancelAdd();
				$(".noinfo").remove();
				if($(".article_item").length>0){
					$(".article_item").eq(0).before(new ArticleItem(data.topic).article_item);
				}else{
					new ArticleItem(data.topic).article_item.appendTo($("#article_item_list"));
				}
				tipsInfo("5分已到碗里");
			}else{
				warm("show",data.msg);
			}
		}
	});
}

function ArticleItem(article){
	this.article_item = $("<div class='article_item'></div>");
	var article_tit = $("<div class='article_tit'>").appendTo(this.article_item);
	
	var article_title=$("<div class='article_title'>").appendTo(article_tit);
	$("<a href='"+global.realPath+"/group/"+article.gid+"/topic/"+article.topicId+"'>"+article.title+"</a>").appendTo(article_title);
	$("<span class='recount'>"+article.readCount+"/"+article.commentCount+"</span>").appendTo(article_title);
	
	var article_title=$("<div class='article_author'>").appendTo(article_tit);
	$("<a href='"+global.realPath+"/user/"+article.userId+"'>"+article.userName+"</a>").appendTo(article_title);
	$("<span class='article_posttime'>发表于&nbsp; "+article.showCreateTime+"</span>").appendTo(article_title);
	
	$("<div class='clear'></div>").appendTo(article_tit);
	
	$("<div class='article_summary'>"+article.summary+"</div>").appendTo(this.article_item);
	var images = article.images;
	if(images!=""&&images!=null){
		for(var i=0,length=images.length;i<length;i++){
			if(i>2){
				break;
			}
			$("<div class='article_attachedimg'><a href='"+global.realPath+"/group/"+article.gid+"/topic/"+article.id+"'><img src='"+images[i]+"' style='max-width:150px;'/></a></div>").appendTo(this.article_item);
		}
		$("<div class='clear'></div>").appendTo(this.article_item);
	}
}