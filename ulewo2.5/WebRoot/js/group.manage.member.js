$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
});
groupManageMember.userIconFormate = function(value,rowData,index){
	return "<div style='width:50px;height:50px;text-align:center'><img src=../../upload/"+rowData.userIcon+" width='50'></div>";
};

//搜索
groupManageMember.searchMember = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../member?gid="+groupManageMember.gid;
	  $( "#datagrid").datagrid( 'load' );
};
//删除成员
groupManageMember.deleteMember = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.userId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":groupManageMember.gid
		    		},
		    		url : "../deleteMember.action",// 请求的action路径
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

groupManageMember.searchApplyMember = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../memberApply?gid="+groupManageMember.gid;
	  $( "#datagrid").datagrid( 'load' );
};

groupManageMember.applyMmeber = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认接受这几位成员吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.userId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"gid":groupManageMember.gid
		    		},
		    		url : "../applyMmeber.action",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("操作成功!",1);
		    				$("#datagrid").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};

