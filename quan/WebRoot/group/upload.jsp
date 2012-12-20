<%@ page language="java" pageEncoding="GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>My JSP 'upload.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
<script type="text/javascript" charset="utf-8" src="../editor/swfupload.js"></script>
<script type="text/javascript" src="../editor/handler.js"></script>
		<script type="text/javascript">
			var swfu;
			window.onload = function () {  
				swfu = new SWFUpload({
					button_image_url:"../editor/butbig.jpg",
					button_placeholder_id:"uploadBtn",
					button_width:97,
					button_height:28,
					button_text:"选择文件",
					flash_url:"../editor/swfupload.swf",
					upload_url:"../avatarUpload",
					upload_complete_handler:uploadComplete,//用于处理文件上传结束的事件
					file_dialog_complete_handler : fileDialogComplete,//用于处理选择文件后触发的事件
					file_queued_handler : fileQueued,//用于处理选择文件后触发的事件
					upload_error_handler:uploadError,//用于处理上传失败触发的事件
					upload_success_handler:uploadSuccess//用于处理上传成功触发的事件
					});
				};
				</script>
	</head>

	<body>
		<span id="uploadBtn"></span>
		<br>
		<button onclick="swfu.startUpload()">上传</button>
		<br>
		<table border="1" id="filesTable" style="display:none">
		<th>name</th>
		<th>size</th>
		<th>status</th>
		</table>
	</body>
</html>
