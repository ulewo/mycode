<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="right_con">
	<div class="list_tag">
		<div class="list_tag_tit_area">
			<div class="list_tag_tit">收听</div>
			<div class="list_tag_blank"></div>
			<div class="clear"></div>
		</div>
		<div class="listen">
			<a href="http://user.qzone.qq.com/2574876079" target="_blank">腾讯微博</a>
			<a href="http://t.qq.com/u8china" target="_blank">QQ空间</a>
			<a href="http://weibo.com/u8china" target="_blank">新浪微博</a>
			<a href="http://www.renren.com/487037932" target="_blank">人人网主页</a>
			<div class="clear"></div>
		</div>
		<div class="list_tag_tit_area list_tag_area">
			<div class="list_tag_tit">标签</div>
			<div class="list_tag_blank"></div>
			<div class="clear"></div>
		</div>
		<div class="tag">
			<a href="javascript:tagClick('伤不起')">伤不起</a>	
			<a href="javascript:tagClick('蛋疼')">蛋疼</a>
			<a href="javascript:tagClick('钓鱼岛')">钓鱼岛</a>
			<a href="javascript:tagClick('你懂的')">你懂的</a>
			<a href="javascript:tagClick('拜金')">拜金</a>
			<a href="javascript:tagClick('草泥马')">草泥马</a>
			<div class="clear"></div>
		</div>
	</div>
	<div class="qqgrouparea">
		qq群 36661697
	</div>
	<form action="search.jsp" method="post" id="searchForm">
		<input type="hidden" name="searchKey" id="searchKey">
	</form>
	<script type="text/javascript">
		function tagClick(tag){
			tag = encodeURI(encodeURI(tag)); 
			document.location.href="search?searchKey="+tag;
		}
	</script>		
</div>