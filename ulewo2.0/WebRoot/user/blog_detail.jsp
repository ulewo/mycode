<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${blog.title}-有乐窝</title>
<meta name="description" content="${blog.title} -有乐窝">
<meta name="keywords" content="${blog.title} -有乐窝">
<script type="text/javascript">
<!--
window.UEDITOR_HOME_URL = "${realPath}/ueditor/";
//-->
</script>

<script type="text/javascript" src="${realPath}/js/scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="${realPath}/js/styles/SyntaxHighlighter.css"/>
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.blog.css">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div class="main">
		<div class="left">
			<%@ include file="left.jsp" %>
			<div class="left_item">
				<div class="left_item_tit">博客分类</div>
				<div class="left_img_p">
					<div class="blog_item"><a href="${realPath}/user/${userId}/blog">全部文章</a><span>(${countTotal})</span></div>
					<c:forEach var="item" items="${blogItemList}">
						<div class="blog_item"><a href="${realPath}/user/${userId}/blog?itemId=${item.id}">${item.itemName}</a><span>(${item.articleCount})</span></div>
					</c:forEach>
				</div>
			</div>
			<div class="left_item">
				<div class="left_item_tit">阅读排行</div>
				<div class="left_img_p">
					<c:set var="num" value="1"/>
					<c:forEach var="blog" items="${hotlist}">
						<div class="blog_rang"><span style="color:#3E62A6">${num}.</span><a href="${realPath}/user/${blog.userId}/blog/${blog.id}">${blog.title}</a></div>
						<c:set var="num" value="${num+1}"></c:set>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="blog_list">
				<div class="right_top_m">
					<a href="${realPath}/user/${userId}">空间</a>&gt;&gt;<a href="${realPath}/user/${userId}/blog">博客</a>
					&gt;&gt;
					<c:if test="${blog.itemId==null||blog.itemId==''}">
						<a href="${realPath}/user/${userId}/blog/">全部分类</a>
					</c:if>
					<a href="${realPath}/user/${userId}/blog?itemId=${blog.itemId}">${blog.itemName}</a>
					&gt;&gt;博客正文
				</div>
				<div class="blog_detail">
						<div class="blog_list_tit"><span id="blogtitle">${blog.title}</span></div>
						<div class="blog_item_op">
							<div class="blog_item_op_info"><span>发布于 ${blog.postTime}，阅读(<span class="blog_item_op_red">${blog.readCount}</span>)|评论(<span class="blog_item_op_red">${blog.reCount}</span>)</span></div>
							<div class="blog_item_op_favorite"><span id="favoriteCount">0</span>人收藏了此文章 <span id="op_favorite"></span></div>
							<div class="clear"></div>
						</div>
						<div class="blog_content">
							<div style="display:inline-block;float:right;margin-left:10px;">
								<div>
								<script type="text/javascript">
									/*博客详情，创建于2013-7-10*/
									var cpro_id = "u1317826";
									</script>
								<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
								</div>
								<div class="ad_info">广告也精彩，喜欢就点点吧^_^</div>
							</div>
							${blog.content}
						</div>
				</div>
			</div>
				<div class="blog_reply">
					<div class="blog_reply_tit">评论</div>
					<div class="blog_reply_list" id="messagelist">
					</div>
				</div>
				<div class="blog_reply_form">
					<form id="subform"> 
				  		<input type="hidden" name="atUserName" id="atUserName">
				  		<input type="hidden" name="atUserId" id="atUserId">
				  		<div>
				  			<div class="blogreply_icon">
				  				<c:if test="${user!=null}">
									<img src="${realPath}/upload/${user.userLittleIcon}" width="37">
								</c:if>
								<c:if test="${user==null}">
									<img src="${realPath}/upload/default.gif" width="37">
								</c:if>
				  			</div>
				  			<div class="blog_reply_content">
				  				<div class="content"><textarea name="content" id="content"></textarea></div>
				  				<div class="sub_op">
				  					<div class="talkop">
						  				<a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a>
	  								</div>
						  			<div class="subbtn">
						  				<a href="javascript:void(0)" class="btn" onfocus="this.blur()" id="sendBtn">发表留言</a>
						  				<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
						  				<div class="clear"></div>
						  			</div>
				  					<div class="warm_info">最多输入500字符</div>
				  					<div class="clear"></div>
				  					<div class="pm_emotion" id="pm_emotion_cnt">
					  				<div class="pm_emotion_panel">
								    	<div class="pm_emotions_bd" style="width:300px;">
								            <a href="javascript:void(0)" title="[围观]"><img src="${realPath }/images/emotions/wg_org.gif"></a>
								            <a href="javascript:void(0)" title="[威武]"><img src="${realPath }/images/emotions/vw_org.gif"></a>
								            <a href="javascript:void(0)" title="[给力]"><img src="${realPath }/images/emotions/geili_org.gif"></a>
								            <a href="javascript:void(0)" title="[浮云]"><img src="${realPath }/images/emotions/fuyun_org.gif"></a>
								            <a href="javascript:void(0)" title="[奥特曼]"><img src="${realPath }/images/emotions/otm_org.gif"></a>
								            <a href="javascript:void(0)" title="[兔子]"><img src="${realPath }/images/emotions/rabbit_org.gif"></a>
								            <a href="javascript:void(0)" title="[熊猫]"><img src="${realPath }/images/emotions/panda_org.gif"></a>
								            <a href="javascript:void(0)" title="[飞机]"><img src="${realPath }/images/emotions/travel_org.gif"></a>
								            <a href="javascript:void(0)" title="[冰棍]"><img src="${realPath }/images/emotions/ice.gif"></a>
								            <a href="javascript:void(0)" title="[干杯]"><img src="${realPath }/images/emotions/cheer.gif"></a>
								            <a href="javascript:void(0)" title="[orz]"><img src="${realPath }/images/emotions/orz1_org.gif"></a>
								            <a href="javascript:void(0)" title="[囧]"><img src="${realPath }/images/emotions/j_org.gif"></a>
								            <a href="javascript:void(0)" title="[风扇]"><img src="${realPath }/images/emotions/fan.gif"></a>
								            <a href="javascript:void(0)" title="[呵呵]"><img src="${realPath }/images/emotions/smile.gif"></a>
								            <a href="javascript:void(0)" title="[嘻嘻]"><img src="${realPath }/images/emotions/tooth.gif"></a>
								            <a href="javascript:void(0)" title="[哈哈]"><img src="${realPath }/images/emotions/laugh.gif"></a>
								            <a href="javascript:void(0)" title="[爱你]"><img src="${realPath }/images/emotions/love.gif"></a>
								            <a href="javascript:void(0)" title="[晕]"><img src="${realPath }/images/emotions/dizzy.gif"></a>
								            <a href="javascript:void(0)" title="[泪]"><img src="${realPath }/images/emotions/sad.gif"></a>
								            <a href="javascript:void(0)" title="[馋嘴]"><img src="${realPath }/images/emotions/cz_thumb.gif"></a>
								            <a href="javascript:void(0)" title="[抓狂]"><img src="${realPath }/images/emotions/crazy.gif"></a>
								            <a href="javascript:void(0)" title="[哼]"><img src="${realPath }/images/emotions/hate.gif"></a>
								            <a href="javascript:void(0)" title="[抱抱]"><img src="${realPath }/images/emotions/bb_org.gif"></a>
								            <a href="javascript:void(0)" title="[可爱]"><img src="${realPath }/images/emotions/tz_org.gif"></a>
								            <a href="javascript:void(0)" title="[怒]"><img src="${realPath }/images/emotions/angry.gif"></a>
								            <a href="javascript:void(0)" title="[汗]"><img src="${realPath }/images/emotions/sweat.gif"></a>
								            <a href="javascript:void(0)" title="[困]"><img src="${realPath }/images/emotions/sleepy.gif"></a>
								            <a href="javascript:void(0)" title="[害羞]"><img src="${realPath }/images/emotions/shame_org.gif"></a>
								            <a href="javascript:void(0)" title="[睡觉]"><img src="${realPath }/images/emotions/sleep_org.gif"></a>
								            <a href="javascript:void(0)" title="[钱]"><img src="${realPath }/images/emotions/money_org.gif"></a>
								            <a href="javascript:void(0)" title="[偷笑]"><img src="${realPath }/images/emotions/hei_org.gif"></a>
								            <a href="javascript:void(0)" title="[酷]"><img src="${realPath }/images/emotions/cool_org.gif"></a>
								            <a href="javascript:void(0)" title="[衰]"><img src="${realPath }/images/emotions/cry.gif"></a>
								            <a href="javascript:void(0)" title="[吃惊]"><img src="${realPath }/images/emotions/cj_org.gif"></a>
								            <a href="javascript:void(0)" title="[闭嘴]"><img src="${realPath }/images/emotions/bz_org.gif"></a>
								            <a href="javascript:void(0)" title="[鄙视]"><img src="${realPath }/images/emotions/bs2_org.gif"></a>
								            <a href="javascript:void(0)" title="[挖鼻屎]"><img src="${realPath }/images/emotions/kbs_org.gif"></a>
								            <a href="javascript:void(0)" title="[花心]"><img src="${realPath }/images/emotions/hs_org.gif"></a>
								            <a href="javascript:void(0)" title="[鼓掌]"><img src="${realPath }/images/emotions/gz_org.gif"></a>
								            <a href="javascript:void(0)" title="[失望]"><img src="${realPath }/images/emotions/sw_org.gif"></a>
								            <a href="javascript:void(0)" title="[思考]"><img src="${realPath }/images/emotions/sk_org.gif"></a>
								            <a href="javascript:void(0)" title="[生病]"><img src="${realPath }/images/emotions/sb_org.gif"></a>
								            <a href="javascript:void(0)" title="[亲亲]"><img src="${realPath }/images/emotions/qq_org.gif"></a>
								            <a href="javascript:void(0)" title="[怒骂]"><img src="${realPath }/images/emotions/nm_org.gif"></a>
								            <a href="javascript:void(0)" title="[太开心]"><img src="${realPath }/images/emotions/mb_org.gif"></a>
								            <a href="javascript:void(0)" title="[懒得理你]"><img src="${realPath }/images/emotions/ldln_org.gif"></a>
								            <a href="javascript:void(0)" title="[右哼哼]"><img src="${realPath }/images/emotions/yhh_org.gif"></a>
								            <a href="javascript:void(0)" title="[左哼哼]"><img src="${realPath }/images/emotions/zhh_org.gif"></a>
								            <a href="javascript:void(0)" title="[嘘]"><img src="${realPath }/images/emotions/x_org.gif"></a>
								            <a href="javascript:void(0)" title="[委屈]"><img src="${realPath }/images/emotions/wq_org.gif"></a>
								            <a href="javascript:void(0)" title="[吐]"><img src="${realPath }/images/emotions/t_org.gif"></a>
								            <a href="javascript:void(0)" title="[可怜]"><img src="${realPath }/images/emotions/kl_org.gif"></a>
								            <a href="javascript:void(0)" title="[打哈气]"><img src="${realPath }/images/emotions/k_org.gif"></a>
								            <a href="javascript:void(0)" title="[黑线]"><img src="${realPath }/images/emotions/h_org.gif"></a>
								            <a href="javascript:void(0)" title="[顶]"><img src="${realPath }/images/emotions/d_org.gif"></a>
								            <a href="javascript:void(0)" title="[疑问]"><img src="${realPath }/images/emotions/yw_org.gif"></a>
								            <a href="javascript:void(0)" title="[握手]"><img src="${realPath }/images/emotions/ws_org.gif"></a>
								            <a href="javascript:void(0)" title="[耶]"><img src="${realPath }/images/emotions/ye_org.gif"></a>
								            <a href="javascript:void(0)" title="[good]"><img src="${realPath }/images/emotions/good_org.gif"></a>
								            <a href="javascript:void(0)" title="[弱]"><img src="${realPath }/images/emotions/sad_org.gif"></a>
								            <a href="javascript:void(0)" title="[不要]"><img src="${realPath }/images/emotions/no_org.gif"></a>
								            <a href="javascript:void(0)" title="[ok]"><img src="${realPath }/images/emotions/ok_org.gif"></a>
								            <a href="javascript:void(0)" title="[赞]"><img src="${realPath }/images/emotions/z2_org.gif"></a>
								            <a href="javascript:void(0)" title="[来]"><img src="${realPath }/images/emotions/come_org.gif"></a>
								            <a href="javascript:void(0)" title="[蛋糕]"><img src="${realPath }/images/emotions/cake.gif"></a>
								            <a href="javascript:void(0)" title="[心]"><img src="${realPath }/images/emotions/heart.gif"></a>
								            <a href="javascript:void(0)" title="[伤心]"><img src="${realPath }/images/emotions/unheart.gif"></a>
								            <a href="javascript:void(0)" title="[钟]"><img src="${realPath }/images/emotions/clock_org.gif"></a>
								            <a href="javascript:void(0)" title="[猪头]"><img src="${realPath }/images/emotions/pig.gif"></a>
								            <a href="javascript:void(0)" title="[话筒]"><img src="${realPath }/images/emotions/m_org.gif"></a>
								            <a href="javascript:void(0)" title="[月亮]"><img src="${realPath }/images/emotions/moon.gif"></a>
								            <a href="javascript:void(0)" title="[下雨]"><img src="${realPath }/images/emotions/rain.gif"></a>
								            <a href="javascript:void(0)" title="[太阳]"><img src="${realPath }/images/emotions/sun.gif"></a>
								            <a href="javascript:void(0)" title="[蜡烛]"><img src="${realPath }/images/emotions/lazu_org.gif"></a>
								            <a href="javascript:void(0)" title="[礼花]"><img src="${realPath }/images/emotions/bingo_thumb.gif"></a>
								            <a href="javascript:void(0)" title="[玫瑰]"><img src="${realPath }/images/emotions/rose.gif"></a>
								            <div style="clear:both;"></div>
								        </div>
								    </div>
					</div> 
				  				</div>
				  			</div>
				  			<div class="clear"></div>
				  		</div>
				  	</form>
			  		<c:if test="${user==null}">
							<div class="shade blogshade" id="shade">
								<div class="shadeLogin">回复，请先 <a href="javascript:goto_login()">登录</a>&nbsp;&nbsp;<a href="javascript:goto_register()">注册</a></div>
							</div>
					</c:if>
				</div>
		</div>
		<div style="clear:left;"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.blogreply.js"></script>
	<script type="text/javascript" src="${realPath}/js/emotion.data.js"></script>
	<script type="text/javascript">
		var realPath = "${realPath}";
		var blogId = "${blog.id}";
		var userId="${userId}";
		var sessionUserId = "${user.userId}";
	</script>
	 <%@ include file="../common/foot.jsp" %>
</body>
</html>