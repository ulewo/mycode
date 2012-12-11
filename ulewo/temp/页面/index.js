function showface(obj){
	if($(".face_panel").length>0){
		$(".face_panel").remove();
	}
	var face_panel = $("<div class='face_panel'></div>").appendTo($(obj).parent());
	var panel_ul = $("<ul></ul>").appendTo(face_panel);
	var num = 0;
	for(var i=1;i<=48;i++){
		var li = $("<li class='face_single' style='background-position: left "+num+"px;'></li>").appendTo(panel_ul);
		li.bind("mouseover",faceOver);
		li.bind("mouseout",faceOut);
		num = num-25;
	}
}

function faceOver(){
	$(this).addClass("face_single_hover");
}

function faceOut(){
	$(this).removeClass("face_single_hover");
}