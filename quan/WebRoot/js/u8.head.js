var _add = art.dialog.defaults;
_add["drag"]=true;
_add['lock'] = true;
_add['fixed'] = true;
_add['yesText'] = 'yes';
_add['noText'] = 'no';

function showLoginDilog(url){
	art.dialog.open(url,
		    {title: '用户登录', width: 500, height: 300});
}

$(function(){
	$("#searchBtn").bind('click', search);
	showMenue();
 })

function showMenue(){
	$("#userName").children("li:has(ul)").hover(
		function(){
			var ulnode = $(this);
			ulnode.css("background","#2292C3");
			ulnode.children("ul").css("display","inline-block");
		},
		function(){
			$(this).children("ul").hide();
			$(this).css("background","#64C5E8");
		}
	);
}
//搜索
function search(type){
	var keyWord = $("#searchInput").val();
	keyWord = encodeURI(encodeURI(keyWord)); 
	if(type=="index"){
		document.location.href="search.jspx?keyWord="+keyWord;
	}else if(type=="common"){
		document.location.href="../search.jspx?keyWord="+keyWord;
	}
	
}

function logout(type){
	var url  = "user/logout.jspx";
	if(type==2){
		url = "../user/logout.jspx";
	}
	$.ajax({
		async:true,
		cache:false,
		type:'POST',
		dataType : "json",
		data : {
			"time":new Date()
		},
		url: url,//请求的action路径
		success:function(data){
			if(data.msg=="ok"){//登录成功
				art.dialog.tips("成功退出。");
				parent.location.reload();
			}else{
				art.dialog.tips("出现错误，稍后重试！");
			}
		}
	});
}
