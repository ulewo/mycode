var article = {};
$(function(){
	article.loadData();
});
//初始化数据
article.loadData = function(){
	$('#dataGrid').datagrid({   
    	url:'http://localhost:8888/ulewo/admin/loadArticle.action',
    	method:'GET',
    	pagination:true, 
    	rownumbers:true,
    	pageSize:20,
    	loadMsg:'请稍等,正在加载...', 
    	toolbar:"#searchForm",
	    columns:[[ 
	    	{field:'id',checkbox:true},  
	        {field:'title',title:'标题',width:400},   
	        {field:'authorName',title:'作者',width:100},  
	        {field:'readNumber',title:'阅读',width:100},
	        {field:'reNumber',title:'回复',width:100}, 
	        {field:'postTime',title:'发帖时间',width:100}
	    ]],
	    onClickRow:function(rowIndex, rowData){
			window.open('../group/'+rowData.gid+'/topic/'+rowData.id);
	    }
	});
};


