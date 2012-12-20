function addMore(obj,gid){
	var atrlength = $(".atr").length;
	if(atrlength>=8){
		alert("最多只能添加8个分类");
		return;
	}
	var p = obj.parentElement.parentElement;
	$(p).before($("<form action='createItem.jspx' method='post'>" +
				"<input type='hidden' name='gid' value="+gid+">" +
					"<div class='atr'>"+
					"<div class='atit'><input type='text' name='itemName'></div>"+
					"<div class='aorder'><input type='text' name='itemCode'></div>"+
					"<div class='aop mbt'>" +
						"<a href='javascript:void(0)' onclick='addItem(this)'>保存</a>"+
						"&nbsp;<a href='javascript:void(0)' onclick='cancelItem(this)'>删除</a>" +
					"</div>"+
					"</div>" +
				"</form>"));
}

function editeItem(obj){
	var p = obj.parentElement.parentElement;
	var titInput = $(p).children().eq(0).children().eq(0);
	var orderInput = $(p).children().eq(1).children().eq(0);
	var readonly = titInput.attr("readonly");
	if(readonly=="readonly"){
		titInput.attr("readonly",false);
		titInput.css("border","1px solid #EEC285");
		orderInput.attr("readonly",false);
		orderInput.css("border","1px solid #EEC285");
		$(obj).html("保存");
	}else{
		if(checkInfo(obj)){
			$(p.parentElement).attr("action","updateItem.jspx");
			$(p.parentElement).submit();
		}
	}
}

function addItem(obj){
	if(checkInfo(obj)){
		$(obj.parentElement.parentElement.parentElement).submit();
	}
}

function checkInfo(obj){
	var itemName = $(obj.parentElement.parentElement).children().eq(0).children().eq(0).val();
	var itemCode = $(obj.parentElement.parentElement).children().eq(1).children().eq(0).val();
	if(itemName.trim()==""){
		alert("分类名称不能为空");
		return false;
	}else if(itemName.realLength()>10){
		alert("分类名称长度不能超过10");
		return false;
	}else{
		$(obj.parentElement.parentElement).children().eq(0).children().eq(0).val(itemName.replaceHtml());
	}
	if(itemCode.trim()==""){
		alert("分类编号不能为空");
		return false;
	}else if(isNaN(itemCode.trim())){
		alert("分类编号必须是数字");
		return false;
	}else if(itemCode.trim().length>3){
		alert("分类编号不能超过3位数字");
		return false;
	}
	return true;
}

function cancelItem(obj){
	$(obj.parentElement.parentElement).remove();
}

function deleteItem(obj){
	$(obj.parentElement.parentElement.parentElement).attr("action","deleteItem.jspx");
	$(obj.parentElement.parentElement.parentElement).submit();
}
