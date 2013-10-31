var user = {};
$(function(){
	user.loadData();
});
//初始化数据
user.loadData = function(){
	$('#dataGrid').datagrid({   
    	url:global.realPath+'/admin/loadUser.action',
    	method:'POST',
    	pagination:true, 
    	rownumbers:true,
    	pageSize:20,
    	loadMsg:'请稍等,正在加载...', 
    	toolbar:"#searchForm",
	    columns:[[ 
	    	{field:' ',checkbox:true},
	    	{field:'userId',title:'用户ID'}, 
	    	{field:'userLittleIcon',title:'头像',formatter:function(value,row,index){
	    		return "<img width=20 src='"+global.realPath+"/upload/"+value+"'/>";
	    	}},
	        {field:'userName',title:'用户名',width:200},   
	        {field:'registerTime',title:'注册时间',width:100},  
	        {field:'previsitTime',title:'最后登录时间',width:100}
	       ]],
	    onClickRow:function(rowIndex, rowData){
			window.open('../user/'+rowData.userId);
	    }
	});
};

$(window).resize(function(){
	$('#dataGrid').datagrid('resize', {
		width:function(){return document.body.clientWidth;},
		height:function(){return document.body.clientHeight;},
	});
});

user.deleteUser = function(){
	var checkedRows = $("#dataGrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length < 1){
		$.messager.alert('提示','请选择要删除的记录!','info');
		return;
	};
	$.messager.confirm("确认","你确定要删除这"+checkedRows.length+"条数据", function (r){  
        if (r) {
        	var keyStr = [];
        	$.each(checkedRows, function(index, item){
        		keyStr.push(item.userId);
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
				url : global.realPath+"/admin/deleteUser.action",// 请求的action路径
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
