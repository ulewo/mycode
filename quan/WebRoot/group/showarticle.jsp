<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/pager.tld" prefix="p"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${article.title}-有乐窝</title>
<meta name="description" content="${article.title}-有乐窝">
<meta name="keywords" content="${article.keyWord}">
<link rel="stylesheet"  href="../css/article.showarticle.css" type="text/css"  />
<script type="text/javascript" src="../js/jquery.min.js"></script>
<link id="artDialog-skin" href="../dialog/skins/default.css" rel="stylesheet" />
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/article.showArticle.js"></script>
<script src="../dialog/jquery.artDialog.min.js?skin=default"></script>
<script type="text/javascript" src="../js/group.index.js"></script>
<script type="text/javascript" src="../scripts/shCore.js"></script>
<link type="text/css" rel="stylesheet" href="../styles/SyntaxHighlighter.css"/>
<c:if test="${reMessage!=''}">
<script type="text/javascript">
</script>
</c:if>
<c:if test="${reMessage!=''&&reMessage=='checkCodeError'}">
<script type="text/javascript">
	alert("验证码错误");
</script>
</c:if>
	<script type="text/javascript">
		$(document).ready(function () {
            $(".showarticle pre").each(function () {
                var $this = $(this);
                if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
                    var lang = $this.attr("class").split(';')[0].split(':')[1];
                    $this.attr('name', 'code');
                    $this.attr('class', lang);
                }
            });
            $(".recon_info_con pre").each(function () {
                var $this = $(this);
                if ($this.attr("class")!=null&&$this.attr("class").indexOf("brush:") != -1) {
                    var lang = $this.attr("class").split(';')[0].split(':')[1];
                    $this.attr('name', 'code');
                    $this.attr('class', lang);
                }
            });
            dp.SyntaxHighlighter.HighlightAll('code');
            $("#subBtn").bind("mouseover",function(){
            	$(this).css({"background":"#38678F"});
            });
            $("#subBtn").bind("mouseout",function(){
            	$(this).css({"background":"#4682B4"});
            });
        });
	
		function changeImage(){
			$("#showlcodeImage").attr("src","../common/image.jsp?rand ="+Math.random());
		}
	</script>
