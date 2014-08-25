$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
});

topicCategoryManage.onClickRow = function(index){
	$('#datagrid').datagrid('selectRow', index).datagrid('beginEdit', index);
	 var ed = $('#datagrid').datagrid('getEditor', {index:index,field:'allowPost'});
	 var allowPost = ed.oldHtml;
	 var checkBox = $(ed.target);
	 if(allowPost=="Y"){
		 checkBox.attr("checked",true);
	 }else{
		 checkBox.attr("checked",false);
	 }
};

topicCategoryManage.save = function(){
	topicCategoryManage.endEdit();	
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
			"gid":topicCategoryManage.gid,
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

topicCategoryManage.endEdit = function(){
	var allRows = $('#datagrid').datagrid('getRows');
	for(var i=0,length=allRows.length;i<length;i++){
		var curRow = allRows[i];
		var ed = $('#datagrid').datagrid('getEditor', {index:i,field:'allowPost'});
		$('#datagrid').datagrid('endEdit', i);
	}
};


topicCategoryManage.newRow = function(){
	 var length = $('#datagrid').datagrid('getRows').length;
	 if(length>=8){
		 ulewo_common.alert("最多只能添加8个分类",1);
		 return;
	 }
	 $('#datagrid').datagrid('appendRow',{topicCount:0,allowPost:'Y'});
	 $('#datagrid').datagrid('beginEdit', length);
};

topicCategoryManage.deleteRow = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 $.messager.alert("提示","请选择要删除的选项","warning");
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	$.each(checkRows,function(index,item){
	    		$('#datagrid').datagrid('endEdit',index);
	    		var rowIndex = $('#datagrid').datagrid('getRowIndex',item);
	    		$('#datagrid').datagrid('deleteRow',rowIndex);
	    	});
	    	//topicCategoryManage.save();
	    }
	 });
};
