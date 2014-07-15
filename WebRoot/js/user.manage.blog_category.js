$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
});

blogCategory.onClickRow = function(index){
	$('#datagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
};

blogCategory.save = function(){
	blogCategory.endEdit();	
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
		url : "../saveCategory.action",// 请求的action路径
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

blogCategory.endEdit = function(){
	var allRows = $('#datagrid').datagrid('getRows');
	for(var i=0,length=allRows.length;i<length;i++){
		var curRow = allRows[i];
		var ed = $('#datagrid').datagrid('getEditor', {index:i,field:'allowPost'});
		$('#datagrid').datagrid('endEdit', i);
	}
};


blogCategory.newRow = function(){
	 var length = $('#datagrid').datagrid('getRows').length;
	 $('#datagrid').datagrid('appendRow',{topicCount:0,blogCount:0});
	 $('#datagrid').datagrid('beginEdit', length);
};

blogCategory.deleteRow = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 ulewo_common.alert("请选择要删除的选项",1);
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	$.each(checkRows,function(index,item){
	    		$('#datagrid').datagrid('endEdit',index);
	    		if(item.blogCount>0){
	    			 ulewo_common.alert("该分类下有文章，不能删除",1);
	    			 return false;
	    		}
	    		var rowIndex = $('#datagrid').datagrid('getRowIndex',item);
	    		$('#datagrid').datagrid('deleteRow',rowIndex);
	    	});
	    	//blogCategory.save();
	    }
	 });
};
