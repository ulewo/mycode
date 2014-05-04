$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	topicmanage.initCategory();
});
//初始化板块下拉框
topicmanage.initCategory = function(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "../category?gid="+topicmanage.gid,// 请求的action路径
		success : function(data) {
			$('#category').combobox({
			     valueField:"categoryId",
			     textField:"name",
			     data:data.rows,
			     panelHeight:"auto"
			  });
		}
	});
};
//搜索
topicmanage.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../topics?gid="+topicmanage.gid;
	  $( "#datagrid").datagrid( 'load' );
}
//修改
topicmanage.edit = function(){
	var checkedRows = $("#datagrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length != 1){
		$.messager.alert("提示","请选择一条记录!","warning");
		return;
	}
	$("#editFrame").attr("src",'../editTopic?topicId='+checkedRows[0].topicId+'&gid='+checkedRows[0].gid);
	ulewo_common.loading("show","正在拼命的为你加载......");
	$("#editDialog").window('open'); 
};

topicmanage.formatTitle = function(val,row){
	var grade = row.grade;
	var essence = row.essence;
	var title = "";
	if(grade=="2"){
		title = title+"<img src='../../images/zding.gif'/>&nbsp;";
	}
	if(essence=="Y"){
		title = title+"<img src='../../images/jing.gif'/>&nbsp;";
	}
	title = title+row.title;
	return title;
};


function reload(){
	$("#editDialog").window("close");
	ulewo_common.tipsInfo("保存成功");
	$( "#datagrid").datagrid( 'load' );
};
function onloadComplete(){
	ulewo_common.loading();
};

function closeWindow(){
	$("#editDialog").window("close");
};

//删除
topicmanage.deleteTopic = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.topicId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":topicmanage.gid
		    		},
		    		url : "../deleteTopic.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("删除成功",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};

//置顶
topicmanage.topTopic = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要置顶的文章",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几篇文章置顶吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.topicId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":topicmanage.gid,
		    		},
		    		url : "../topTopic.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("设置成功",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};

topicmanage.topTopicCancel = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要取消置顶的文章",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几篇文章取消置顶吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.topicId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":topicmanage.gid,
		    		},
		    		url : "../topTopicCancel.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("设置成功",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};

//精华
topicmanage.essenceTopic = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要加精华的文章",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几篇文章设为精华吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.topicId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":topicmanage.gid
		    		},
		    		url : "../essenceTopic.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("设置成功",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};

topicmanage.essenceTopicCancel = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要取消精华的文章",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几篇文章取消精华吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.topicId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":topicmanage.gid
		    		},
		    		url : "../essenceTopicCancel.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("设置成功",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};