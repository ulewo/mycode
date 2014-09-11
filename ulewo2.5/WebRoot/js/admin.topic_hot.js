var adminTopicHot = {};
$(function(){
	var dg = $('#datagrid');
	dg.datagrid({
		onCheck: function(rowIndex,rowData){
			adminTopicHot.collectionTitle(rowData,"check");
		},
		onUncheck: function(rowIndex,rowData){
			adminTopicHot.collectionTitle(rowData,"uncheck");
		}
	});
	dg.datagrid('getPager').pagination({
		layout:['list','sep','first','prev','sep','links','sep','next','last','sep','refresh'],
		pageList: [20,30,40,50,100,150,200,300]
	});
});
//搜索
adminTopicHot.search = function(){
	  var fromObjStr=$('#searchform').serialize();
	  var params = ulewo_common.str2Obj(fromObjStr);
	  $( "#datagrid").datagrid( 'options' ).queryParams= params;
	  $( "#datagrid").datagrid( 'options' ).url="topics";
	  $( "#datagrid").datagrid( 'load' );
};

adminTopicHot.formatTitle = function(val,row){
	var grade = row.grade;
	var essence = row.essence;
	var title = "";
	if(row.images!=null){
		title = title+"<img src='../images/img.gif'/>&nbsp;";
	}
	if(grade=="2"){
		title = title+"<img src='../images/zding.gif'/>&nbsp;";
	}
	if(essence=="Y"){
		title = title+"<img src='../images/jing.gif'/>&nbsp;";
	}
	title = title+row.title;
	return title;
};

adminTopicHot.collectionTitle = function(rowData,type){
	var hotList = $('#datagrid_hot').datagrid('getRows');
	if(hotList.length==0){
		$('#datagrid_hot').datagrid('appendRow',rowData);
	}
	var isInsert = true;
	var isDelete = false;
	var index  = 0;
	for(var i=0,length=hotList.length;i<length;i++){
		if(parseInt(hotList[i].topicId)==parseInt(rowData.topicId)&&type=="check"){
			isInsert = false;
		}
		if(parseInt(hotList[i].topicId)==parseInt(rowData.topicId)&&type=="uncheck"){
			isDelete = true;
			isInsert = false;
			index = i;
		}
	}
	if(isInsert){
		$('#datagrid_hot').datagrid('appendRow',rowData);
	}
	if(isDelete){
		$('#datagrid_hot').datagrid('deleteRow',index);
	}
};
adminTopicHot.createHot = function(){
	ulewo_common.loading("正在生成热点文章......","show");
	var hotList = $('#datagrid_hot').datagrid('getRows');
	if(hotList.length==0){
		ulewo_common.alert("先选择要生成的热点文章");
		return;
	}
	var key = [];
	$.each(hotList,function(inex,item){
		key.push(item.topicId);
	});
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			"key":key.join(",")
		},
		url : "createHot",// 请求的action路径
		success : function(data) {
			ulewo_common.loading();
			if(data.result=="200"){
				$("#hotDialog").window("open");
				var topics = data.list;
				$("#hotTopic").empty();
				for(var i=0,length=topics.length;i<length;i++){
					new TopicLine(topics[i]).appendTo("#hotTopic");
				}
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
};

function TopicLine(data){
	var item = $('<div style="margin-top:10px;border-bottom:1px dashed #B2B3B2;padding-bottom:10px;width:900px;"></div>');
	var titlecon = $("<div></div>").appendTo(item);
	var title =$("<div style='display:inline-block;font-size:14px;'></div>").appendTo(titlecon);
		$('<a style="text-decoration:none;float:left;display:inline-block;color:#3E62A6;" href="http://ulewo.com/group/'+data.gid+'/topic/'+data.topicId+'">'+data.title+'</a>').appendTo(title)	;
		$('<div style="clear:both"></div>').appendTo(title)	;	
	$('<div style="margin-top:10px;line-height:20px;color:#666666">'+data.summary+'</div>').appendTo(item);
	if(data.images!=null){
		var imageDiv = $('<div></div>').appendTo(item);
		var count = data.images.length;
		if(data.images.length>5){
			count = 5;
		}
		for(var i=0,length=count;i<length;i++){
			$('<div style="display:inline-block;border:1px solid #B2B3B2;float:left;padding:2px;margin-top:10px;max-height:100px;max-width:150px;margin-right:5px;"><a href="http://ulewo.com/group/'+data.gid+'/topic/'+data.topicId+'"><img src="'+data.images[i]+'" style="max-width:150px;max-height:100px;"></a></div>').appendTo(imageDiv);
		}
		$('<div style="clear:both"></div>').appendTo(imageDiv);
	}
	/*		
		<div style="margin-top:10px;line-height:20px;color:#666666">
			 一点单元测试的小知识。......
		</div>
		<div>
			
			<div style="clear:both"></div>
		</div>*/
		return item;
}

adminTopicHot.copy = function(){
	copy_code($("#hotTopic").html());
};

function copy_code(copyText) { 
	if (window.clipboardData) { 
		window.clipboardData.setData("Text", copyText) 
	}else { 
		var flashcopier = 'flashcopier'; 
		if(!document.getElementById(flashcopier)){ 
			var divholder = document.createElement('div'); 
			divholder.id = flashcopier; 
			document.body.appendChild(divholder); 
		} 
		document.getElementById(flashcopier).innerHTML = ''; 
		var divinfo = '<embed src="../images/_clipboard.swf" FlashVars="clipboard='+encodeURIComponent(copyText)+'" width="0" height="0" type="application/x-shockwave-flash"></embed>'; 
		document.getElementById(flashcopier).innerHTML = divinfo; 
	} 
	ulewo_common.alert('copy成功！'); 
} 