</head>
<body>
<jsp:include page="../common/head.jsp"/>
<div class="bodycon">
<jsp:include page="head.jsp"/>
	<div class="itemcon">
		<div class="itemcon_item"><jsp:include page="items.jsp"/></div>
		<div class="itemcon_btn">
			<div class="bbtn2"><a href="javascript:addGroup('${user.userId}','${gid}')">+加入圈子</a></div>
			<div class="bbtn1"><a href="javascript:addArticle('${user.userId}','${gid}')">◎发表话题</a></div>
		</div>
	</div>
		<div class="main">
			<div class="title_con">
				<div class="title" id="title_con">${article.title}</div>
				<div class="article_info">${fn:substring(article.postTime,0,16)} 阅读(${article.readNumber})&nbsp;&nbsp;回复(<span id="reCount">${article.reNumber}</span>)</div>
				<!-- <div class="next_article"><a href="">上一篇</a>&nbsp;&nbsp;<a href="">下一篇</a></div> -->
			</div>
			<div class="user_info">
				<div class="user_icon">
					<a href="../user/userInfo.jspx?userId=${article.author.userId }"><img src="../upload/${article.author.userLittleIcon}" border="0" width="60"></a>
				</div>
				<div class="user_info_info">
					<div class="user_name">
						作者:<a href="../user/userInfo.jspx?userId=${article.author.userId }">${article.author.userName}</a>&nbsp;&nbsp;
						文章数:${article.author.articleNumber}&nbsp;&nbsp;来自:${article.author.address}
					</div>
					<div class="user_chare">
						个性:${article.author.characters}
					</div>
				</div>
			</div>
			<div class="showarticle" id="${article.id }">${article.content }</div>
			<div class="about_title">
				你可能也喜欢
			</div>
			<div class="about_con" id="about_con">
				<script>
					showAboutArticle('${article.keyWord}','${gid}');
				</script>
			</div>
			<div class="page_con">
				<div class="bbtn1"><a href="javascript:reArticle('${user.userId}','${article.id}')">☆回复帖子</a></div>
				<div class="bbtn1"><a href="javascript:addArticle('${user.userId}','${gid}')">◎发表话题</a></div>
			</div>
			
			<div class="reCon" id="reCon">
				<c:set var="num" value="${(page-1)*15}"></c:set>
				<c:forEach var="reArticle" items="${reArticleList}">
				<c:set var="num" value="${num+1}"></c:set>
				<div class="recon_con">
					<div class="recon_img">
						<c:if test="${reArticle.authorIcon==''}">
							<img src="../upload/user_default.gif" width='60'>
						</c:if>
						<c:if test="${reArticle.authorIcon!=''}">
							<a href='../user/userInfo.jspx?userId=${reArticle.authorid}'><img src="../upload/${reArticle.authorIcon}" width='50'></a>
						</c:if>
					</div>
					<div class="recon_info">
						<div class="recon_info_re">
							<div class="recon_info_info">
								<span class='info_lou'>${num}楼</span>
								<span class='info_name'>
									<c:if test="${reArticle.authorid!=null&&reArticle.authorid!=''}">
										<a href='../user/userInfo.jspx?userId=${reArticle.authorid}'>${reArticle.authorName }</a>
									</c:if>
									<c:if test="${reArticle.authorid==null||reArticle.authorid==''}">
										${reArticle.authorName }
									</c:if>
								</span>
								<span class='info_time'>发表时间：${fn:substring(reArticle.reTime,0, 16)}</span>
							</div>
							<div class="recon_info_info_op">
								<c:if test="${user!=null}"><span><a href='javascript:void(0)' onclick="quote('${reArticle.id}')">引用</a></span></c:if>
							<c:if test="${showDelete=='Y'}"><span class='re_op_d'>
							<!-- <a href='####'>删除</a> -->
							</span></c:if>
							</div>
						</div>
						<div class="recon_info_con">
							${reArticle.content}
						</div>
					</div>
					<div class="clear"></div>
				</div>
				</c:forEach>
			</div>
			<div style="height:25px;margin-top:10px;">
				<div  class="pagination">
					<p:pager url="post.jspx?id=${id}" page="${page}" pageTotal = "${pageTotal }"></p:pager> 
	 			</div>
	 			<div style="clear:both;"></div>
 			</div>
			<div class="fast_re_tit">快速回复:（无需登录）</div>	
			<form action="fastReArticle.jspx" method="post">
				<div class="fast_re">
					<div class="faset_re_img">
						<c:if test="${user!=null}">
							<img src="../upload/${user.userLittleIcon}" width="50">
						</c:if>
						<c:if test="${user==null}">
							<img src="../upload/default.gif" width="50">
						</c:if>
					</div>
					<div class="fast_re_reform">
						<c:if test="${user==null}">
							<div class="reform_input">
								<input type="text" id="reUserName">
							</div>
						</c:if>
						<div style="margin-top:10px;">
							<textarea class="reform_erea" id="reContent"></textarea>
						</div>
						<c:if test="${user==null}">
							<div class="checkcode">
								<div class="check_con">
									<input type="text" class="long_input" name="checkCode" id="checkCode"/>
								</div>
								<div class="check_img">
									<a href="JavaScript:refreshcode();" onfocus="this.blur();"><img id="checkCodeImage" src="../common/image.jsp" border="0"/></a>
								</div>
								<div class="changecode">
									<a href="javascript:refreshcode()">换一张</a>
								</div>
	  						</div>
  						</c:if>
						<div class="reform_submit">
							<div class="submit_btn sbtn" id="subBtn_con">
								<input type="button" class="button" onclick="subReForm('${user.userId}','${id}','${gid }')" value="回复" id="subBtn">
							</div>
							<div style="margin-left:20px;padding-top:8px;float:left;">最多输入500字符</div>
						</div>
							
					</div>
					<div class="clear"></div>
				</div>
			</form>
	</div>
</div>
<jsp:include page="../common/foot.jsp"/>
</body>
</html>