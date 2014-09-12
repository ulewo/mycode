var adminSpider = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50,100]
	});
	adminSpider.loadGroup();
});
adminSpider.typeFormate = function(value,rowData,index){
	if(value=="osc"){
		return "开源中国资讯";
	}else if(value=="xwg"){
		return "新闻哥";
	}else if(value=="cnblog"){
		return "博客园资讯";
	}else if(value=="qilu_new"){
		return "齐鲁资-讯快报";
	}else if(value=="qilu_life"){
		return "齐鲁网-生活百态";
	}else if(value=="qilu_star"){
		return "齐鲁网-八卦明星";
	}else if(value=="gb_pic"){
		return "逛吧-图片";
	}else if(value=="gb_game"){
		return "逛吧-游戏";
	}else if(value=="gb_movie"){
		return "逛吧-视频";
	}else if(value=="gb_topic"){
		return "逛吧-文章";
	}else if(value=="gb_joke"){
		return "逛吧-搞笑";
	}else if(value=="gb_talk"){
		return "逛吧-杂谈";
	}
};

adminSpider.statusFormate = function(value,rowData,index){
	if(value=="0"){
		return "未发布";
	}else if(value=="1"){
		return "已发布";
	}
};

//搜索
adminSpider.searchSpider = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="getSpiderList.action";
	  $( "#datagrid").datagrid( 'load' );
};
//删除成员
adminSpider.deleteMember = function(){
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
		    			"key":key.join(",")
		    		},
		    		url : "deleteUsers.action",// 请求的action路径
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


adminSpider.spider = function(){
	ulewo_common.loading("show","正在抓取文章......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:$("#spiderform").serialize(),
		url : "spider.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				ulewo_common.tipsInfo("爬取成功",1);
				$("#datagrid").datagrid("load");
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
};

adminSpider.selectGroup = function(){
	var rows = $("#datagrid").datagrid("getChecked");
	if(rows.length==0){
		ulewo_common.alert("请选择需要发布的文章",1);
		return;
	}
	$("#groupDialog").window('open'); 
}

adminSpider.loadGroup = function(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url:"allGroups.action",
		success : function(data) {
			$('#group').combobox({
			     valueField:"gid",
			     textField:"groupName",
			     data:data.list,
			     panelHeight:150,
			     onSelect:function(record){
			    	 loadCategory(record.gid);
			     }
			  });
		}
	});
}

function loadCategory(gid){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : "../groupmanage/category?gid="+gid,// 请求的action路径
		success : function(data) {
			$('#category').combobox({
			     valueField:"categoryId",
			     textField:"name",
			     data:data.rows,
			     panelHeight:"auto"
			  });
		}
	});	
}

adminSpider.sendTopic = function(){
	var fromObj=$("#categoryForm");
	var validateForm= fromObj.form('validate');
    if(validateForm==false){
        return ;
    }
    var rows = $("#datagrid").datagrid("getChecked");
	var key = [];
	$.each(rows,function(inex,item){
		key.push(item.id);
	});
	var data =ulewo_common.convertArray($('#categoryForm').serializeArray());
	data.key = key.join(",");
	ulewo_common.loading("show","正在发布文章......");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:data,
		url : "sendTopic.action",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				$("#datagrid").datagrid("load");
				$("#groupDialog").window('close'); 
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}
