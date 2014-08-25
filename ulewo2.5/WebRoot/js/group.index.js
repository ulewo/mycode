$(function(){
	lazyLoadImage("article_attachedimg");
	loadMember();
});


function loadMember(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/group/"+global.gid+"/loadMembers",// 请求的action路径
		success : function(data) {
			var memberlist = data.memberList;
			var activerList = data.activerList;
			for(var i=0,length=activerList.length;i<length;i++){
				$("<div class='group_member_icon'><a href='"+global.realPath+"/user/"+activerList[i].userId+"' title='"+activerList[i].userName+"'><img src='"+global.realPath+"/upload/"+activerList[i].userIcon+"'/></a></div>").appendTo($("#activer_list"));
			}
			$("<div class='clear'></div>").appendTo($("#activer_list"));
			for(var i=0,length=memberlist.length;i<length;i++){
				$("<div class='group_member_icon'><a href='"+global.realPath+"/user/"+memberlist[i].userId+"' title='"+memberlist[i].userName+"'><img src='"+global.realPath+"/upload/"+memberlist[i].userIcon+"'/></a></div>").appendTo($("#member_list"));
			}
			$("<div class='clear'></div>").appendTo($("#member_list"));
			if(memberlist.length==0){
				$("<div class='noinfo'>暂时没有成员</div>").appendTo($("#member_list"));
			}
			if(activerList.length==0){
				$("<div class='noinfo'>暂时没有成员</div>").appendTo($("#activer_list"));
			}
		}
	});
}
