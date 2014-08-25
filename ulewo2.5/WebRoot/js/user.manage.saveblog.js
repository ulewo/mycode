var userManageBlog = {};
userManageBlog.saveBlog = function(){
	var title = $("#title").val().trim();
	var keyWord = $("#keyWord").val().trim();
	if(title==""){
		ulewo_common.alert("标题不能为空",1);
		return;
	}
	
	if(title.length>150){
		ulewo_common.alert("标题不能超过150",1);
		return;
	}
	if(keyWord.length>150){
		ulewo_common.alert("关键字不能超过150",1);
		return;
	}
	var infoError = false;
	if(UE.utils.trim(editor.getContentTxt())==""){
		infoError = true;
		if(editor.getContent().haveContent()){
			infoError = false;
		}
	}
	if(infoError){
		ulewo_common.alert("内容不能为空",1);
		return ;
	}
	
	ulewo_common.loading("show","正在保存......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#saveForm").serialize(),
		url : global.realPath+"/manage/saveBlog.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				if(data.blog.newBlog){
					ulewo_common.tipsInfo("5分已到碗里",1);
				}
				$("#title").val("");
				$("#keyWord").val("");
				editor.setContent("");
				setTimeout("parent.document.location.href='../user/"+data.blog.userId+"/blog/"+data.blog.blogId+"'",2000);
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}


userManageBlog.updateBlog = function(){
	var title = $("#title").val().trim();
	var keyWord = $("#keyWord").val().trim();
	if(title==""){
		ulewo_common.alert("标题不能为空",1);
		return;
	}
	
	if(title.length>150){
		ulewo_common.alert("标题不能超过150",1);
		return;
	}
	if(keyWord.length>150){
		ulewo_common.alert("关键字不能超过150",1);
		return;
	}
	
	var infoError = false;
	if(UE.utils.trim(editor.getContentTxt())==""){
		infoError = true;
		if(editor.getContent().haveContent()){
			infoError = false;
		}
	}
	if(infoError){
		ulewo_common.alert("内容不能为空",1);
		return ;
	}
	
	ulewo_common.loading("show","正在保存......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#saveForm").serialize(),
		url : global.realPath+"/manage/saveBlog.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				ulewo_common.tipsInfo("保存成功",1);
				setTimeout("parent.parent.document.location.href='../user/"+data.blog.userId+"/blog/"+data.blog.blogId+"'",2000);
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}
