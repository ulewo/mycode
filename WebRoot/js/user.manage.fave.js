var userFave = {};
userFave.formatTopicTitle = function(val,row){
	return "<a href='../../group/"+row.gid+"/topic/"+row.articleId+"' target='_blank'>"+row.title+"</a>";
};

userFave.formatBlogTitle = function(val,row){
	return "<a href='../../user/"+row.userId+"/blog/"+row.articleId+"' target='_blank'>"+row.title+"</a>";
};

userFave.search = function(type){
	var fromObjStr=$('#searchform').serialize();
	var params = ulewo_common.str2Obj(fromObjStr);
	params.type = type;
	$( "#datagrid").datagrid( 'options' ).queryParams= params;
	$( "#datagrid").datagrid( 'options' ).url="../../manage/queryFavoriteArticle.action";
	$( "#datagrid").datagrid( 'load' );
};

userFave.deleteFave = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var key = [];
		    	$.each(rows,function(inex,item){
		    		key.push(item.articleId);
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(",")
		    		},
		    		url : "../deleteBlog.action",// 请求的action路径
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
	
}
