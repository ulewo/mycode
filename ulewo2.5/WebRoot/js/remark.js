function reMarkP(userId,usericon,name,time,sourceFrom){
	this.mark = $("<div class='mark_con'></div>");
	$("<div class='mark_icon'><a href='"+global.realPath+"/user/"+userId+"' target='_blank'><img src='"+ global.realPath + "/upload/"
			+usericon+"' width='60'/></a></div>").appendTo(this.mark);
	$("<div class='mark_time'><a href='"+global.realPath+"/user/"+userId+"' target='_blank'>"+name+"</a></div>").appendTo(this.mark);
	$("<div class='mark_time'>"+time+"</div>").appendTo(this.mark);
	if(sourceFrom=="A"){
		$("<div class='mark_from'>Android</div>").appendTo(this.mark);
	}
}

function loadMySin(year){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadMySignIn.action?year="+year,// 请求的action路径
		success : function(data) {
			$("#remarkist").empty();
			$("#loading").hide();
			if(data.result=="200"){
				//加载年
				var selectDiv = $("<div class='selectDiv'></div>").appendTo("#remarkist");
				var selectCon  = $("<div class='selectCon'></div>").appendTo(selectDiv);
				var select = $("<select></select>").appendTo(selectCon).change(function(){
					loadMySin($(this).val());
				});
				$("<option value='2013'>2013</option>").appendTo(select);
				var maxYear = parseInt(data.maxYear);
				for(var i=2014;i<=maxYear;i++){
					if(i==data.year){
						$("<option value="+i+" selected='selected'>"+i+"</option>").appendTo(select);
					}else{
						$("<option value="+i+">"+i+"</option>").appendTo(select);
					}
				}
				
				$("<div class='noSignin'>未签到</div>").appendTo(selectDiv);
				$("<div class='Signin'>已签到</div>").appendTo(selectDiv);
				$("<div style='clear:left'></div>").appendTo(selectDiv);
				var months = data.list;
				for(var i=0,length=months.length;i<length;i++){
					var divCon = $("<div class='calendarCon'></div>").appendTo("#remarkist");
					var table = $("<table cellpadding='1' cellspacing='1'></table>").appendTo("#remarkist").appendTo(divCon);
					$("<tr><td colspan='8' class='month'>"+(i+1)+"月</td></tr>").appendTo(table);
					$("<tr><td>星期日</td><td>星期一</td><td>星期二</td><td>星期三</td><td>星期四</td><td>星期五</td><td>星期六</td></tr>").appendTo(table);
					var monthData = months[i];
					var fristDay = monthData.fristDay;
					var monthDays = monthData.monthDays;
					var dayInfos  = monthData.dayInfos;
					var tr = $("<tr></tr>").appendTo(table);
					for(var j=1;j<fristDay;j++){
						$("<td>&nbsp;</td>").appendTo(tr);
					}
					for(var n=0;n<monthDays;n++){
						var td = "";
						if(dayInfos[n].signinType){
							td = $("<td class='tdsignin'>"+dayInfos[n].day+"</td>").appendTo(tr);
						}else{
							if(dayInfos[n].beforeNowDate){
								td = $("<td class='tdnosignin'>"+dayInfos[n].day+"</td>").appendTo(tr);
							}else{
								td = $("<td class='afterday'>"+dayInfos[n].day+"</td>").appendTo(tr);
							}
						}
					   if(dayInfos[n].curDay){
						   td.addClass("curDay");
					   }
					   
						if((fristDay+n)%7==0){
								tr=$("<tr></tr>").appendTo(table);
						}
					}
				}
				$("<div style='clear:left'></div>").appendTo("#remarkist")
			}else{
				alert(data.msg);
			}
		}
	});
}

function loadAllSignIn(){
	$.ajax({
		async : true,
		cache : false,
		type : 'GET',
		dataType : "json",
		url : global.realPath+"/loadAllSignIn.action",// 请求的action路径
		success : function(data) {
			$("#loading").hide();
			if(data.result=="200"){
				var size = data.data.list.length;
				if(size>0){
					for(var i=0;i<size;i++){
						new reMarkP(data.data.list[i].userId,data.data.list[i].userIcon,data.data.list[i].userName,data.data.list[i].showSignTime,data.data.list[i].sourceFrom).mark.appendTo($("#remarkist"));
					}
					$("<div class='clear'></div>").appendTo($("#remarkist"));
				}else{
					$("<div class='noinfo'>今天没有签到记录，速度抢沙发</div>").appendTo($("#remarkist"));
				}
			}else{
				$("<div class='noinfo'>"+data.msg+"</div>").appendTo($("#remarkist"));
			}
		}
	});
}