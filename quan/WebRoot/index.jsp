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
<script type="text/javascript" src="js/jquery.min.js"></script>
<!-- <script type="text/javascript" src="js/index.js"></script> -->
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/talk.js"></script>
<script type="text/javascript" src="js/emotion.data.js"></script>
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/index_new.css">
<link rel="stylesheet" type="text/css" href="css/talk.css">
<script type="text/javascript">
	function createGroup(){
		var user = "${user}";
		if(user==""){
			showLoginDilog('user/login.jsp')
		}
	}
</script>
</head>
<body>
	 <%@ include file="common/head.jsp" %>
  	<div class="main">
  		<div class="left">
  			<div class="titinfo">每日图文</div>
  			<div class="hot">
  				<div>
  				<c:set var="num" value="0"/>
  				<c:forEach var="article" items="${imgArticle}">
  					<c:set var="num" value="${num+1}"/>
  					<div class="day_pic" <c:if test="${num==1}">style='margin-left:5px'</c:if> >
					<a href="group/post.jspx?id=${article.id}" class="day_pic_link"><span class="day_pic_con"><img src="upload/${article.image}"></span></a>
					<div class="day_pic_tit"><a href="group/post.jspx?id=${article.id}" title="${article.title}" target="_blank">${article.title}</a></div>
				</div> 
  				</c:forEach>
				<div class="clear"></div>
			  </div>
  			</div>
  			<div class="titinfo">最新文章</div>
	  			<ul class="new_article_list">
	  				<c:forEach var="article" items="${list}">
	  					<li><span class="article_tit"><a href="group/group.jspx?gid=${article.gid}" target="_blank">[${article.groupName}]</a><a href="group/post.jspx?id=${article.id}" class="sec_span2"  title="${article.title}" target="_blank">${article.title}</a></span><span class="article_user">${article.postTime} by ${article.authorName}</span></li>
	  				</c:forEach>
	  			</ul>
  			<div class="titinfo">最新博文</div>
  			  <div>
				<ul class="new_blog">
					<c:forEach var="blog" items="${blogList}">
						<li><span class="article_tit"><a href="user/blogdetail.jspx?id=${blog.id}" title="${article.title}" target="_blank" class="sec_span2" style="width:460px;padding-left:0px;">${blog.title}</a><span class="sec_span">${blog.postTime} by ${blog.userName}</span></span><span class="article_read">${blog.reCount}回/${blog.readCount}阅</span></li>
					</c:forEach>
	  			</ul>
	  			</div>
  		</div>
  		<div class="right">
  			<div class="create_wo"><a href="javascript:createWoWo()">创建我的窝窝</a></div>
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
	  				<div class="clear"></div>
	  				<div id="talk_img_con">
	  					<div class="talk_img_tit">
	  						<span class='talk_img_tit_tit'>图片上传</span>
	  						<span class='talk_img_tit_close'><a href="javascript:closeUploader()">关闭</a></span>
	  					</div>
	  					<div class="talk_img_fram" id="talk_img_fram">
	  						<iframe src="imageUpload/talkimgupload.jsp" width="260" height="30" frameborder="0"></iframe>
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
	  			<div class='moretalk'><a href='moretalk.jsp'>看看大家都在吐槽什么&gt;&gt;</a></div>
  			</div>
	  			<div class="titinfo">推荐窝窝</div>
	  			<c:forEach var="group" items="${commendGroupList}">
	  				<div class="recommend_wo">
  					<div class="wo_img"><a href="group/group.jspx?gid=${group.id}" target="_blank"><img src="upload/${group.groupIcon}"></a></div>
  					<div class="wo_info">
  						<div><a href="group/group.jspx?gid=${group.id}" target="_blank">${group.groupName}</a></div>
  						<div>成员${group.members}人</div>
  						<div>文章${group.topicCount}篇</div>
  					</div>
  					<div class="clear"></div>
  				</div>
	  			</c:forEach>
  				<div class="titinfo">最活跃成员</div>
			  	<div>
			  		<c:forEach var="member" items="${activeUserList}">
				  		<div class="user_img">
				  			<div><a href="user/userInfo.jspx?userId=${member.userId}" target="_blank"><img src="upload/${member.userLittleIcon}"></a></div>
				  			<div class="user_img_name"><a href="user/userInfo.jspx?userId=${member.userId}" title="${member.userName}" target="_blank">${member.userName}</a></div>
				  		</div>
			  		</c:forEach>
			  		<div class="clear"></div>
			  	</div>
  			</div>
  			<div class="clear"></div>
  		</div>
  	 <jsp:include page="common/foot.jsp"/>
</body>
</html>