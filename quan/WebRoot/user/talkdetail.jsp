<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/emotion.data.js"></script>
	<script type="text/javascript">
		var GlobalParam = {};
	  	GlobalParam.talkId="${param.talkId}";
	  	GlobalParam.content = "${talk.content}";
 	 </script>
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<link rel="stylesheet" type="text/css" href="../css/moretalk.css">
	<script type="text/javascript" src="../js/user.talkdetail.js"></script>
	<script type="text/javascript" src="../js/talk.js"></script>
	<style type="text/css">
		#sel_left6 a{background:url(../images/bg.gif) 0px -85px;}
		#sel_left6 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <%@ include file="../common/head.jsp" %>
   <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
  	<div class="right">
  		<div class="navPath">
  			<a href="userInfo.jspx?userId=${param.userId}">空间</a>&nbsp;&gt;&gt;&nbsp;吐槽
  		</div>
  		<div>
  			<div class="talkitem">
  				<div class="itemicon">
  					<img src="../upload/${talk.userIcon}" width="37">
  				</div>
  				<div class="itemcon">
  					<span class="item_user"><a href="userInfo.jspx?userId=${talk.userId}">${talk.userName}</a></span>
  					<span class="item_content" id="item_content"></span>
  					<c:if test="${talk.imgurl!=''&&talk.imgurl!=null}">
  						<div class="talkimg">
  							<img src="../upload/${talk.imgurl}">
  						</div>
  					</c:if>
  					<div>
  						<span class="detail_item_time">${talk.createTime}
							<c:if test="${talk.sourceFrom=='A'}">Android</c:if>
  						</span>
  						<span class="detail_item_recount">评论(${talk.reCount})</span>
  					</div>
  				</div>
  				<div class="clear"></div>
  			</div>
  			<div class="u_talk" id="u_talk_textarea_con">
  				<input type="hidden" id="hide_atuserId">
  				<input type="hidden" id="hide_atuserName">
	  			<div class="u_talk_textarea"><textarea id="talkcontent"></textarea></div>
	  			<div class="u_talk_sub">
	  				<div class="talkop">
	  					<a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a>
	  				</div>
	  				<div class="u_talk_subtn">
	  					<a href="javascript:void(0)" id="talkBtn">评&nbsp;&nbsp;论</a>
	  					<img src="../images/load.gif" id="talkload" style="display:none;">
	  				</div>
	  				<div class="clear"></div>
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
  		</div>
  		<div class="re_line">评论：</div>
  		<div id="talklist">
  		
  		</div>
  		
 	</div>
 	<div style="clear:left;"></div>
 	</div>
    <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
