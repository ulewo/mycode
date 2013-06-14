$(function(){
	$(".edit_item").bind("click",editItem);
	$("#cancel_edit").bind("click",cancelEdit);
});


function editItem(){
	var item_con =  $(this).parent().parent();
	var name_input = item_con.children().eq(0);
	var rang_input = item_con.children().eq(1);
	$("#item_name").val(name_input.text());
	$("#item_rang").val(rang_input.text());
	$("#save_btn").html("确定");
}

function cancelEdit(){
	$("#item_name").val("");
	$("#item_rang").val("");
	$("#save_btn").html("新增");
}