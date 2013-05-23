<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>${userVo.userName }的空间--有乐窝</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="../css/user.userinfo.css">
	<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
		<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src="../js/user.userinfo.js"></script>
	<script type="text/javascript" src="../js/util.js"></script>
	<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
	<script src="../dialog/plugins/iframeTools.js"></script>
	<script type="text/javascript" src="../js/talk.js"></script>
	<script type="text/javascript" src="../js/user.talk.js"></script>
	<script type="text/javascript" src="../js/emotion.data.js"></script>
	<style type="text/css">
	#sel_left1 a{background:url(../images/bg.gif) 0px -85px;}
	#sel_left1 a:hover{text-decoration:none;}
	</style>
  </head>
  <body>
  <%@ include file="../common/head.jsp" %>
  <div class="main">
	  <div class="left">
	  	<jsp:include page="left.jsp"></jsp:include>
	  </div>
	  <div class="right">
		  	<c:if test="${user.userId==param.userId}">
	  		<div class="u_talk">
	  			<div class="u_talk_tit">
	  				<span class='u_talk_titname'>今天你吐槽了吗？</span>
	  				<span class='u_talk_wordcount'>最多可以输入250字符</span>
	  				<div class="clear"></div>
	  			</div>
	  			<div class="u_talk_textarea"><textarea id="talkcontent"></textarea></div>
	  			<input type="hidden" id="imgUrl">
	  			<div class="u_talk_sub">
	  				<div class="talkop">
	  					<a href="javascript:showEmotion();" class="icon_sw_face" title="表情"></a>
	  					<a href="javascript:showUploader();" class="icon_sw_img" title="图片上传"></a>
	  				</div>
	  				<div class="u_talk_subtn">
	  					<a href="javascript:void(0)" id="talkBtn">发&nbsp;&nbsp;布</a>
	  					<img src="../images/load.gif" id="talkload" style="display: none;">
	  				</div>
	  				<div class="clear"></div>
	  				<div id="talk_img_con">
	  					<div class="talk_img_tit">
	  						<span class='talk_img_tit_tit'>图片上传</span>
	  						<span class='talk_img_tit_close'><a href="javascript:closeUploader()">关闭</a></span>
	  					</div>
	  					<div class="talk_img_fram" id="talk_img_fram">
	  						<iframe src="../imageUpload/talkimgupload.jsp" width="260" height="30" frameborder="0"></iframe>
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
		  	<div class="baseinfoCon">
			  	<span class="base_tit">加入时间：</span><span class="base_info">${userVo.registerTime}</span><br>
			  	<span class="base_tit">最近登录：</span><span class="base_info">${userVo.previsitTime}</span><br>
			  	<span class="base_tit">性别：</span><span class="base_info">
			  		<c:choose>
                   		<c:when test="${userVo.sex =='M' }">男</c:when>
                   		<c:when test="${userVo.sex =='F' }">女</c:when>
                   		<c:otherwise>未知</c:otherwise>
                  	</c:choose>
			  	</span><br>
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
			  	<span class="base_tit">积分：</span><span class="base_info">${userVo.mark}</span><br>
		  	</div>
		  	<div style="padding:10px;border:1px solid #EEEEEE;">
		  		<div class="topblog_titcon">
		  			<span class="topblog_tit">吐槽</span>
		  			<span class="topblog_link"><a href="moretalk.jsp?userId=${param.userId}">更多吐槽&gt;&gt;</a></span></div>
		  		<div id="talklist"></div>
		  	</div>
		  	<div class="topblog">
		  		<div class="topblog_titcon"><span class="topblog_tit">最新博文</span><span class="topblog_link"><a href="blog.jspx?userId=${userId}" target="_blank">进入博客&gt;&gt;</a></span></div>
		  		<c:forEach var="blog" items="${blogList}">
		  			<div class="blog_link">
		  				<a href="blogdetail.jspx?id=${blog.id}" target="_blank">${blog.title}</a><span>${blog.reCount}/${blog.readCount}</span>
		  			</div>
		  		</c:forEach>
		  		<c:if test="${empty blogList}">
		  			<span>尚无发表博文</span>
		  		</c:if>
		  	</div>
		  	<div class="topblog">
		  		<div class="topblog_titcon"><span class="topblog_tit">最新留言</span><span class="topblog_link"><a href="message.jsp?userId=${userId}" target="_blank">进入留言板&gt;&gt;</a></span></div>
		  		<div class="messagelist"  id="messagelist">
			  	<c:forEach var="message" items="${messageList}">
			  	<div class="main_message">
			  		<div class="re_icon">
				  		<c:if test="${message.reUserId!=null&&message.reUserId!=''}">
							<img src="../upload/${message.reUserIcon }" width="37" style="border:1px solid #9B9B9B">
						</c:if>
		  				<c:if test="${message.reUserId==null||message.reUserId==''}">
		  					<img src="../upload/default.gif" width="37" style="border:1px solid #9B9B9B">
		  				</c:if>
			  		</div>
			  		<div class="re_info">
			  			<div class="note_name_time">
			  				<span class="message_name">
				  				<c:if test="${message.reUserId!=null&&message.reUserId!=''}">
									<a href="userInfo.jspx?userId=${message.reUserId}">${message.reUserName }</a>
								</c:if>
				  				<c:if test="${message.reUserId==null||message.reUserId==''}">
				  					${message.reUserName }
				  				</c:if>
			  				</span>
			  				<c:if test="${message.atUserId!=''}">
			  					<span style="color:#008000">回复</span>
			  					<span class="message_name">
									<a href="userInfo.jspx?userId=${message.atUserId}">${message.atUserName }</a>
			  					</span>
			  				</c:if>
			  				<span class="note_time nofirst">发表于：${message.postTime}</span>
			  			</div>
			  			<div class="re_content">${message.message }</div>
			  		</div>
			  		<div class="clear"></div>
			  	</div>	
			  	</c:forEach>
		  	</div>
		  		<c:if test="${empty messageList}">
		  			<span>尚无任何留言</span>
		  		</c:if>
		  	</div>
		</div>
	<div style="clear:left;"></div>
  </div>
  <script type="text/javascript">
  	var gloableParam={};
  	gloableParam.userId = "${param.userId}";
  </script>
   <jsp:include page="../common/foot.jsp"/>
  </body>
</html>
