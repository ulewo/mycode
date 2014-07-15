var index = {};
var links = [
	[  //浪漫一生 1
	 {"title":"情侣写真","link":"####"},
	 {"title":" 微电影","link":"####"},
	 {"title":"婚礼婚庆","link":"####"},
	 {"title":"婚纱摄影","link":"####"}
	],[//主题产品2
	  {"title":"浪漫之旅","link":"####"},
	  {"title":"亲子体验","link":"####"},
	  {"title":"拓展训练","link":"####"},
	  {"title":"团队出游","link":"####"},
	],[ //3
	   
	],[ //紫色印象 4
	  {"title":"花田美食","link":"####"},
	  {"title":"花海露营","link":"####"}
	],[ //那些花儿 5
		 
	],[ //熏衣农场 6
		 
	],[ //有关花田 7
	  {"title":"项目综述","link":"####"},
	  {"title":"目标定位","link":"####"},
	  {"title":"设计构思","link":"####"}
	],[
	   //8
	],[//花田产品 9
	  {"title":"精油","link":"####"},
	  {"title":"盆栽","link":"####"},
	  {"title":"香薰","link":"####"},
	  {"title":"纪念品","link":"####"}
	],[
	   //10
	],[//花田周边11
	  {"title":"周边度假","link":"####"},
	  {"title":"周边景区","link":"####"},
	  {"title":"周边美食","link":"####"}
	]
];
$(function(){
	var mainLeft = $('#main').offset().left+1000;
	$("#contact").css("left",mainLeft);
	var box_timer = "";
	$(".partcon img").bind("mouseover",function(){
		//var left = $(this).offset().left; 通过jquery获取位置，浏览器之间不一直，不采用此方法
		if(box_timer!=null){
			clearTimeout(box_timer);
		}
		var num = parseInt($(this).attr("num"));
		var top = 0;
		if(num>5&&num<9){
			top = 200;
		}
		if(num>=9){
			top = 400;
		}
		createLink(links[num-1]);
		switch(num){
			case 1:
			$("#showbox").css({top:top,left:200,display:"block"});
				break;
			case 2:
			$("#showbox").css({top:top,left:400,display:"block"});
				break;
			case 4:
				$("#showbox").css({top:top,left:800,display:"block"});
				break;
			case 7:
				$("#showbox").css({top:top,left:600,display:"block"});
				break;
			case 8:
				$("#showbox").css({top:top,left:800,display:"block"});
				break;
			case 9:
				$("#showbox").css({top:top,left:600,display:"block"});
				break;
			case 10:
				$("#showbox").css({top:top,left:800,display:"block"});
				break;
			case 11:
				$("#showbox").css({top:top,left:600,display:"block"});
				break;
			break;
		}
	}).bind("mouseout",function(){
		box_timer = setTimeout(function(){
			$("#showbox").hide();
		},10);
	});
	$("#showbox").bind("mouseover",function(){
		clearTimeout(box_timer);
		$("this").show();
	}).bind("mouseout",function(){
		$("#showbox").hide();
	});
});
function createLink(obj){
	$("#showbox").empty();
	for(var i=0,length=obj.length;i<length;i++){
		$("<a href='"+obj[i].link+"'>"+obj[i].title+"</a>").appendTo($("#showbox"));
	}
}
