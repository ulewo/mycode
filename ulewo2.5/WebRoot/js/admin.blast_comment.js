var adminTtopicComment = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid({
		onDblClickRow: function(rowIndex,rowData){
			adminTtopicComment.loadComment(rowData);
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
adminTtopicComment.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  fromObjStr = decodeURIComponent(fromObjStr,true);
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../topics?gid="+adminTtopicComment.gid;
	  $( "#datagrid").datagrid( 'load' );
}
//加载评论
adminTtopicComment.loadComment = function(data){
	 var params = {};
	 params.blastId = data.blastId;
	 $( "#datagrid_comment").datagrid( 'options' ).queryParams= params;
	 $( "#datagrid_comment").datagrid( 'options' ).url="blastComments";
	 $( "#datagrid_comment").datagrid( 'load' );
};
//格式化评论内容
adminTtopicComment.formatContent = function(val,row){
	return "<div style='word-wrap:break-word;word-break: normal;'>"+val+"</div>"
}

//删除
adminTtopicComment.deleteComment = function(){
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
		    			topicId  = item.id;
		    		}
		    	});
		    	$.ajax({
		    		async : true,
		    		cache : false,
		    		type : 'POST',
		    		dataType : "json",
		    		data:{
		    			"key":key.join(",")
		    		},
		    		url : "deleteBlastComments",// 请求的action路径
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