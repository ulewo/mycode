﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${userVo.userName}的空间-有乐窝</title>
<meta name="description" content="${userVo.userName}的空间-有乐窝">
<meta name="keywords" content="${userVo.userName}的空间-有乐窝">
<script type="text/javascript">
var dynamic = {};
dynamic.userId = "${userVo.userId}";
</script>
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.usercenter.css?version=2.5">
<link rel="stylesheet" type="text/css" href="${realPath}/css/talk.css?version=2.5">
<link rel="stylesheet" type="text/css" href="${realPath}/css/user.dynamic.css?version=2.5">
</head>
<body>
	<%@ include file="../common/head.jsp" %>
	<div <c:if test="${userVo.centerTheme==1}">class="main2"</c:if> <c:if test="${userVo.centerTheme!=1}">class="main"</c:if>>
	  <div class="left" id="left">
	  	<%@ include file="left.jsp" %>
		  	<div class="left_item">
		  		<div class="left_item_tit">关注</div>
		  		<div class="left_img_p">
		  			<div>
			  			<c:forEach var="friend" items="${focusList}">
			  				<div class="left_img_item"><a href="${realPath}/user/${friend.friendUserId}" title="${friend.friendName}"><img src="${realPath}/upload/${friend.friendIcon}" width="40"></a></div>
			  			</c:forEach>
			  			<c:if test="${empty focusList}">
			  				<div class="left_noinfo">尚未关注其他人</div>
			  			</c:if>	
			  			<div class="clear"></div>
		  			
		  			<!-- 
		  			<div class="left_img_p_more"><a href="">显示所有关注(${userVo.focusCount})</a></div>
		  			 -->
		  		</div>
		  	</div>
		  	<div class="left_item">
		  		<div class="left_item_tit">粉丝</div>
		  		<div class="left_img_p">
		  			<div>
			  			<c:forEach var="friend" items="${fansList}">
			  				<div class="left_img_item"><a href="${realPath}/user/${friend.userId}" title="${friend.friendName}"><img src="${realPath}/upload/${friend.friendIcon}" width="40"></a></div>
			  			</c:forEach>
			  			<c:if test="${empty fansList}">
			  				<div class="left_noinfo">尚无粉丝，精彩分享才能吸引关注</div>
			  			</c:if>
			  			<div class="clear"></div>
		  			</div>
		  			<!-- 
		  			<div class="left_img_p_more"><a href="">显示所有粉丝(${userVo.fansCount})</a></div>
		  			 -->
		  		</div>
		  	</div>
		  	<div class="left_item">
		  		<div class="left_item_tit">Ta创建的窝窝</div>
		  		<div class="left_img_p">
		  			<div>
		  			<c:forEach var="group" items="${createdGroups}">
		  				<div class="left_img_item"><a href="${realPath}/group/${group.gid}" title="${group.groupName}"><img src="${realPath}/upload/${group.groupIcon}" width="40"></a></div>
		  			</c:forEach>
		  			<c:if test="${empty createdGroups}">
			  			<div class="left_noinfo">没有创建任何窝窝</div>
			  		</c:if>
		  			<div class="clear"></div>
		  			</div>
		  		</div>
		  	</div>
		  	<div class="left_item">
		  		<div class="left_item_tit">Ta加入的窝窝</div>
		  		<div class="left_img_p">
		  			<div>
		  			<c:forEach var="group" items="${joinedGroups}">
		  				<div class="left_img_item"><a href="${realPath}/group/${group.gid}" title="${group.groupName}"><img src="${realPath}/upload/${group.groupIcon}" width="40"></a></div>
		  			</c:forEach>
		  			<c:if test="${empty joinedGroups}">
			  			<div class="left_noinfo">没有加入任何窝窝</div>
			  		</c:if>
		  			<div class="clear"></div>
		  			</div>
		  		</div>
		  	</div>
		  </div>
	  </div>
	  <div class="right" id="right">
	  		<div style="margin-bottom:15px;">
	  			<script type="text/javascript">
				/*个人中心，创建于2013-7-10*/
				var cpro_id = "u1317830";
				</script>
				<script src="http://cpro.baidustatic.com/cpro/ui/c.js" type="text/javascript"></script>
	  		</div>
		  	<c:if test="${userVo.userId==user.userId}">
	  		<div class="u_talk">
	  			<div class="u_talk_tit" style="height:23px;">
	  				<span class='u_talk_titname' style="float:none;">今天你吐槽了吗？</span>
	  				<span class='u_talk_wordcount' style="float:none;font-size:12px;">（最多可以输入250字符）</span>
	  			</div>
	  			<div class="u_talk_textarea" style="padding-right:1px;border:2px solid #64C5E8;"><textarea id="talkcontent" style="width:100%;border:0px;"></textarea></div>
	  			<input type="hidden" id="imgUrl">
	  			<div class="u_talk_sub" >
	  				<div style="height:30px">
	  					<div class="talkop">
		  					<a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a>
		  					<a href="javascript:showUploader();" class="icon_sw_img" title="图片上传"></a>
	  					</div>
		  				<div class="u_talk_subtn" style="height:30px;">
		  					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
		  					<a href="javascript:void(0)" id="talkBtn" class="btn">发&nbsp;&nbsp;布</a>
		  				</div>
	  				</div>
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
					    	<div class="pm_emotions_bd">
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
		  	</c:if>
		  	<c:if test="${userVo.userId!=user.userId}">
			  	<div class="baseinfoCon topblog">
				  	<span class="base_tit">加入时间：</span><span class="base_info">${userVo.registerTime}</span><br>
				  	<span class="base_tit">最近登录：</span><span class="base_info">${userVo.previsitTime}</span><br>
				  	<span class="base_tit">职业：</span><span class="base_info">
				  		<c:choose>
	                   		<c:when test="${!empty userVo.work}">${userVo.work}</c:when>
	                   		<c:otherwise>未知</c:otherwise>
	                  	</c:choose>	
				  	</span><br>
				  	<span class="base_tit">地址：</span><span class="base_info">
				  		<c:choose>
	                   		<c:when test="${!empty userVo.address}">${userVo.address}</c:when>
	                   		<c:otherwise>未知</c:otherwise>
	                  	</c:choose>
				  		</span><br>
			  	</div>
		  	</c:if>
		  	<div class="topblog">
		  		<div class="topblog_titcon" style="height:30px;">
		  			<span class="topblog_tit">最新博文</span>
		  			<span class="topblog_link"><a href="${realPath}/user/${userVo.userId}/blog">进入博客&gt;&gt;</a></span>
		  		</div>
		  		<div>
		  			<c:forEach var="blog" items="${bloglist}">
			  			<div class="blog_link">
			  				<a href="${realPath}/user/${userVo.userId}/blog/${blog.blogId}">${blog.title}</a><span>${blog.commentCount}/${blog.readCount}</span>
			  			</div>
		  			</c:forEach>
		  			<c:if test="${empty bloglist}">
		  				<div class="left_noinfo">没有发现博文</div>
		  			</c:if>
		  		</div>
		  	</div>
		  	<div class="trends">
		  		<ul class="trends_tag">
		  			<li class="tag_select"><a href="javascript:void(0)">所有动态</a></li>
					<li><a href="javascript:void(0)">吐&nbsp;&nbsp;槽</a></li>
					<li><a href="javascript:void(0)">帖&nbsp;&nbsp;子</a></li>
				</ul>
				<div id="dynamicList"></div>
		  		<a href="javascript:void(0)" id="loadmorebtn" style="display:none;">加载更多</a>
				<div id="loading" ><img src="${realPath}/images/load.gif" ></div>
				<div id="pager" class="pagination" style="margin-top:10px;"></div>
		  	</div>
		</div>
  </div>
  <script type="text/javascript" src="${realPath}/js/talk.js?version=2.6"></script>
  <script type="text/javascript" src="${realPath}/js/user.home.js?version=2.5"></script>
  <%@ include file="../common/toolbar.jsp" %> 
</body>
</html>