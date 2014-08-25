var blogComment = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid({
		onDblClickRow: function(rowIndex,rowData){
			blogComment.curBlogId = rowData.blogId;
			blogComment.loadComment(rowData);
		}
	});
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	var dgcoment = $('#datagrid_comment');
	dgcoment.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	blogComment.initCategory();
});
//初始化板块下拉框
blogComment.initCategory = function(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "../loadCategory.action",// 请求的action路径
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
blogComment.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../loadBlog.action";
	  $( "#datagrid").datagrid( 'load' );
}

//加载评论
blogComment.loadComment = function(data){
	 var params = {};
	 params.blogId = data.blogId;
	 $( "#datagrid_comment").datagrid( 'options' ).queryParams= params;
	 $( "#datagrid_comment").datagrid( 'options' ).url="../loadComment.action";
	 $( "#datagrid_comment").datagrid( 'load' );
};
//格式化评论内容
blogComment.formatContent = function(val,row){
	return "<div style='word-wrap:break-word;word-break: normal;'>"+val+"</div>"
}

//批量删除评论
blogComment.deleteCommentBatch = function(){
	var checkRows = $("#datagrid_comment").datagrid("getChecked");
	if(checkRows.length==0){
		$.messager.alert('提示','请选择需要删除的评论',"warning");    
		return;
	}
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	        var keys = [];
	        for(var i=0,length=checkRows.length;i<length;i++){
	        	keys.push(checkRows[i].id);
	        }
	        $.ajax({
	    		async : true,
	    		cache : false,
	    		type : 'POST',
	    		dataType : "json",
	    		data:{
	    			"blogId":blogComment.curBlogId,
	    			"kyes":keys.join(",")
	    		},
	    		url : "../deleteComent.action",// 请求的action路径
	    		success : function(data) {
	    			if(data.result=="200"){
	    				$.messager.alert('提示','删除成功',"info");
	    				$("#datagrid_comment").datagrid("load");
	    			}else{
	    				$.messager.alert('提示','请选择需要删除的评论',"error");    
	    			}
	    		}
	    	});
	    }    
	}); 
}