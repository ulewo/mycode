var ulewo_common ={};
ulewo_common.str2Obj = function(str){    
    str = str.replace(/&/g,"','");    
    str = str.replace(/=/g,"':'");    
    str = "({'"+str +"'})";    
    obj = eval(str);     
    return obj;    
};

ulewo_common.getCurDate = function(){
	var now = new Date(); 
	var SY = now.getFullYear(); 
	var SM = now.getMonth(); 
	var SD = now.getDate(); 
	return SY+"-"+(SM+1)+"-"+SD;
};

ulewo_common.isStartLessThanEndDate = function(startDate,endDate){   
    if(startDate.length>0&&endDate.length>0){   
        var arrStartDate = startDate.split("-");   
        var arrEndDate = endDate.split("-");   
        var allStartDate = new Date(arrStartDate[0],arrStartDate[1],arrStartDate[2]);   
        var allEndDate = new Date(arrEndDate[0],arrEndDate[1],arrEndDate[2]);   
        if(allStartDate.getTime()>allEndDate.getTime()){   
             return false;   
        }   
     }   
     return true;   
};

ulewo_common.clearForm = function(id){
	$("#"+id).form("clear");
};
ulewo_common.closeWindow = function(id){
	$("#"+id).window("close");
};
ulewo_common.loading = function(type,msg){
	if(msg==null||msg==""){
		msg = "请稍后,正在拼命为你加载......";
	}
	var  body_width =  document.body.clientWidth;
	var  body_height= document.body.clientHeight;
	//展示loading
	if(type=="show"){
		var myload = $("<div id='myload' style='border:2px solid #95B8E7;display:inline-block;padding:10px 8px;;position:absolute;z-index:999999999;top:0px;left:0px;background:#ffffff;font-size:12px'>"+
				"<div style='float:left;'><img src='"+global.realPath+"/images/load.gif'></div>"+
				"<div style='float:left;display:inline-block;margin-top:2px;margin-left:5px;'>"+msg+"</div>"+
		 "</div>").appendTo($("body"));
		 var myloadwidth = myload.width();
		 var myloadheight = myload.height();
		 myload.css({"left":(body_width-myloadwidth)/2,"top":(body_height-myloadheight)/2});
		 $("<div id='remote_load' style='position:fixed;width:100%;height:"+body_height+"px;z-index:99999999;top:0px;left:0px;background-color: #ccc;opacity: 0.3;filter: alpha(opacity = 30);'></div>").appendTo($("body"));
	}else{
		$("#myload").remove();
		$("#remote_load").remove();
	}
};
//两个数相乘
ulewo_common.accMul = function(arg1,arg2)  
{  
    var m=0,s1=arg1.toString(),s2=arg2.toString();  
    try{m+=s1.split(".")[1].length}catch(e){}  
    try{m+=s2.split(".")[1].length}catch(e){}  
    return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);  
} 

ulewo_common.tipsInfo = function(msg,type){
	setTimeout(function(){
		var tips;
		if(type==null){
			type=0;
		}
		var style;
		if(type==0){
			style = "tips";
		}else if(type==1){
			style = "tips2";
		}
		if($("#tips")[0]==null){
			tips = $("<div class='"+style+"' id='tips'></div>").appendTo($("body"));
			$("<div class='tipscon'><span>"+msg+"</span><img src='"+global.realPath+"/images/good.png' width='15'></div>").appendTo(tips);
			var width = parseInt($(document.body).outerWidth(true));
			var tipswidth = tips.width();
			tips.css({"left":(width-tipswidth)/2});
		}else{
			tips = $("#tips");
			tips.show();
			tips.css({"top":"400px"});
		}
		tips.animate({top:150},1000).fadeOut(1000);
	},1);
}

ulewo_common.alert = function(msg,type){
    //info-0,warning-1,error-2,question-3 ,success-4 
     var typeStr="info";
     if(type==1){
         typeStr='warning';
     }else if(type==2){
         typeStr='error';
     }else if(type==3){
         typeStr='question';
     }else if(type==4){
         typeStr='success';
     }else{
         typeStr='info';
     }
     $.messager.alert('提示框',msg,typeStr); 
  }

ulewo_common.setPostion = function(){
	var url = window.location.href;
	var index_ = url.lastIndexOf("#");
	if(index_!=-1){
		var reId = url.substring(url.lastIndexOf("#")+1);
		var top = $("a[name='re"+reId+"']").offset().top-50;
		window.scrollTo(0,top);
	}
}

ulewo_common.convertArray= function(o) { 
	var v = {};
	for ( var i in o) {
		if (typeof (v[o[i].name]) == 'undefined')
			v[o[i].name] = o[i].value;
		else
			v[o[i].name] += "," + o[i].value;
	}
	return eval("(" +JSON.stringify(v)+ ")");
}