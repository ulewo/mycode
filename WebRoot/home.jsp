<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<%@ include file="common/path.jsp" %>
<script type="text/javascript" src="${realPath}/js/index.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/talk.js?version=2.7"></script>
<script type="text/javascript" src="${realPath}/js/emotion.data.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/date.js?version=2.5"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/index.css?version=2.6">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css?version=2.5">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="group_category">
		<c:forEach var="category" items="${groupCateGroy}" begin="0" end="5">
			<div class="category">
				<a href="${realPath}/group/all?pCategroyId=${category.groupCategoryId}" target="_blank" class="p_cate">${category.name}</a>
				<c:forEach var="sub" items="${category.children}" begin="0" end="3">
					<a href="${realPath}/group/all?categroyId=${sub.groupCategoryId}" target="_blank">${sub.name}</a>
				</c:forEach>
			</div>
		</c:forEach>
		<div class="clear"></div>
	</div>
  	<div class="main">
  		<div class="left">
  			<div style="margin-top:10px;padding-left:-10px;overflow:hidden;">
  			
	  			<script type="text/javascript">
				/*首页横幅，创建于2013-7-10*/
				var cpro_id = "u1317718";
				</script>
				<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
			
			</div>
	  		<div class="titinfo">
	  			<span>热门窝</span>
	  			<a href="group/all" target="_blank">&gt;&gt;更多</a>
	  		</div>
	  		<div>	
	  			<c:set var="num" value="0"></c:set>
		  		<c:forEach var="group" items="${groups}">
		  			<div class="n_part" <c:if test="${num%2==0}">style="margin-left:0px;"</c:if>>
		  				<div class="n_group">
		  					<div class="n_group_icon">
		  						<a href="${realPath}/group/${group.gid}" onFocus="this.blur()" target="_blank" title="${group.groupName}"><img src="${realPath}/upload/${group.groupIcon}"></a>
		  					</div>
		  					<div class="n_group_info">
		  						<div class="n_group_info_tit"><a href="${realPath}/group/${group.gid}" onFocus="this.blur()" class="n_group_name_link" target="_blank" title="${group.groupName}">${group.groupName}</a></div>
		  						<div class="n_group_info_con">成员${group.members}人|文章${group.topicCount}篇</div>
		  					</div>
		  					<div class="clear"></div>
		  				</div>
		  				<c:forEach var="article" items="${group.topicList}">
		  					<div class="n_article_title">
		  						<a href="${realPath}/group/${article.gid}/topic/${article.topicId}" onFocus="this.blur()" <c:if test="${article.newPost}">class="new_title_link"</c:if>   title="${article.title}" target="_blank">${article.title}</a><span class="recount">${article.commentCount}/${article.readCount}</span>
		  					</div>
		  				</c:forEach>
					</div>
					<c:set var="num" value="${num+1}"></c:set>
		  		</c:forEach>
	  			<div class="clear"></div>
	  		</div>	
	  		<div class="titinfo">
	  			<span>最新博文</span>
	  			<a href="blog" target="_blank">&gt;&gt;更多</a>
	  		</div>
  			  <div>
				<c:forEach var="blog" items="${blogList}">
					<div class="n_blog">
						<div class="n_blog_title">
		 					<a href="${realPath}/user/${blog.userId}/blog/${blog.blogId}" onFocus="this.blur()" class="article_title_link"  title="${blog.title}" target="_blank">${blog.title}</a>
		 					<span class="recount">${blog.commentCount}/${blog.readCount}</span>
	 			 		</div>
	 			 		<div class="n_blog_info">
	 			 			<a href="${realPath}/user/${blog.userId}">${blog.userName}</a> <span class="article_posttime">发表于 ${blog.showCreateTime}</span>
	 			 		</div>
	 			 		<div class="clear"></div>
 			 		</div>
				</c:forEach>	
	  		  </div> 
  		</div>
  		<div class="right">
  			<div class="create_wo"><a href="javascript:createWoWo()">创建我的窝窝</a></div>
  			<div class="todayinfo">
  				<div class="tody_tit">今天是</div>
  				<div class="tody_con">
  					<div id="tody_time"></div>
  					<div id="tody_festival"></div>
  				</div>
  				<div class="clear"></div>
  			</div>
  			<div class="tuhao">
  				<a href="userMarks" target="_blank">有乐窝土豪排行榜TOP50</a>
  			</div>
  			<div class="remarkcon" id="remarkcon">
				
			</div>
  			<div class="talk">
	  			<div>
	  				<div class="talkarea"><textarea id="talkcontent">今天你吐槽了吗？</textarea></div>
	  				<div class="talkbtn"><a href="javascript:void(0)" id="talkBtn">吐槽</a><img src="images/load.gif" id="talkload"></div>
	  				<div class="clear"></div>
	  			</div>
	  			<input type="hidden" id="imgUrl">
	  			<c:if test="${user!=null}">
	  			<div class="u_talk_sub">
	  				<div class="talkop">
	  					<a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a>
	  					<a href="javascript:showUploader();" class="icon_sw_img" title="图片上传"></a>
	  				</div>
	  				<span class="result_info"><i id="warm_icon"></i><span id="warm_info" style="padding-top:5px;"></span></span>
	  				<div class="clear"></div>
	  				<div id="talk_img_con">
	  					<div class="talk_img_tit">
	  						<span class='talk_img_tit_tit'>图片上传</span>
	  						<span class='talk_img_tit_close'><a href="javascript:closeUploader()">关闭</a></span>
	  					</div>
	  					<div class="talk_img_fram" id="talk_img_fram">
	  						<iframe src="${realPath}/common/imgupload.jsp" width="260" height="30" frameborder="0"></iframe>
	  					</div>
	  					<div id="talk_img_showimg">
	  						<img src=""><br>
	  						<a href="javascript:deleteImg()">删除</a>
	  					</div>
	  				</div>
	  				
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
	  			</c:if>
	  			<div id="talklist"></div>
	  			<div class='moretalk'><a href='${realPath}/blast'>看看大家都在吐槽什么&gt;&gt;</a></div>
  			</div>
  			 <div style="margin-top:10px;margin-bottom:5px;">
  			 <!-- 
  			 	<script type="text/javascript">
					/*首页标签广告，创建于2013-11-16*/
					var cpro_id = "u1412773";
					</script>
					<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
  				-->
  			 </div>
  			 <div class="app_info">
			  	 	<div><a href="${realPath}/ulewoapp"><img src="${realPath}/images/androidappicon.png"></a></div>
			  	 	<div style="color:#666666">扫一扫下载有乐窝安卓客户端</div>
			  	 	<div class="qq_qun">有乐窝交流群:<span style="color:#3E62A6">321576517</span></div>
			 </div>
			 <div style="margin-top:10px;margin-bottom:10px;">
			 	<a href="http://weibo.com/u8china" target="_blank"><img src="images/wb_logo.png"></a>
			 </div>
  			</div>
  			<div class="clear"></div>
  		</div>
  	<%@ include file="common/foot.jsp" %>
</body>
</html>