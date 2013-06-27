$(function(){
	$("#searchBtn").bind("click",search);
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	search();
	    }  
	};
});
function search(){
	var keyword = $("#searchInput_search").val().trim();
	if(keyword==""){
		alert("请输入关键字");
		return;
	}else{
		keyword = encodeURI(encodeURI(keyword)); 
		document.location.href=global.realPath+"/search?type="+searchType+"&q="+keyword;
	}
}
