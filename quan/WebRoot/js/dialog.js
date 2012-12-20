Dialog = function(args){
	var width;
	var height;
	var title;
	var url;
	var divcon = null;
	var cover = null;
	var div = null;
	this.iframeObj;
	if(args["width"]!=undefined){
		width = args["width"]
	}
	if(args["height"]!=undefined){
		height = args["height"];
	}
	if(args["title"]!=undefined){
		title = args["title"];
	}
	if(args["url"]!=undefined){
		url = args["url"]
	}
	
	var marginTop = 200;
	//获取页面高宽
	var getBodySize = function(){
		var bodySize = new Array(); 
		with(document.documentElement) {
			if(scrollWidth > clientWidth){
				bodySize[0] = scrollWidth;
			}else{
				bodySize[0] = clientWidth;
			}
			if(scrollHeight > clientHeight){
				bodySize[1] = scrollHeight;
			}else{
				bodySize[1] = clientHeight;
			}
		} 
		return bodySize; 
	};

	//遮罩层
	handleCover = function (bool){
		if(bool){
		var bodySize = getBodySize(); 
			var _width = parseInt(bodySize[0]);
			
			var _height= parseInt(bodySize[1]); 
			
			cover = document.createElement("div"); 
			cover.style.cssText = "width:"+_width+"px;height:"+_height+"px;filter:alpha(opacity=10);opacity:0.1;position:absolute;top:0px;left:0px;z-index:2;background:#000000;";
			document.body.appendChild(cover);	
		} else {
			if(cover != null){
				document.body.removeChild(cover);
				cover = null;
			}
		}
	};


	var init = function(){
		
		//弹出层
		handleCover(true);
		
		div = document.createElement("div");
		div.style.cssText = "width:"+width+";height:"+height+";border:1px solid red;z-Index:99999";
		document.body.appendChild(div);
		var _left = parseInt((document.documentElement.clientWidth-parseInt(width))/2);
		if(navigator.userAgent.indexOf("MSIE 6.0")>0){  //如果是IE 6
			div.style.position = "absolute";
			div.style.left = _left+"px";
			div.style.top = (document.documentElement.scrollTop+marginTop)+"px";
			 window.onscroll = function(){   
				div.style.top = (document.documentElement.scrollTop+marginTop)+"px";
			 } 
			
		}else{  //不是IE6
			div.style.position = "fixed";
			div.style.left = _left+"px";
			div.style.top = marginTop+"px";
		}
		//标题栏
		var titDiv = document.createElement("div");
			titDiv.style.cssText = "height:30px;background:pink";
		div.appendChild(titDiv);
		
		//标题
		var titText = document.createElement("div");
			titText.style.cssText = "height:30px;float:left;background:red;width:"+(parseInt(width)-30)+"px";
		//关闭按钮
		var closeCon = document.createElement("div");
			closeCon.style.cssText = "height:30px;float:left;width:30px;background:blue;cursor:pointer;";
			//closeCon.innerHTML = "<a href='javascript:tcloseDialog()'>关闭</a>";
		titDiv.appendChild(titText);
		titDiv.appendChild(closeCon);

		var conDiv = document.createElement("div");
		div.appendChild(conDiv);
		
		if(url!=undefined){
			var  iframe = document.createElement("iframe");
			iframe.width = "100%";
			iframe.style.border = "0px";
			iframe.style.background = "white";
			iframe.style.height = (parseInt(height)-30)+"px";
			iframe.src = url;
			conDiv.appendChild(iframe);
		}
	};
	
	this.show = function(){
		init();
	}
	this.remove = function(){
		document.body.removeChild(div);
		div = null;
		handleCover(false);
	}
}
