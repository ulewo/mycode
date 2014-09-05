var fastPost = {};
fastPost.group={};
$(function(){
	fastPost.loadGroup();
	$("#sub_article_btn4fast").bind("click",fastPost.submitForm);
	$("#topicType4fast").find('input:radio[name="topicType"]').bind("click",fastPost.showSurvey);
	$(".deleteIcon4fast").live("click",fastPost.deleteLine);
	$("input:radio[name='wowoType']").bind("click",fastPost.setGroupInfo);
	$("#group").change(function(){
		fastPost.loadCategory();
	});
});
//加载窝窝分类
fastPost.loadCategory = function(){
	$.ajax({
		async : true,
		cache : true,
		type : 'GET',
		dataType : "json",
		url : global.realPath + "/groupmanage/category?gid="+$("#group").val(),// 请求的action路径
		success : function(data) {
			$("#article_item4fast").children().remove();
			$("<option value='0'>全部分类</option>").appendTo($("#article_item4fast"));
			var rows = data.rows;
			for(var i=0,_len=rows.length;i<_len;i++){
				if(rows[i].allowPost=="Y"){
					$("<option value='"+rows[i].categoryId+"'>"+rows[i].name+"</option>").appendTo($("#article_item4fast"));
				}
			}
		}
	});
}
//设置窝窝下拉
fastPost.setGroupInfo = function(){
	var wowoType = $("input:radio[name='wowoType']:checked").val();
	var groups;
	if(wowoType=="0"){
		groups = fastPost.group.createdGroups;
	}else if(wowoType=="1"){
		groups = fastPost.group.joinedGroups;
	}
	$("#group").children().remove();
	$("<option value=''>==请选择窝窝==</option>").appendTo($("#group"));
	for(var i=0,_len=groups.length;i<_len;i++){
		$("<option value='"+groups[i].gid+"'>"+groups[i].groupName+"</option>").appendTo($("#group"));
	}
}

fastPost.loadGroup = function(){
	if(fastPost.group.createdGroups==null||fastPost.group.joinedGroups==null){
		$.ajax({
			async : true,
			cache : true,
			type : 'GET',
			dataType : "json",
			url : global.realPath + "/user/loadGroup.action",// 请求的action路径
			success : function(data) {
				fastPost.group.createdGroups = data.createdGroups;
				fastPost.group.joinedGroups = data.joinedGroups;
				fastPost.setGroupInfo();
			}
		});
	}
}

fastPost.showAddForm = function(){
	warm4fast("hide","");
	$("#article_title4fast").val("");
	$("#article_keyword4fast").val("");
	$("#attached_file_name4fast").val("");
	$("#attached_file4fast").val("");
	$("#uploadFrame4fast").show();
	//清空图片
	editor4fast.setContent("");
	//清除投票内容
	$("#topicType4fast0").attr("checked",true);
	$("#topicType4fast1").attr("checked",false);
	$("#survey_con4fast").hide();
	fastPost.clearSurvey();
}

fastPost.showSurvey = function(){
	var value =$(this).val();
	if(value=="0"){
		$("#survey_con4fast").hide();
	}else if(value=="1"){
		$("#survey_con4fast").show();
	}
	fastPost.clearSurvey();
}

fastPost.addSurveyLine = function(){
	if($("#surveyLine4fast").children().length>=20){
		alert("最多只能添加20个选项!");
		return ;
	}
	$('<div class="survey_input add"><input type="text" name="surveyTitle"><a href="javascript:void(0)" class="deleteIcon4fast"><img src="'+global.realPath+'/images/icon-del.png"></a></div>').appendTo($("#surveyLine4fast"));
}
fastPost.deleteLine = function(){
	$(this).parent().remove();
}


fastPost.clearSurvey = function(){
	$("#surveyLine4fast .add").remove();
	$("input:text[name='surveyTitle']").val("");
	$("#surveyType4fast0").attr("checked",true);
	$("#surveyType4fast1").attr("checked",false);
	$("#wdates").val("");
}

/*********取消***********/
fastPost.cancelAdd=function(){
	toolbar.closeToolbarCon();
}

fastPost.deleteFile = function(){
	$("#file_deleting").show();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			userId:global.userId,
			fileName:$("#attached_file4fast").val(),
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
fastPost.submitForm = function(){
	var subBtn = $("#sub_article_btn4fast");
	if(subBtn.attr("disable")=="disable"){
		return;
	}
	var article_title = $("#article_title4fast").val().trim();
	var article_item = $("#article_item4fast").val();
	var article_keyword = $("#article_keyword4fast").val().trim();
	var mark =  $("#mark").val().trim();
	
	if($("#group").val()==""){
		warm4fast("show","请选择窝窝");
		$("#group").focus();
		return;
	}
	
	if(article_title==""){
		warm4fast("show","标题不能为空");
		return;
	}
	
	if(article_title.length>150){
		warm4fast("show","标题不能超过150");
		return;
	}
	
	if(article_item==""){
		warm4fast("show","请选择分类");
		return;
	}
	
	if(article_keyword.length>150){
		warm4fast("show","关键字不能超过150");
		return;
	}
	if(!mark.isNumber()){
		warm4fast("show","下载积分只能是数字");
		return;
	}
	
	var infoError = false;
	if(UE.utils.trim(editor4fast.getContentTxt())==""){
		infoError = true;
		if(editor4fast.getContent().haveContent()){
			infoError = false;
		}
	}
	if(infoError&&$("#topicType4fast").find("input[name='topicType']:checked").val()=="0"){
		warm4fast("show","内容不能为空");
		return ;
	}
	if($("#topicType4fast").find("input[name='topicType']:checked").val()=="1"){
		if($("#wdates").val()==""){
			warm4fast("show","投票结束日期不能为空");
			return ;
		}
		if(ulewo_common.isStartLessThanEndDate($("#wdates").val(),ulewo_common.getCurDate())){
			warm4fast("show","投票结束日期必须大于当天");
			return ;
		}
		var titles = $("#surveyLine4fast").find("input[name='surveyTitle']");
		var notEmptyCount = 0;
		for(var i=0,length=titles.length;i<length;i++){
			if(titles.eq(i).val()!=""){
				notEmptyCount++;
			}
		}
		if(notEmptyCount<=1){
			warm4fast("show","投票选项必须大于1");
			return ;
		}
		
	}
	warm4fast("hide","");
	btnLoading(subBtn,"发表中<img src='"+global.realPath+"/images/load.gif' width='14'>");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#articleform4fast").serialize(),
		url : global.realPath+"/group/addTopic.action",// 请求的action路径
		success : function(data) {
			btnLoaded(subBtn,"发表帖子");
			if(data.result=="200"){
				$(".noinfo").remove();
				tipsInfo("5分已到碗里");
				setTimeout(function(){parent.closeToolbarCon()},2000);
			}else{
				warm4fast("show",data.msg);
			}
		}
	});
}


/*********附件上传回调函数***********/
function showFile(filePath){
	$("#attached_file").val(filePath);
	$("<div class='file_con'><a href='javascript:void(0)'>"+$("#attached_file_name").val()+"</a>&nbsp;&nbsp;<a href='javascript:fastPost.deleteFile()'>删除</a><img src='"+global.realPath+"/images/load.gif' id='file_deleting'></div>").appendTo($("#file_upload"));
	$("#uploadFrame").hide();
}

function setFileName(fileName){
	$("#attached_file_name").val(fileName);
}
