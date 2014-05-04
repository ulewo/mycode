var userInfo = {};
userInfo.save = function(){
	var age = $("#age").val().trim();
	var address = $("#address").val();
	var work = $("#work").val().trim();
	var characters= $("#characters").val().trim();
	if(age!=""&&!age.isNumber()){
		ulewo_common.alert("年龄只能是数字",1);
		return;
	}
	
	if(address!=""&&address.length>50){
		ulewo_common.alert("居住地不能超过50字符",1);
		return;
	}
	
	if(work!=""&&work.length>50){
		ulewo_common.alert("职业不能超过50字符",1);
		return;
	}
	
	if(characters.length>200){
		ulewo_common.alert("个性签名不能超过200",1);
		return;
	}
	$("#saveBtn").linkbutton('disable');
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		data : $("#user_form").serialize(),
		url : global.realPath+"/manage/saveUserInfo.action",// 请求的action路径
		success : function(data) {
			$("#saveBtn").linkbutton('enable');
			if(data.result=="200"){
				ulewo_common.tipsInfo("保存成功",1);
			}else{
				ulewo_common.alert(data.msg,2);
			}
		}
	});
}
