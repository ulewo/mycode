var groupCategory={};

groupCategory.onClickRow = function(index){
	$('#datagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
};
groupCategory.onClickSubRow = function(index){
	$('#subdatagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
};
groupCategory.save = function(){
	groupCategory.endEdit();	
	var inserted = $('#datagrid').datagrid('getChanges',"inserted");
	var updated = $('#datagrid').datagrid('getChanges',"updated");
	var deleted = $('#datagrid').datagrid('getChanges',"deleted");
	var allRows = $("#datagrid").datagrid('getRows');
	var allOk = true;
	$.each(allRows,function(index,item){
		if(!$('#datagrid').datagrid('validateRow',index)){
			ulewo_common.alert("第"+(index+1)+"数据不符合规范");
			allOk = false;
			return false;
		}
	});
	if(!allOk){
		return;
	}
	ulewo_common.loading("show","正在保存......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			"inserted":encodeURIComponent(JSON.stringify(inserted)),
			"updated":encodeURIComponent(JSON.stringify(updated)),
			"deleted":encodeURIComponent(JSON.stringify(deleted))
		},
		url : "saveCategory.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				$('#datagrid').datagrid("load");
				$('#datagrid').datagrid("acceptChanges");
				ulewo_common.tipsInfo("保存成功",1);
			}else{
				ulewo_common.alert(data.msg,2);
			}
			
		}
	});
}
groupCategory.formatOp = function(val,row){
	return "<div style='word-wrap:break-word;word-break: normal;'><a href='javascript:groupCategory.loadSub("+row.groupCategoryId+")'>查看子模块</a></div>"
}
groupCategory.loadSub = function(groupCategoryId){
	var params = {};
	params.pid = groupCategoryId;
	groupCategory.pid = groupCategoryId;
	$( "#subdatagrid").datagrid( 'options' ).queryParams= params;
	$( "#subdatagrid").datagrid( 'options' ).url="loadGroupCategory.action";
	$( "#subdatagrid").datagrid( 'load' );
}


groupCategory.endEdit = function(){
	var allRows = $('#datagrid').datagrid('getRows');
	for(var i=0,length=allRows.length;i<length;i++){
		var ed = $('#datagrid').datagrid('getEditor',{index:i,field:'name'});
		$('#datagrid').datagrid('endEdit', i);
	}
};


groupCategory.newRow = function(){
	 var length = $('#datagrid').datagrid('getRows').length;
	 $('#datagrid').datagrid('appendRow',{rang:(length+1)});
	 $('#datagrid').datagrid('beginEdit', length);
};

groupCategory.deleteRow = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 ulewo_common.alert("请选择要删除的选项",1);
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	$.each(checkRows,function(index,item){
	    		$('#datagrid').datagrid('endEdit',index);
	    		var rowIndex = $('#datagrid').datagrid('getRowIndex',item);
	    		$('#datagrid').datagrid('deleteRow',rowIndex);
	    	});
	    	//groupCategory.save();
	    }
	 });
};

groupCategory.saveSub = function(){
	groupCategory.endSubEdit();	
	var inserted = $('#subdatagrid').datagrid('getChanges',"inserted");
	var updated = $('#subdatagrid').datagrid('getChanges',"updated");
	var deleted = $('#subdatagrid').datagrid('getChanges',"deleted");
	var allRows = $("#subdatagrid").datagrid('getRows');
	var allOk = true;
	$.each(allRows,function(index,item){
		if(!$('#subdatagrid').datagrid('validateRow',index)){
			ulewo_common.alert("第"+(index+1)+"数据不符合规范");
			allOk = false;
			return false;
		}
	});
	if(!allOk){
		return;
	}
	ulewo_common.loading("show","正在保存......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			"inserted":encodeURIComponent(JSON.stringify(inserted)),
			"updated":encodeURIComponent(JSON.stringify(updated)),
			"deleted":encodeURIComponent(JSON.stringify(deleted))
		},
		url : "saveCategory.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				$('#subdatagrid').datagrid("load");
				$('#subdatagrid').datagrid("acceptChanges");
				ulewo_common.tipsInfo("保存成功",1);
			}else{
				ulewo_common.alert(data.msg,2);
			}
			
		}
	});
}

groupCategory.endSubEdit = function(){
	var allRows = $('#subdatagrid').datagrid('getRows');
	for(var i=0,length=allRows.length;i<length;i++){
		var ed = $('#subdatagrid').datagrid('getEditor',{index:i,field:'name'});
		$('#subdatagrid').datagrid('endEdit', i);
	}
};


groupCategory.newSubRow = function(){
	 var length = $('#subdatagrid').datagrid('getRows').length;
	 $('#subdatagrid').datagrid('appendRow',{pid:groupCategory.pid,rang:length+1});
	 $('#subdatagrid').datagrid('beginEdit', length);
};

groupCategory.deleteSubRow = function(){
	 var checkRows = $('#subdatagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 ulewo_common.alert("请选择要删除的选项",1);
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	$.each(checkRows,function(index,item){
	    		$('#subdatagrid').datagrid('endEdit',index);
	    		var rowIndex = $('#subdatagrid').datagrid('getRowIndex',item);
	    		$('#subdatagrid').datagrid('deleteRow',rowIndex);
	    	});
	    	//groupCategory.save();
	    }
	 });
};