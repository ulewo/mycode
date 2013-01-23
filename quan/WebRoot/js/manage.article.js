$(function() {
	$("#checkAll").click(function() {
		if ($(this).attr("checked")) {
			$(".checkId").attr("checked", true);
		} else {
			$(".checkId").attr("checked", false);
		}

	})
});

/* 置顶 */
function setTop(type) {
	if (checkSelect()) {
		$("#opType").val(type);
		$("#subForm").attr("action", "setTop.jspx");
		$("#subForm").submit();
	}
}

/* 设置精华 */
function setGood(type) {
	if (checkSelect()) {
		$("#opType").val(type);
		$("#subForm").attr("action", "setGood.jspx");
		$("#subForm").submit();
	}
}

/* 设置个性标题 */
function setTitle() {
	if (checkSelect()) {
		$("#subForm").attr("action", "setTitle.jspx");
		$("#subForm").submit();
	}
}

/* 删除 */
function deleteArticle() {
	if (checkSelect()) {
		$("#subForm").attr("action", "deleteArticle.jspx");
		$("#subForm").submit();
	}
}

function checkSelect() {
	if ($(".checkId:checked").length < 1) {
		alert("请选择要操作的主题");
		return false;
	}
	return true;
}