<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${group.groupName}-友吧中国</title>
<meta name="description" content="${group.groupName}-友吧中国">
<meta name="keywords" content="${group.groupName}">
<link rel="stylesheet"  href="../css/group.article.css" type="text/css"  />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="../editor/swfupload.js"></script>
<script type="text/javascript" src="../editor/handler.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/group.article.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
<script type="text/javascript">
var isHaveImg = false;
KE.show({
	id : 'content',
	allowFileManager : true,
	resizeMode : 1,
	newlineTag : 'br',
	urlType : 'relative',
	afterCreate : function(id) {
		KE.event.ctrl(document, 13, function() {
			KE.util.setData(id);
			document.forms['example'].submit();
		});
		KE.event.ctrl(KE.g[id].iframeDoc, 13, function() {
			KE.util.setData(id);
			document.forms['example'].submit();
		});
	}
});
function insertFile(file) {
    var img = new Image();
    img.src = file;
    var width = img.width;
    var html = "";
    if (width > 900) {
        html = "<img src='../upload/" + file + "' width='700'/>";
    } else {
        html = "<img src='../upload/" + file + "'/>";
    }
    KE.util.insertHtml("content", html);
}
function getImage(imageName) {
    var image = imageName.split("|");
    if(image[0]!=null&&!isHaveImg){
    	$("#faceImg").val(image[0]);
    	isHaveImg = true;
    }
    for (var i = 0; i < image.length; i++) {
        $("<div style='float:left;width:100px;margin-left:3px;text-align:center'>" + "<img src='../upload/" + image[i] + "' width='100' height='100'/>" + "<input type='checkbox' name='insertImg' value='" + image[i] + "'><a href='javascript:void()' onclick=inserSingle('"+image[i]+"')>插入</a></div>").appendTo($("#imagecon"));
    }
    $("#images").css({
        "display": "block"
    });
}
function insertImage(){
	var images = $("input[name='insertImg']:checked");
	var insertStr = "";
	if(images.length<1){
		alert("请选择要插入的图片");
	}else{
		for(var i=0;i<images.length;i++){
			insertStr =insertStr+ "<img src='../upload/" + images.eq(i).val() + "'/><br><br>"
		}
	}
	KE.util.insertHtml("content",insertStr);
}

function inserSingle(img){
	KE.util.insertHtml("content","<img src='../upload/" + img+ "'/><br><br>");
}
function selectAll(){
	if($("#selectall").attr("checked")){
		$("input[name='insertImg']").attr("checked",true);
	}else{
		$("input[name='insertImg']").attr("checked",false);
	}
}

function submitForm(){
	var title = $("#title").val();
	if(title.trim()==""){
		alert("标题不能为空");
		return;
	}else{
		title = title.replaceHtml();
		$("#title").val(title);
	}
	if(KE.util.isEmpty('content')){
		alert("内容不能为空");
		KE.util.focus('content')
		return ;
	}
	$("#subForm").submit();
}

</script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
	<jsp:include page="head.jsp"/>
	<div class="ad_con">
		<form action="subArticle.jspx" method="post"  name="example" id="subForm">
		<input type="hidden" name="image" id="faceImg" value="">
		<input type="hidden" name="gid" value="${gid}"  />
		<div class="ad_line">
			<div class="ad_title">
				<input type="text" name="title" id="title">&nbsp;&nbsp;发表在
			</div>
			<div class="ad_item">
				<select name="itemId">
  					<c:forEach var="item" items="${itemList}">
						<option value="${item.id}">${item.itemName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="ad_line">
			<input type="text" name="keyWord" id="keyWord">&nbsp;&nbsp;<span style="color:#999999">好的关键字可以让别人更容易找到此篇文章（可选）多个关键字用半角逗号隔开</span>
		</div>
		<div class="ad_content">
				<textarea id="content" name="content"  style="width:1000px;height:400px;visibility:hidden;"></textarea>
		</div>
		<div class="ad-part" style="height:200px;">
			<div class="ad-title">上传图片：</div>
			<div class="ad-input"><iframe src="../imageUpload/imageUpload.jsp" height="200" width="800" frameborder="0" style="text-align:left;"></iframe></div>
		</div>
		<div id="images" style="display:none;">
			<div id="imagecon"></div>
			<div style="clear:left;"></div>
			<div><input type="checkbox" onclick="selectAll()" id="selectall">全选
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:insertImage()">插入图片</a>
			</div>
		</div>
		<div class="ad-part bigButon" style="text-align:center;margin-top:10px;">
			<a href="javascript:submitForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		</form>
	</div>
</div>	
<jsp:include page="../common/foot.jsp"/>
</body>
</html>