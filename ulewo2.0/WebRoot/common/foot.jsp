<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"  href="${realPath}css/foot.css" type="text/css"  />
<div class="footArea">
			<div id="footlink">
				<ul>
					<li style="margin-left:10px;"><a href="${realPath}index.jspx">首页</a></li>
					<li style="margin-left:10px;"><a href="${realPath}groups.jspx">窝窝</a></li>
					<li style="margin-left:10px;"><a href="${realPath}square.jspx">广场</a></li>
				</ul>
			<div id="footright">&copy; 2011-2012&nbsp;&nbsp;ulewo.com 有乐窝 All rights reserved.</div>
			<div id="gototop"><a href="javascript:void(0)" onclick="window.scrollTo(0,0);" onfocus="this.blur()" title="回到顶部"><img border="0" src="${realPath}images/totop.png"></a></div>
			
				<script type="text/javascript" id="bdshare_js" data="type=slide&amp;img=0&amp;pos=right&amp;uid=0" ></script>
				<script type="text/javascript" id="bdshell_js"></script>
				<script type="text/javascript">
				document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
				</script>
			
			<div style="margin-top:15px;">
				<script type="text/javascript">
					var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
					document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3F1535c97f8b589c71b7a6497eda7dac5f' type='text/javascript'%3E%3C/script%3E"));
				</script>
			</div>
			
	</div>
</div>
<script>
	$(window).bind("scroll",function(){  
	     var scollPos =  ScollPostion();
	     if(scollPos.top>200){
	    	 $("#gototop").show();
	     }else{
	    	 $("#gototop").hide();
	     }
	     
	});
	
	function  ScollPostion() {//滚动条位置
        var t, l, w, h;
        if (document.documentElement && document.documentElement.scrollTop) {
            t = document.documentElement.scrollTop;
            l = document.documentElement.scrollLeft;
            w = document.documentElement.scrollWidth;
            h = document.documentElement.scrollHeight;
        } else if (document.body) {
            t = document.body.scrollTop;
            l = document.body.scrollLeft;
            w = document.body.scrollWidth;
            h = document.body.scrollHeight;
        }
        return { top: t, left: l, width: w, height: h };
    }
</script>