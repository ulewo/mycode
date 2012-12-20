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
<script type="text/javascript" src="../js/group.addarticle.js"></script>

<script type="text/javascript" charset="utf-8" src="../editor/kindeditor-min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/jquery.min.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/group.article.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/util.js"></script>
<script type="text/javascript">
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
</script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
	<jsp:include page="head.jsp"/>
	<div class="ad_con">
		<form action="subQuote.jspx" method="post"  name="example" id="subForm">
		<input type="hidden" name="id" value="${id}"  />
		<div class="ad_quto">
			<img border="0" src="../images/quto.gif">
			<b>引用&nbsp;
			<span style="color:blue">${reArticle.authorName}</span>
			&nbsp;在&nbsp;${fn:substring(reArticle.reTime,0,16)}&nbsp;的发表</b>
			<div style="margin-top:5px;">${reArticle.content}</div>
		</div>
		<div class="ad_content">
			<textarea id="content" name="content"  style="width:1000px;height:400px;visibility:hidden;"></textarea>
		</div>
		<div class="ad-part" style="height:200px;">
			<div class="ad-title">上传图片：</div>
			<div class="ad-input">
			</div>
		</div>
		<div id="images" style="display:none;">
			<div id="imagecon"></div>
			<div style="clear:left;"></div>
			<div><input type="checkbox" onclick="selectAll()" id="selectall">全选
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:insertImage()">插入图片</a>
			</div>
		</div>
		<div class="ad-part bigButon" style="text-align:center;margin-top:10px;">
			<a href="javascript:subQutoForm()" onfocus="this.blur()" >发表话题</a>
		</div>
		</form>
	</div>
</div>	
<jsp:include page="../common/foot.jsp"/>
</body>
</html>