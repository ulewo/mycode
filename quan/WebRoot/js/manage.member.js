$(function(){
	$("#checkAll").click(function(){
		if($(this).attr("checked")){
			$(".checkId").attr("checked",true);
		}else{
			$(".checkId").attr("checked",false);
		}
	})
	
});

//设为管理员
function set2Admin(opType){
	var members = $("input[name='member']:checked");
	var admins = $("input[name='admin']:checked");
	if(members.length==0){
		alert("请选择要设为管理员的成员");
		return;
	}
	if(admins.length>0){
		alert("只能选择普通成员");
		return;
	}
	$("#memberForm").attr("action","set2Admin.jspx");
	$("#memberForm").submit();
}

//取消管理员
function cancelAdmin(){
	var members = $("input[name='member']:checked");
	var admins = $("input[name='admin']:checked");
	if(admins.length==0){
		alert("请选择要取消的管理员");
		return;
	}
	if(members.length>0){
		alert("只能选择管理成员");
		return;
	}
	$("#adminForm").submit();
}

function deleteMember(){
	var members = $("input[name='member']:checked");
	var admins = $("input[name='admin']:checked");
	if(members.length==0){
		alert("请选择要删除的普通成员");
		return;
	}
	if(admins.length>0){
		alert("管理员不能直接删除");
		return;
	}
	$("#memberForm").attr("action","deleteMember.jspx");
	$("#memberForm").submit();
}

//接受成员
function acceptMember(obj){
	$(obj.parentElement.parentElement).children().eq(0).children().eq(1).children().eq(0).attr("checked","checked");
	$("#memberForm").attr("action","acceptMember.jspx");
	$("#memberForm").submit();
}

//接受成员
function refuseMember(obj){
	$(obj.parentElement.parentElement).children().eq(0).children().eq(1).children().eq(0).attr("checked","checked");
	$("#memberForm").attr("action","refuseMember.jspx");
	$("#memberForm").submit();
}
//批量接受
function acceptAll(){
	var members = $("input[name='ids']:checked");
	if(members.length==0){
		alert("请选择要接受的成员");
		return;
	}
	$("#memberForm").attr("action","acceptMember.jspx");
	$("#memberForm").submit();
}

//批量拒绝
function refuseAll(){
	var members = $("input[name='ids']:checked");
	if(members.length==0){
		alert("请选择要拒绝的成员");
		return;
	}
	$("#memberForm").attr("action","refuseMember.jspx");
	$("#memberForm").submit();
}

