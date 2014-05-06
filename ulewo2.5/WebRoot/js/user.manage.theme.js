var centerTheme = {};
$(function(){
	$("input[name='theme']").bind("click",function(){
		ulewo_common.loading("show","正在设置主题.....");
		var theme = $(this).val();
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				"theme":theme
			},
			url : global.realPath+"/manage/setTheme.action",// 请求的action路径
			success : function(data) {
				ulewo_common.loading();
				if(data.result=="200"){
					ulewo_common.tipsInfo("设置成功",1);
				}else{
					ulewo_common.alert(data.msg,2);
				}
			}
		});
	});
});

