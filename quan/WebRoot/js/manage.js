$(function(){
	$(".bigTitle").mouseover(function(){
		$(this).css({background:"#FEFFEF"});
	});
	
	$(".bigTitle").mouseout(function(){
		$(this).css({background:"#FFFFFF"});
	});
	$("#addGroup").click(function(){
		document.location.href="addGroup.jspx?groupNumber="+$(this).attr("name");
	});
});