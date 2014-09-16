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
<script type="text/javascript" src="${realPath}/js/jquery.jscrollpane.js?version=2.7"></script>
<script type="text/javascript" src="${realPath}/js/koala.min.1.5.js?version=2.7"></script>
<script type="text/javascript" src="${realPath}/js/index.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/talk.js?version=2.7"></script>
<script type="text/javascript" src="${realPath}/js/emotion.data.js?version=2.5"></script>
<script type="text/javascript" src="${realPath}/js/date.js?version=2.5"></script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/index.css?version=2.8">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css?version=2.6">
<link href="${realPath}/css/datouwang.css" rel="stylesheet" type="text/css" />

<style type="text/css">
.jp-container{width:260px;height:400px;position:relative;background:#fff;padding-bottom:10px;}
</style>
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
	  			<span>网事</span>
	  		</div>
	  		<div class="webthing">	
	  			<div class="web-left">
	  				<div class="web-left-image">
	  					<div id="fsD1" class="focus">  
						    <div id="D1pic1" class="fPic">  
						    	<c:forEach var="topic" items="${topics.imageTopics}">
						    		<div class="fcon" style="display: none;">
						            	<a target="_blank" href="${realPath}/group/${topic.gid}/topic/${topic.topicId}"><img src="${topic.defImage}" style="opacity: 1; "></a>
						            	<span class="shadow"><a target="_blank" href="${realPath}/group/${topic.gid}/topic/${topic.topicId}">${topic.title}</a></span>
						        </div>
						    	</c:forEach>
						    </div>
						    <div class="fbg">  
						    <div class="D1fBt" id="D1fBt">  
						        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>1</i></a>  
						        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>2</i></a>  
						        <a href="javascript:void(0)" hidefocus="true" target="_self" class="current"><i>3</i></a>  
						        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>4</i></a>  
						        <a href="javascript:void(0)" hidefocus="true" target="_self" class=""><i>5</i></a>
						    </div>  
						    </div>  
						    <span class="prev"></span>   
						    <span class="next"></span>   	 
						</div>
						<script type="text/javascript">
							Qfast.add('widgets', { path: "js/terminator2.2.min.js", type: "js", requires: ['fx'] });  
							Qfast(false, 'widgets', function () {
								K.tabs({
									id: 'fsD1',   //焦点图包裹id  
									conId: "D1pic1",  //** 大图域包裹id  
									tabId:"D1fBt",  
									tabTn:"a",
									conCn: '.fcon', //** 大图域配置class       
									auto: 1,   //自动播放 1或0
									effect: 'fade',   //效果配置
									eType: 'click', //** 鼠标事件
									pageBt:true,//是否有按钮切换页码
									bns: ['.prev', '.next'],//** 前后按钮配置class                          
									interval: 3000  //** 停顿时间  
								}) 
							}) 
						</script>
	  				</div>
	  			</div>
	  			<div class="web-right">
	  				<div class="titinfo sub">
	  					<span>社会热点</span>
	  				</div>
	  				<div class="web-right-new">
	  					<c:forEach var="topic" items="${topics.newsTopics4Life}">
		  					<div class="n_article_title float">
		  						<a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" title="${topic.title}" <c:if test='${topic.newPost==true}'>class="new"</c:if> target="_blank">${topic.title}</a>
		  						<span class="recount">${topic.showCreateTime}</span>
		  					</div>
	  					</c:forEach>
	  				</div>
	  			</div>
	  			<div class="clear"></div>
	  			<div class="titinfo sub">
	  					<span>挨踢资讯</span>
	  				</div>
	  				<div class="web-right-it">
	  					<c:forEach var="topic" items="${topics.newsTopics4IT}">
		  					<div class="n_article_title n_article_title_float">
		  						<a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" title="${topic.title}" <c:if test='${topic.newPost==true}'>class="it-new"</c:if> target="_blank">${topic.title}</a>
		  					</div>
	  					</c:forEach>
	  				</div>
	  				<div class="clear"></div>
	  		</div>	
	  		<div class="titinfo">
	  			<span>编程</span>
	  		</div>
	  		<div class="it">
	  			<div class="it-thing">
	  				<c:forEach var="topic" items="${topics.itTopics}">
	  					<div class="n_article_title">
		  					<a href="${realPath}/group/${topic.gid}" target="_blank" class="wo">[${topic.groupName}]</a>
	  						<a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" target="_blank" title="${topic.title}" class="it-title<c:if test='${topic.newPost==true}'> it-new</c:if>">${topic.title}</a>
	  						<a href="${realPath}/user/${topic.userId}" class="user" target="_blank">${topic.userName}</a>
	  					</div>
	  				</c:forEach>
	  			</div>
	  		</div>
	  		<div class="titinfo">
	  			<span>生活</span>
	  		</div>
	  		<div class="life">
	  			<div class="lief-image">
	  				<div class="life-image-con">
		  				<c:forEach var="topic" items="${topics.imageTopics4Life}">
		  					<a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" title="${topic.title}" target="_blank"><img src="${topic.defImage}"></a>
		  					<div class="image-title"><a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" target="_blank">${topic.title}</a></div>
		  				</c:forEach>
	  				</div>
	  			</div>
	  			<div class="lief-thing">
	  				<c:forEach var="topic" items="${topics.lifeTopics}">
	  					<div class="n_article_title">
		  					<a href="${realPath}/group/${topic.gid}" target="_blank" class="wo">[${topic.groupName}]</a>
	  						<a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" title="${topic.title}" target="_blank" class="life-title<c:if test='${topic.newPost==true}'> life-new</c:if>">${topic.title}</a>
	  						<a href="${realPath}/user/${topic.userId}" class="user" target="_blank">${topic.userName}</a>
	  					</div>
	  				</c:forEach>
	  			</div>
	  			<div class="clear"></div>
	  		</div>
	  		<div class="titinfo">
	  			<span>最新博文</span>
	  			<a href="blog" target="_blank">&gt;&gt;更多</a>
	  		</div>
 			<div>
				<c:forEach var="blog" items="${blogList}">
					<div class="n_blog">
						<div class="blog_icon">
							<a href="${realPath}/user/${blog.userId}"><img src="${realPath}/upload/${blog.userIcon}"></a>
						</div>
						<div class="blog-info">
							<div class="blog-title"><a href="${realPath}/user/${blog.userId}/blog/${blog.blogId}" onFocus="this.blur()" class="article_title_link"  title="${blog.title}" target="_blank">${blog.title}</a></div>
							<div class="blog-user-info" style="margin-top:10px;">
								<span class="blog-user-info-user"><a href="${realPath}/user/${blog.userId}">${blog.userName}</a></span>
								<span class="blog-user-info-time">${blog.showCreateTime}</span>
								<div class="clear"></div>
							</div>
						</div>
					</div>	
				</c:forEach>	
				<div class="clear"></div>
  		 	 </div> 
	  		<div class="titinfo">
	  			<span>推荐窝窝</span>
	  		</div>
	  		<div class="commend-wo">
	  			<c:forEach var="group" items="${groups}">
					<div class="group_item">
						<div class="group_icon">
							<a href="${realPath}/group/${group.gid}" target="_blank"><img src="${realPath}/upload/${group.groupIcon}" width="50"></a>
						</div>
						<div class="group_con">
							<div class="group_tit">
								<div class="group_title"><a href="${realPath}/group/${group.gid}" target="_blank">${group.groupName}</a></div>
							</div>
							<div class="group_info">
								创建者：<a href="${realPath}/user/${group.groupUserId}" target="_blank">${group.authorName}</a>&nbsp;|&nbsp;
								成员数：<span class="group_blue">${group.members}</span>&nbsp;|&nbsp;
								文章数：<span class="group_blue">${group.topicCount}</span>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</c:forEach>
				<div class="clear"></div>
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
 			<div id="keepshow">
 				<div class="remarkcon" id="remarkcon">
				</div>
		  			<div class="talk">
			  			<div>
			  				<div class="talkarea"><textarea id="talkcontent">今天你吐槽了吗？</textarea></div>
			  				<div class="talkbtn"><a href="javascript:void(0)" id="talkBtn">吐槽</a></div>
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
						<div id="talklists" class="jp-container">
							<c:forEach var="blast" items="${blastList}">
								<div class="talkitem">
									<div class="itemicon">
										<img src="${realPath}/upload/${blast.userIcon}" noLazyload="true" width="37">
									</div>
									<div class="itemcon">
										<span class="item_user">
											<a href="${realPath}/user/${blast.userId}">
												${blast.userName}
											</a>
										</span>
										<span class="item_content">
											：${blast.content}
										</span>
										<span class="item_time">
											<span class="item_time_time">
												${blast.showCreateTime}
											</span>
											<a href="${realPath}/user/${blast.userId}/blast/${blast.blastId}" class="item_time_s">
												(${blast.commentCount})
											</a>
											<c:if test="${blast.sourceFrom=='A'}">
												<span class='item_time_s'>&nbsp;Android&nbsp;</span>
											</c:if>
											<c:if test="${blast.imageUrl!=''&&blast.imageUrl!=null}">
												<a class='item_time_s' href="${realPath}/user/${blast.userId}/blast/${blast.blastId}"><img src='${realPath}/images/img.gif' border=0></a>
											</c:if>
											<a href="javascript:void(0)" opid="${blast.blastId}" type="L" disable="false" class="op_like ok_like_small item_time_s"
												style="display: none;" title="赞">
											</a>
										</span>
									</div>
									<div class="clear">
									</div>
								</div>
							</c:forEach>
						</div>
			  			<div class='moretalk'><a href='${realPath}/blast'>看看大家都在吐槽什么&gt;&gt;</a></div>
		  			</div>
	  			 <div class="titinfo">
		  			<span>公告</span>
		  		</div>
		  		<div>
					<c:forEach var="topic" items="${topics.noticTopic}">
						<div class="notice">
							<div class="notice-info">
								<div class="notice-title"><a href="${realPath}/group/${topic.gid}/topic/${topic.topicId}" onFocus="this.blur()" class="article_title_link"  title="${blog.title}" target="_blank">${topic.title}</a></div>
							</div>
						</div>	
					</c:forEach>	
	  		 	 </div>
				 <div style="margin-top:10px;margin-bottom:10px;">
			 		<a href="http://weibo.com/u8china" target="_blank"><img src="images/wb_logo.png"></a>
			 	</div>
 			</div>
 			<!-- 
  		 	  -->
  		</div>
  		<div class="clear"></div>
  	</div>
  	<%@ include file="common/foot.jsp" %>
</body>
</html>