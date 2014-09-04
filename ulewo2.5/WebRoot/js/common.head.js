var curIndex;
var msgFlag = 0;
$(function(){
	$("#searchInput").bind("keydown",function(event){
		var ev = document.all ? window.event : event;  
	    if(ev.keyCode==13) {// 如（ev.ctrlKey && ev.keyCode==13）为ctrl+Center 触发  
	    	search();
	    }  
	});
	$("a.op_like").live("click",function(){
		if(global.userId==""){
			goto_login();
			alert("请先登录");
			return;
		}
		likeArticle($(this));
	});
});

// 登录跳转
function goto_login() {
	var redirectUrl = window.location.href;
	if (redirectUrl.indexOf("redirectUrl") == -1) {
		document.location.href =global.realPath+"/login?redirectUrl=" + redirectUrl;
	} else {
		document.location.href = window.location.href;
	}
}

function goto_register() {
	document.location.href = global.realPath+"/register";
}

function logout() {
	var redirectUrl = global.realPath;
	var url =  global.realPath + "/user/logout";
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : {
			"time" : new Date()
		},
		url : url,// 请求的action路径
		success : function(data) {
			if (data.code == "200") {
				document.location.href = redirectUrl;
			}
		}
	});
}

/**刷新验证码**/
function refreshImage() {
	$("#codeImage").attr("src", global.realPath+"/common/image.jsp?rand =" + Math.random());
}


function createWoWo(){
	if (global.userId == "") {
		alert("请先登录");
		goto_login();
		return;
	}else{
		document.location.href=global.realPath+"/createWoWo";
	}
}

function search(keyword){
	if(keyword==null){
		keyword = $("#searchInput").val().trim();
	}
	if(keyword==""){
		alert("请输入关键字");
		return;
	}else{
		if(keyword==""){
			alert("请输入关键字");
			return;
		}else{
			if(global.type==""){
				global.type = "T";
			}
			keyword = encodeURI(encodeURI(keyword)); 
			
			document.location.href=global.realPath+"/search?type="+ global.type+"&q="+keyword;
		}
	}
}
function likeArticle(obj,opId,type){
	if(obj.attr("disable")=="true"){
		return
	}
	obj.attr("disable","true");
	var opid = obj.attr("opid"),type = obj.attr("type");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data:{
			opid:opid,
			type:type
		},
		url : global.realPath + "/like.action",// 请求的action路径
		success : function(data) {
			obj.attr("disable","false");
			if(data.result=="200"){
				if(obj.find("span").length>0){
					var count = parseInt(obj.find("span").eq(0).text());
					obj.find("span").eq(0).text(count+1)
				}else{
					alert("谢谢你的赞。");
				}
			}else{
				alert(data.msg);
			}
		}
	});
}