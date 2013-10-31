var admin={};
//树菜单，这里由于菜单比较少，没必要写到数据库中，实际项目中，可以写到数据库中，然后读取的时候返回这样一个json对象就可以了。
admin.menue=[
   {
	   "title":"吐槽管理",
	   "subMenue":[
	       {
	    	   "text":"所有吐槽",
	    	   "children": [{
	    		   "text":"所有吐槽",
	    		   "attributes":{"url":"talk.jsp"}  
	   			},{
	   				"text":"吐槽评论",
	   				"attributes":{"url":"article.jsp"} 
	   			}]
	       }
	   ]
   },
   {
	   "title":"文章管理",
	   "subMenue":[
	       {
	    	   "text":"文章管理",
	    	   "children": [{
	    		   "text":"主题文章",
	   				"attributes":{"url":"article.jsp"} 
	   			},{
	   				"text":"回复",
	   				"attributes":{"url":"http://www.ulewo.com"} 
	   			}]
	       }
	   ]
   },
   {
	   "title":"博客管理",
	   "subMenue":[
	       {
	    	   "text":"博客管理",
	    	   "children": [{
	    		   "text":"所有博文",
	   				"attributes":{"url":"blog.jsp"} 
	   			},{
	   				"text":"所有评论",
	   				"attributes":{"url":"http://www.ulewo.com"} 
	   			}]
	       }
	   ]
   },
   {
	   "title":"窝窝管理",
	   "subMenue":[
	       {
	    	   "text":"窝窝管理",
	    	   "children": [{
	    		   "text":"所有窝窝",
	   				"attributes":{"url":"http://www.ulewo.com"} 
	   			}]
	       }
	   ]
   },
   {
	   "title":"会员管理",
	   "subMenue":[
	       {
	    	   "text":"会员管理",
	    	   "children": [{
	    		   "text":"所有会员",
	   				"attributes":{"url":"user.jsp"} 
	   			}]
	       }
	   ]
   },
   {
	   "title":"屏蔽IP",
	   "subMenue":[
	       {
	    	   "text":"屏蔽IP",
	    	   "children": [{
	    		   "text":"屏蔽IP",
	   				"attributes":{"url":"http://www.ulewo.com"} 
	   			}]
	       }
	   ]
   }
];
$(function(){
	var isOpen = false;
	var content="";
	$.each(admin.menue,function(index,item){
		/**
		 * 构建树的内容,content，然后下面将树放到菜单中
		 */
		//默认展开第一个菜单
		if(index==0){
			isOpen = true;
		}else{
			isOpen = false;
		}
		//先构建一个ul，这个是构建树的基本
		content = $("<ul class='easyui-tree' style='padding-bottom:5px;'></ul>");
		//具体构建树
		content.tree({
			//树的节点数据
			data:item.subMenue,
			//当选中树的时候触发的方法，也就是点击树节点的时候
			onSelect:function(node){
				var centerTab = $('#centerTab');
				//根据title判断标签是否存在
				var isExist	=centerTab.tabs('exists',node.text);
				if(isExist){
					//如果存在，那么选中标签
					centerTab.tabs('select', node.text);  
					//刷新选中标签中的内容，如果不需要，可以去掉该方法的调用
					admin.refreshTab({tabTitle:node.text,url:node.attributes.url});
				}else{
					//如果不存在，那么就添加一个标签
					centerTab.tabs('add',{   
					    title:node.text,   
					    content:'<iframe frameborder="0" class="myframe" src='+node.attributes.url+'></iframe>',   
					    closable:true 
					});
				}
			}
		});
		//构建菜单
		$('#menue').accordion('add', {
			title: item.title,
			content: content,
			selected: isOpen
		});
	});
});

//刷新当前选中的tag
admin.refreshTab = function(cfg){  
    var refresh_tab = cfg.tabTitle?$('#centerTab').tabs('getTab',cfg.tabTitle):$('#centerTab').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
    	var _refresh_ifram = refresh_tab.find('iframe')[0];  
    	var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
    	_refresh_ifram.contentWindow.location.href=refresh_url;  
    }  
}  