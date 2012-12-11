<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小友吧 大世界 小智慧 大财富 --友吧中国</title>
<meta name="description" content="友吧中国 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小友吧 大世界 小智慧 大财富 — 友吧中国">
<style>
	.spider,.spider_tit,.result{text-align:left;float:left;display:inline-block;padding:6px}
	.spider_tit{padding:10px}
	#page{width:20px;}
</style>
</head>
<body>
<jsp:include page="admin_menue.jsp"></jsp:include>
<div class="body_con">
	<div>
		<div class="spider_tit">爬虫</div>
		<div class="spider" id="spider">
		    <input type="radio" value="" name="source" checked="checked"/>全部
			<input type="radio" value="Q" name="source"/>糗事百科
			<input type="radio" value="H" name="source"/>哈哈
			<input type="radio" value="P" name="source"/>捧腹网
			共爬<input type="text" id="page">页
			<input type="button" onclick="spider()" value="开始爬了" id="spiderBtn">
		</div>
		<div class="clear"></div>
	</div>
	<div>
	<div class="spider_tit">统计</div>
	<div class="spider">
		<input type="button" value="开始统计" onclick="statistics()">
	</div>
	<div class="result" id="result">
	
	</div>
	</div>
</div>
<script type="text/javascript">
function spider(){
	$("#spiderBtn").attr("disabled",true);
	var type = $("input[name='source']:checked").val();
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data : {
			source : type,
			totalPage:$("#page").val()
		},
		dataType : "json",
		url : 'spider',// 请求的action路径
		success : function(data) {
			$("<span>"+data.result+"</span>").appendTo("#spider");
			$("#spiderBtn").attr("disabled",false);
		}
	});
}

function statistics(){
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		data : {
			time : "2012-10-07 12:12:10"
		},
		dataType : "json",
		url : 'statistics',// 请求的action路径
		success : function(data) {
			$("<span>糗事百科:"+data.count.Q+"</span>").appendTo("#result");
			$("<span>哈哈:"+data.count.H+"</span>").appendTo("#result");
			$("<span>捧腹:"+data.count.P+"</span>").appendTo("#result");
		}
	}); 
}
</script>
</body>
</html>