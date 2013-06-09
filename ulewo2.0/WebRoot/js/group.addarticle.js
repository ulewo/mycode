var editor;
$(function(){
	$("#new_article_p").bind("click",showAddForm);
	
});

function showAddForm(){
	$(this).hide();
	$("#add_article").show();
	if(editor==null){
		var width = $("#add_article").outerWidth(true);
		window.UEDITOR_CONFIG.initialFrameWidth = parseInt(width-20);
		editor = new UE.ui.Editor();
		editor.render("editor");
	}
	editor.ready(function(){
	        editor.setContent("");
	});
}
/*********取消***********/
function cancelAdd(){
	$("#new_article_p").show();
	$("#add_article").hide();
}
    