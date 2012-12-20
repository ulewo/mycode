$(function(){
	$(".noEdit").click(function(){
		$(this).attr("class","edit");
		$(this).parent().parent().children().eq(4).children().eq(0).val("保存")
	});
	
	$(".buttonU").click(function(){
		var itemName = $(this).parent().parent().children().eq(2).children().eq(0).val();
		var itemCode = $(this).parent().parent().children().eq(3).children().eq(0).val();
		if(itemName.trim()==""){
				alert("分类名称不能为空");
				$(this).parent().parent().children().eq(2).children().eq(0).focus();
				return;
		}else if(itemName.realLength()>5){
				alert("分类名称长度不能超过5");
				$(this).parent().parent().children().eq(2).children().eq(0).focus();
				return;
		}else{
			$(this).parent().parent().children().eq(2).children().eq(0).val(itemName.replaceHtml());
		}
		
		if(itemCode.trimAll()==""){
			alert("分类编号不能为空");
			$(this).parent().parent().children().eq(3).children().eq(0).focus();
			return;
		}else if(isNaN(itemCode.trimAll())){
			alert("分类编号必须是数字");
			$(this).parent().parent().children().eq(3).children().eq(0).focus();
			return;
		}else if(itemCode.trimAll().length>3){
			alert("分类编号不能超过3位数字");
			$(this).parent().parent().children().eq(3).children().eq(0).focus();
			return;
		}
	       $(this).parent().parent().submit();
	});
	
});

function deleteItem(id,groupNumber){
	if(confirm('确定要删除吗？')){   
	   		 document.location="group/deleteItem.jspx?id="+id+"&groupNumber="+groupNumber;   
	} 
}

function submitForm(){
			var itemNum = $(".itemrow").length;
			if(itemNum>7){
				alert("最多只能添加8个分类");
				return;
			}
			var itemName = $("#itemName").val();
			var itemCode = $("#itemCode").val();
			if(itemName.trimAll()==""){
				alert("分类名称不能为空");
				$("#itemName").focus();
				return;
			}else if(itemName.realLength()>10){
				alert("分类名称长度不能超过10");
				$("#itemName").focus();
				return;
			}else{
				itemName = itemName.replaceHtml();
				$("#itemName").val(itemName.trimAll());
			}
			
			if(itemCode.trimAll()==""){
				alert("分类编号不能为空");
				$("#itemCode").focus();
				return;
			}else if(isNaN($("#itemCode").val())){
				alert("分类编号必须是数字");
				$("#itemCode").focus();
				return;
			}else if(itemCode.trimAll().length>3){
				alert("分类编号不能超过3位数字");
				$("#itemCode").focus();
				return;
			}
	       $("#subform").submit();
}