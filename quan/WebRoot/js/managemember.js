$(function(){
	$("#accept").click(function(){
		if($(".userIds:checked").length<1){
				alert("请选择要操作的选项");
				return;
			}
		$("#memberForm").attr("action","acceptMember.jspx");
		if(confirm( "你确定要接纳成员吗？")){
			$("#memberForm").submit();
		} 
		
	});
	
	$("#refuse").click(function(){
		if($(".userIds:checked").length<1){
				alert("请选择要操作的选项");
				return;
			}
		$("#memberForm").attr("action","refuseMember.jspx");
		if(confirm( "你确定要拒绝成员吗？")){
			$("#memberForm").submit();
		} 
		
	});
});