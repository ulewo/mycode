﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet"  href="${realPath}/css/foot.css" type="text/css"  />
<div class="footArea">
<div id="footlink">
				<div class="space_host"><a href="http://www.fjjsp.com" target="_blank">网站主机提供商</a></div>
				<ul>
					<li style="margin-left:10px;"><a href="${realPath}">首页</a></li>
					<li style="margin-left:10px;"><a href="${realPath}/group">窝窝</a></li>
					<li style="margin-left:10px;"><a href="${realPath}/blog">博客</a></li>
					<li style="margin-left:10px;"><a href="${realPath}/square">广场</a></li>
				</ul>
			<div id="footright">&copy;2011-2013&nbsp;&nbsp;ulewo.com 有乐窝&nbsp;&nbsp;All rights reserved. <br><br>
						<span style="color:#737573">黑ICP备10007364号-1</span></div>
			<div id="gototop"><a href="javascript:void(0)" onclick="window.scrollTo(0,0);" onfocus="this.blur()" title="回到顶部"></a></div>
			<div id="foot_info">
				<script type="text/javascript">
var _bdhmProtocol = (("https:" == document.location.protocol) ? " https://" : " http://");
document.write(unescape("%3Cscript src='" + _bdhmProtocol + "hm.baidu.com/h.js%3Fe12cbd65c81a8a4ef386c05f7777f8f6' type='text/javascript'%3E%3C/script%3E"));
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
    };
</script>