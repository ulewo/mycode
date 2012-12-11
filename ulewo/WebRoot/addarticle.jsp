<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小友吧 大世界 小智慧 大财富 --你乐我</title>
<meta name="description" content="你乐我 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小友吧 大世界 小智慧 大财富 — 你乐我">
<script type="text/javascript" src="js/addArticle.js"></script>
<link rel="stylesheet" type="text/css" href="css/addarticle.css" />
<style type="text/css">
.sel5{background:#4EB2D4}
</style>
</head>
<body>
<jsp:include page="menue.jsp"></jsp:include>
<div class="body_con">
	<input type="hidden" id="imgUrl">
	<div class="left" style="background:#F5F5F5;padding:10px;width:715px;">
		<div class="content">
			<textarea id="content" style="width:100%;height:200px;"></textarea>
		</div>
		<div class="media_con">
			<label class="media_tit">媒体</label>
			<div class="media_tag"><input type="radio" name="medio_type" checked="checked" onclick="setMedioType(this)" value="P">图片<input type="radio" name="medio_type" onclick="setMedioType(this)" value="V">视频</div>
			<div class="img_tag" id="img_tag"><iframe src="upload.jsp" width="300" height="25" frameborder="0" scrolling="no"></iframe></div>
			<div class="video_tag" id="video_tag"><input type="text" name="video_url" id="video_url"><a href="">详细帮助</a><span>目前支持：优酷、土豆、酷6、6间房、56视频  粘贴播放页地址即可</span></div>
		</div>
		<div class="form_con">
			<label>标签</label>
			<input type="text" id="tag">
		</div>
		<div class="form_con">
			<label>选项</label>
			<input type="checkbox" id="agreement">我已经阅读并同意投稿需知
		</div>
		<div class="form_con">
			<div>
				<a href="javascript:addArticle()" class="subtn">投递</a>
			</div>
		</div>
		<div style="line-height:20px;">
			投稿须知<br>
			自己的或听说过的 真实有笑点，不含政治、色情、广告、个人隐私、歧视等内容。<br>
			投稿需经过审核后发表。<br>
			转载请注明出处。<br>
			内容版权归你乐我网站所有。<br>
		</div>
	</div>
	<div class="right">
		<jsp:include page="right.jsp"></jsp:include>		
	</div>
	<div class="clear"></div>
</div>
<jsp:include page="foot.jsp"></jsp:include>
</body>
</html>