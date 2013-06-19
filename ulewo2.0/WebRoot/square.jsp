<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js" lang="en"> <!--<![endif]-->
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>小窝窝 大世界 小智慧 大财富 --有乐窝</title>
<meta name="description" content="有乐窝 大型服务社区，让你的生活更精彩 学习经验交流，网络文摘分享 ，游戏娱乐 ......">
<meta name="keywords" content="小窝窝 大世界 小智慧 大财富 — 有乐窝">
<link rel="stylesheet" href="${realPath}/css/imageload.reset.css">
<link rel="stylesheet" href="${realPath}/css/imageload.style.css">
<link rel="stylesheet" href="${realPath}/css/public.css">
<script src="${realPath}/js/jquery.wookmark.js"></script>
<style type="text/css">
.selected4{background:#1C91BE;}
</style>
</head>
<body>
	 <%@ include file="common/head.jsp" %>
  	<div class="main"style="width:100%">
  		<ul id="tiles">
        	<!-- These is where we place content loaded from the Wookmark API -->
      	</ul>
	    <div id="loader">
	        <div id="loaderCircle"></div>
	    </div>
  	</div>
    <%@ include file="common/foot.jsp" %>
  <script type="text/javascript">
    (function ($) {
      var handler = null,
          page = 1,
          isLoading = false,
          apiURL = 'http://www.wookmark.com/api/json/popular';

      // Prepare layout options.
      var options = {
        autoResize: true, // This will auto-update the layout when the browser window is resized.
        container: $('#tiles'), // Optional, used for some extra CSS styling
        offset: 2, // Optional, the distance between grid items
        itemWidth: 210 // Optional, the width of a grid item
      };

      /**
       * When scrolled all the way to the bottom, add more tiles.
       */
      function onScroll(event) {
        // Only check when we're not still waiting for data.
        if(!isLoading) {
          // Check if we're within 100 pixels of the bottom edge of the broser window.
          var closeToBottom = ($(window).scrollTop() + $(window).height() > $(document).height() - 100);
          if(closeToBottom) {
            loadData();
          }
        }
      };

      /**
       * Refreshes the layout.
       */
      function applyLayout() {
        // Create a new layout handler.
        handler = $('#tiles li');
        handler.wookmark(options);
      };

      /**
       * Loads data from the API.
       */
      function loadData() {
        isLoading = true;
        $('#loaderCircle').show();

        $.ajax({
          url: apiURL,
          dataType: 'jsonp',
          data: {page: page}, // Page parameter to make sure we load new data
          success: onLoadData
        });
      };

      /**
       * Receives data from the API, creates HTML for images and updates the layout
       */
      function onLoadData(data) {
        isLoading = false;
        $('#loaderCircle').hide();

        // Increment page index for future calls.
        page++;

        // Create HTML for the images.
        var html = '';
        var i=0, length=data.length, image;
        for(; i<length; i++) {
          image = data[i];
          html += '<li>';

          // Image tag (preview in Wookmark are 200px wide, so we calculate the height based on that).
          html += '<img src="'+image.preview+'" style="maxWidth=200px" height="'+Math.round(image.height/image.width*200)+'">';

          // Image title.
          html += '<p>'+image.title+'</p>';

          html += '</li>';
        }

        // Add image HTML to the page.
        $('#tiles').append(html);

        // Apply layout.
        applyLayout();
      };

      // Capture scroll event.
      $(document).bind('scroll', onScroll);

      // Load first data from the API.
      loadData();
    })(jQuery);
</script>
</body>
</html>