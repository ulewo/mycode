function addMore(obj, id) {
	var atrlength = $(".atr").length;
	if (atrlength >= 8) {
		alert("最多只能添加8个分类");
		return;
	}
	var p = obj.parentElement.parentElement;
	$(p)
			.before(
					$("<form action='createItem.jspx' method='post'>"
							+ "<input type='hidden' name='id' value="
							+ id
							+ ">"
							+ "<div class='atr'>"
							+ "<div class='atit'><input type='text' name='itemName' class='itemName'></div>"
							+ "<div class='aorder'><input type='text' name='itemRang' class='itemRang'></div>"
							+ "<div class='aop mbt'>"
							+ "<a href='javascript:void(0)' onclick='addItem(this)' class='save'>保存</a>"
							+ "&nbsp;<a href='javascript:void(0)' onclick='deleteItem(this)' class='del'>删除</a>"
							+ "</div>" + "</div>" + "</form>"));
}

function editeItem(obj) {
	var p = obj.parentElement.parentElement;
	var titInput = $(p).children().eq(0).children().eq(0);
	var orderInput = $(p).children().eq(1).children().eq(0);
	var readonly = titInput.attr("readonly");
	if (readonly == "readonly") {
		titInput.attr("readonly", false);
		titInput.css("border", "1px solid #EEC285");
		orderInput.attr("readonly", false);
		orderInput.css("border", "1px solid #EEC285");
		$(obj).html("保存");
	} else {
		addItem(obj);
	}
}

function addItem(obj) {
	if (!checkInfo(obj)) {
		return;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $(obj.parentElement.parentElement.parentElement).serialize(),
		url : "saveItem.jspx",// 请求的action路径
		success : function(data) {
			if (data.msg == "ok") {
				$(obj.parentElement.parentElement.parentElement).children().eq(
						0).val(data.id);
				$(obj.parentElement.parentElement).children().eq(0).children()
						.eq(0).css({
							"border" : "0px"
						}).attr("readonly", true);
				$(obj.parentElement.parentElement).children().eq(1).children()
						.eq(0).css({
							"border" : "0px"
						}).attr("readonly", true);
				$(obj).html("修改");
				$(obj).attr("onclick", "editeItem(this)");
				alert("保存成功!");
			} else {
				alert("保存失败!");
			}
		}
	});
}

function checkInfo(obj) {
	var itemName = $(obj.parentElement.parentElement).children().eq(0)
			.children().eq(0).val();
	var itemCode = $(obj.parentElement.parentElement).children().eq(1)
			.children().eq(0).val();
	if (itemName.trim() == "") {
		alert("分类名称不能为空");
		return false;
	} else if (itemName.realLength() > 10) {
		alert("分类名称长度不能超过10");
		return false;
	} else {
		$(obj.parentElement.parentElement).children().eq(0).children().eq(0)
				.val(itemName.replaceHtml());
	}
	if (itemCode.trim() == "") {
		alert("分类编号不能为空");
		return false;
	} else if (isNaN(itemCode.trim())) {
		alert("分类编号必须是数字");
		return false;
	} else if (itemCode.trim().length > 3) {
		alert("分类编号不能超过3位数字");
		return false;
	}
	return true;
}

function cancelItem(obj) {
	$(obj.parentElement.parentElement).remove();
}

function deleteItem(obj) {
	if ($(obj.parentElement.parentElement.parentElement).children().eq(0).val() == "") {
		$(obj.parentElement.parentElement).remove();
		return;
	}
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $(obj.parentElement.parentElement.parentElement).serialize(),
		url : "deleteItem.jspx",// 请求的action路径
		success : function(data) {
			if (data.result == "ok") {
				alert("删除成功!");
				$(obj.parentElement.parentElement).remove();
			} else if (data.result == "havearticle") {
				alert("该分类下有文章不能删除!");
			} else {
				alert("删除失败!");
			}
		}
	});
}
