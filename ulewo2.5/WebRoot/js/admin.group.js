var adminGroup = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	loadCategory();
});
adminGroup.userIconFormate = function(value,rowData,index){
	return "<div style='width:50px;height:50px;text-align:center'><img src=../upload/"+rowData.groupIcon+" width='50'></div>";
};

//搜索
adminGroup.searchGroup = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="groups";
	  $( "#datagrid").datagrid( 'load' );
};
//删除成员
adminGroup.deleteGroup = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.gid);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(",")
		    		},
		    		url : "deleteGroups.action",// 请求的action路径
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


//精华
adminGroup.essenceGroup = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要加推荐的窝窝",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几个窝窝推荐吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.gid);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(",")
		    		},
		    		url : "essenceGroup.action",// 请求的action路径
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

adminGroup.essenceGroupCancel = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要取消推荐的窝窝",1);
		return;
	}
	 $.messager.confirm('确认','您确认要将这几个窝窝取消推荐吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.gid);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(",")
		    		},
		    		url : "essenceGroupCancel.action",// 请求的action路径
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


adminGroup.changeCategroy = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要修改分类的窝窝",1);
		return;
	}
	$("#categoryDialog").window('open'); 
};

function loadCategory(){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#group_info").serialize(),
		url : global.realPath+"/loadGroupCategory",// 请求的action路径
		success : function(data) {
			if(data.result=="200"){
					var select = $("#cate").bind("change",function(){
						var sub = $("#sub").empty();
						var children;
						for(var i=0,length=data.groupCateGroy.length;i<length;i++){
							if(data.groupCateGroy[i].groupCategoryId==$(this).children('option:selected').val()){
								children = data.groupCateGroy[i].children;
								break;
							}
						}
						for(var i=0,length=children.length;i<length;i++){
							$("<option value='"+children[i].groupCategoryId+"'>"+children[i].name+"</option>").appendTo(sub);
						}
					});
				for(var i=0,length=data.groupCateGroy.length;i<length;i++){
					$("<option value='"+data.groupCateGroy[i].groupCategoryId+"'>"+data.groupCateGroy[i].name+"</option>").appendTo(select);
				}
				var children = data.groupCateGroy[0].children;
				for(var i=0,length=children.length;i<length;i++){
					$("<option value='"+children[i].groupCategoryId+"'>"+children[i].name+"</option>").appendTo(sub);
				}
			}else{
				warm("show",data.msg);	
			}
		}
	});
};

adminGroup.updateGroupCategory = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	var key = [];
	$.each(rows,function(inex,item){
		key.push(item.gid);
	});
	var data =ulewo_common.convertArray($('#categoryForm').serializeArray());
	data.key = key.join(",");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:data,
		url : "updateGroupCategroy.action",// 请求的action路径
		success : function(data) {
			if(data.result=="200"){
				ulewo_common.tipsInfo("设置成功",1);
				$("#datagrid").datagrid("load");
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
};