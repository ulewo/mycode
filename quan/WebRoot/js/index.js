$(function() {
	$("#talkBtn").click(function() {
		addTalk();
	});
	$("#talkcontent").focus(function() {
		$(this).val("");
		$(this).css({
			"color" : "#000000"
		});
	});
	$("#talkcontent").blur(function() {
		if ($(this).val() == "" || $(this).val() == "今天你吐槽了吗？") {
			$(this).val("今天你吐槽了吗？");
			$(this).css({
				"color" : "#A9A9A9"
			});
		}
	});
	loadTalk();
});