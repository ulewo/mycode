var notice = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	dg.datagrid({
		onDblClickRow: function(rowIndex,rowData){
			window.open("../readNotice.action?id="+rowData.id);
			if(rowData.status=="0"){
				dg.datagrid("load");
			}
		}
	});
});

notice.formatTitle = function(val,row){
	if(row.status=="0"){
		return "<span class='noread'>"+row.title+"</span>";
	}else if(row.status=="1"){
		return "<span class='read'>"+row.title+"</span>";
	}
};

notice.formatStatus = function(val,row){
	if(row.status=="0"){
		return "未读";
	}else if(row.status=="1"){
		return "已读";
	}
};

//搜索
notice.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../notice.action";
	  $( "#datagrid").datagrid( 'load' );
}

notice.signNoticeRead = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 $.messager.alert("提示","请选择要标记的选项?","warning");
		 return;
	 }
	 $.messager.confirm('确认','确定要标记这"'+checkRows.length+'"项为已读么?',function(r){    
	    if (r){  
	    	var key = [];
	    	$.each(checkRows,function(index,item){
	    		key.push(item.id);
	    	});
	    	$.ajax({
	    		async : true,
	    		cache : false,
	    		type : 'POST',
	    		dataType : "json",
	    		data:{
	    			"key":key.join(",")
	    		},
	    		url : "../SignNoticeRead.action",// 请求的action路径
	    		success : function(data) {
	    			if(data.result=="200"){
	    				ulewo_common.tipsInfo("标记成功",1);
	    				$("#datagrid").datagrid("load");
	    			}else{
	    				ulewo_common.alert(data.msg,2);
	    			}
	    		}
	    	});
	    }
	 });
};

notice.deleteNotice = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 $.messager.alert("提示","请选择要删除的选项","warning");
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	var key = [];
	    	$.each(checkRows,function(index,item){
	    		key.push(item.id);
	    	});
	    	$.ajax({
	    		async : true,
	    		cache : false,
	    		type : 'POST',
	    		dataType : "json",
	    		data:{
	    			"key":key.join(",")
	    		},
	    		url : "../deleteNotice.action",// 请求的action路径
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
