$(function(){
	$(".edit_item").bind("click",editItem);
	$("#cancel_edit").bind("click",cancelEdit);
	$("#save_btn").bind("click",saveItem);
	$(".deleteItem").bind("click",deleteItem);
});


function editItem(){
	var item_con =  $(this).parent().parent();
	var name_input = item_con.children().eq(0);
	var rang_input = item_con.children().eq(1);
	$("#itemId").val($(this).attr("name"));
	$("#item_name").val(name_input.text());
	$("#item_code").val(rang_input.text());
	$("#save_btn").html("确定");
}

function cancelEdit(){
	$("#item_name").val("");
	$("#item_code").val("");
	$("#itemId").val("");
	$("#save_btn").html("新增");
}

function saveItem(){
	if($("#itemId").val()==""&&($(".item_con").length>8)){
		alert("最多只能添加8个分类");
		return;
	}
	var itemName = $("#item_name").val().trim();
	var item_code = $("#item_code").val().trim();
	if(itemName==""){
		alert("分类名称不能为空！");
		return;
	}
	if(itemName.length>10){
		alert("分类名称不能超过10字符！");
		return;
	}
	if(item_code==""){
		alert("编号不能为空");
		return;
	}
	if(!item_code.isNumber()){
		alert("编号必须是数字");
		return;
	}
	$("#itemForm").submit();
}

function deleteItem(){
	
	if($(this).attr("count")>0){
		alert("该分类下有文章不能删除");
		return;
	}
	if(confirm("确定要删除分类吗？")){
		document.location.href=global.realPath+"/groupManage/"+global.gid+"/deleteItem?id="+$(this).attr("name");
	}
}