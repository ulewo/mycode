var retalk = {};
$(function(){
	retalk.loadData();
});
//初始化数据
retalk.loadData = function(){
	$('#dataGrid').datagrid({   
    	url:global.realPath+'/admin/loadReTalk.action',
    	method:'POST',
    	pagination:true, 
    	rownumbers:true,
    	pageSize:20,
    	loadMsg:'请稍等,正在加载...', 
    	toolbar:"#searchForm",
	    columns:[[ 
	    	{field:'id',checkbox:true},  
	        {field:'content',title:'内容',width:400},   
	        {field:'userName',title:'作者',width:100},
	        {field:'createTime',title:'发帖时间',width:100}
	    ]]
	});
};


retalk.deleteReTalk = function(){
	var checkedRows = $("#dataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		$.messager.alert('提示','请选择要删除的记录!','info');
		return;
	};
	$.messager.confirm("确认","你确定要删除这"+checkedRows.length+"条数据", function (r){  
        if (r) {
        	var keyStr = [];
        	$.each(checkedRows, function(index, item){
        		keyStr.push(item.id);
        	});               
        	var data={
        	    "keyStr":keyStr.join(",")
        	};
        	$.ajax({
				async : true,
				cache : false,
				type : 'POST',
				dataType : "json",
				data : data,
				url : global.realPath+"/admin/deleteReTalk.action",// 请求的action路径
				success : function(data) {
					if(data.result=='success'){
	        			 $.messager.alert('提示','删除成功!','info');
						 $('#dataGrid').datagrid('reload');
	        		 }else{
	        			 $.messager.alert('提示','删除失败,请联系管理员!','error');
	        		 }
				}
			});
        }  
    });  
};