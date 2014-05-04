//树菜单，这里由于菜单比较少，没必要写到数据库中，实际项目中，可以写到数据库中，然后读取的时候返回这样一个json对象就可以了。
userManage.menue = [{
	"id": 1,
	"text": "C",
	"children": [{
		"text": "个人信息管理",
		"iconCls": 'icon-user_set',
		"children": [{
			"id": 10,
			"iconCls": 'icon-setting',
			"text": "空间设置",
			"attributes": {
				"url": global.realPath+"/manage/queryTheme.action"
			}
		},{
			"id": 11,
			"iconCls": 'icon-user',
			"text": "修改个人资料",
			"attributes": {
				"url": global.realPath+"/manage/user_inf.action"
			}
		},
		{
			"id": 12,
			"iconCls": 'icon-password',
			"text": "修改登陆密码",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/changepwd.action"
			}
		},
		{
			"iconCls": 'icon-avatar',
			"id": 13,
			"text": "修改头像",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/user_icon.action"
			}
		}]
	},
	{
		"text": "博客管理",
		"iconCls": 'icon-blog-set',
		"children": [{
			"id": 20,
			"iconCls": 'icon-new-blog',
			"text": "发表博文",
			"attributes": {
				"url": global.realPath+"/manage/new_blog.action"
			}
		},
		{
			"id": 21,
			"iconCls": 'icon-blog',
			"text": "博文管理",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/blog.action"
			}
		},
		{
			"id": 22,
			"iconCls": 'icon-comment',
			"text": "评论管理",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/blog_comment.action"
			}
		},{
			"id": 23,
			"text": "分类管理",
			"iconCls": 'icon-category',
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/blog_category.action"
			}
		}]
	},
	{
		"text": "消息管理",
		"iconCls": 'icon-message',
		"children": [{
			"id": 30,
			"iconCls": 'icon-message-open',
			"text": "我的消息",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/notice.action"
			}
		}]
	},
	{
		"text": "收藏管理",
		"iconCls": 'icon-fave',
		"children": [{
			"id": 40,
			"iconCls": 'icon-topic',
			"text": "我收藏的帖子",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/fave_topic.action"
			}
		},
		{
			"id": 41,
			"iconCls": 'icon-blog',
			"text": "我收藏的博文",
			"attributes": {
				"url": global.realPath+"/manage/dispatcher/fave_blog.action"
			}
		}]
	}]
}];
$(function(){
	var curUrl = window.location.href;
	var op = curUrl.substring(curUrl.indexOf("#")+1);
	var content="";
		//先构建一个ul，这个是构建树的基本
		content = $("<ul class='easyui-tree' id='tree' style='padding-bottom:5px;margin-top:-16px;margin-left:-16px'></ul>");
		//具体构建树
		content.tree({
			//树的节点数据
			data:userManage.menue,
			//当选中树的时候触发的方法，也就是点击树节点的时候
			onSelect:function(node){
				var centerTab = $('#centerTab');
				//根据title判断标签是否存在
				var isExist	=centerTab.tabs('exists',node.text);
				if($(this).tree('isLeaf',node.target)){
					if(isExist){
						//如果存在，那么选中标签
						centerTab.tabs('select', node.text);  
						//刷新选中标签中的内容，如果不需要，可以去掉该方法的调用
						userManage.refreshTab({tabTitle:node.text,url:node.attributes.url});
					}else{
						//如果不存在，那么就添加一个标签
						centerTab.tabs('add',{   
						    title:node.text,   
						    content:'<iframe frameborder="0" class="myframe" src='+node.attributes.url+'></iframe>',   
						    closable:true 
						});
					}
				}else{
					$(this).tree('toggle',node.target);
				}
			}
		}).appendTo($('#menue'));
	if(op=="notice"){         //消息
		gotoTreeNode(30);
	}else if(op=="collectionT"){//收藏的主题
		gotoTreeNode(40);
	}else if(op=="collectionB"){//收藏额博文
		gotoTreeNode(41);     
	}else if(op=="newBlog"){ //写博客
		gotoTreeNode(20);
	}else if(op=="userIcon"){//修改头像
		gotoTreeNode(13);
	}else if(op=="userInfo"){//修改基本信息
		gotoTreeNode(11);
	}
});

function gotoTreeNode(id){
	var selectNode =$('#tree').tree('find',id);
	$('#tree').tree('select',selectNode.target);
}

//刷新当前选中的tag
userManage.refreshTab = function(cfg){  
    var refresh_tab = cfg.tabTitle?$('#centerTab').tabs('getTab',cfg.tabTitle):$('#centerTab').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
    	var _refresh_ifram = refresh_tab.find('iframe')[0];  
    	var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
    	_refresh_ifram.contentWindow.location.href=refresh_url;  
    }  
}