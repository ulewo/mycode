var adminBlogComment = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid({
		onDblClickRow: function(rowIndex,rowData){
			adminBlogComment.loadComment(rowData);
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
});
	//搜索
adminBlogComment.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  fromObjStr = decodeURIComponent(fromObjStr,true);
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="blogs";
	  $( "#datagrid").datagrid( 'load' );
}
//加载评论
adminBlogComment.loadComment = function(data){
	 var params = {};
	 params.blogId = data.blogId;
	 $( "#datagrid_comment").datagrid( 'options' ).queryParams= params;
	 $( "#datagrid_comment").datagrid( 'options' ).url="blogComments";
	 $( "#datagrid_comment").datagrid( 'load' );
};
//格式化评论内容
adminBlogComment.formatContent = function(val,row){
	return "<div style='word-wrap:break-word;word-break: normal;'>"+val+"</div>"
}

//删除
adminBlogComment.deleteComment = function(){
	var rows = $("#datagrid_comment").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var blogId;
		    	var key = [];
		    	$.each(rows,function(index,item){
		    		key.push(item.id);
		    		if(index==0){
		    			blogId  = item.blogId;
		    		}
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"blogId":blogId
		    		},
		    		url : "deleteBlogComments",// 请求的action路径
		    		success : function(data) {
		    			if(data.result=="200"){
		    				ulewo_common.tipsInfo("删除成功",1);
		    				$("#datagrid_comment").datagrid("load");
		    			}else{
		    				ulewo_common.alert(data.msg,2);
		    			}
		    		}
		    	});
		    }
	 });
};