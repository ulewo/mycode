$(function(){
	var parents = $(".parent");
	$.each(parents,function(index,item){
		parents.eq(index).bind("click",function(){
			$(".parent").removeClass("select");
			$(this).addClass("select");
			$(".child").hide();
			$(".child").eq(index).slideDown("slow");
		});
	});
})
