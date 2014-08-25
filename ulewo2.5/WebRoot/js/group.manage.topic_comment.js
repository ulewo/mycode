$(function(){
	var dg = $('#datagrid');
	dg.datagrid({
		onDblClickRow: function(rowIndex,rowData){
			topicCommentManage.loadComment(rowData);
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
	topicCommentManage.initCategory();
});

//初始化板块下拉框
topicCommentManage.initCategory = function(){
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : "../category?gid="+topicCommentManage.gid,// 请求的action路径
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
topicCommentManage.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  fromObjStr = decodeURIComponent(fromObjStr,true);
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../topics?gid="+topicCommentManage.gid;
	  $( "#datagrid").datagrid( 'load' );
}
//加载评论
topicCommentManage.loadComment = function(data){
	 var params = {};
	 params.topicId = data.topicId;
	 params.gid = topicCommentManage.gid;
	 $( "#datagrid_comment").datagrid( 'options' ).queryParams= params;
	 $( "#datagrid_comment").datagrid( 'options' ).url="../topicComment.action";
	 $( "#datagrid_comment").datagrid( 'load' );
};
//格式化评论内容
topicCommentManage.formatContent = function(val,row){
	return "<div style='word-wrap:break-word;word-break: normal;'>"+val+"</div>"
}

//删除
topicCommentManage.deleteComment = function(){
	var rows = $("#datagrid_comment").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要删除的数据",1);
		return;
	}
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		    if (r){  
		    	var topicId;
		    	var key = [];
		    	$.each(rows,function(index,item){
		    		key.push(item.id);
		    		if(index==0){
		    			topicId  = item.topicId;
		    		}
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(","),
		    			"topicId":topicId
		    		},
		    		url : "../deleteComment.action",// 请求的action路径
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