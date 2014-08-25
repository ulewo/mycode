var blog = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50]
	});
	blog.initCategory();
});
//初始化板块下拉框
blog.initCategory = function(){
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
blog.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="../loadBlog.action";
	  $( "#datagrid").datagrid( 'load' );
}
//修改
ulewo_common.edit = function(){
	var checkedRows = $("#datagrid").datagrid("getChecked");// 获取所有勾选checkbox的行
	if(checkedRows.length != 1){
		$.messager.alert("提示","请选择一条记录!","warning");
		return;
	}
	$("#editFrame").attr("src",'../edit_blog.action?blogId='+checkedRows[0].blogId);
	ulewo_common.loading("show","正在拼命的为你加载......");
	$("#editDialog").window('open'); 
};

function closeWindow(){
	$("#editDialog").window("close");
};

function reload(){
	$("#editDialog").window("close");
	ulewo_common.tipsInfo("保存成功",1);
	$( "#datagrid").datagrid( 'load' );
};
function onloadComplete(){
	ulewo_common.loading();
};

ulewo_common.deleteBlog = function(){
	 var checkRows = $('#datagrid').datagrid('getChecked');
	 if(checkRows.length==0){
		 $.messager.alert("提示","请选择要删除的选项","warning");
		 return;
	 }
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){  
	    	var key = [];
	    	$.each(checkRows,function(index,item){
	    		key.push(item.blogId);
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
};
