$(function(){
	$("#searchBtn").bind("click",search);
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
