//树菜单，这里由于菜单比较少，没必要写到数据库中，实际项目中，可以写到数据库中，然后读取的时候返回这样一个json对象就可以了。
var groupManage = {}
groupManage.menue = [{
	"id": 1,
	"text": "C",
	"children": [{
		"text": "吐槽管理",
		"children": [{
			"id": 10,
			"text": "所有吐槽",
			"attributes": {
				"url": global.realPath+"/admin/admin_blast.jsp"
			}
		},
		{
			"id": 11,
			"text": "吐槽评论",
			"attributes": {
				"url": global.realPath+"/admin/admin_blast_comment.jsp"
			}
		}]
	},
	{
		"text": "文章管理",
		"children": [{
			"id": 20,
			"text": "主题管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_topic.jsp"
			}
		},
		{
			"id": 21,
			"text": "评论管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_topic_comment.jsp"
			}
		},
		{
			"id": 21,
			"text": "每周热点",
			"attributes": {
				"url": global.realPath+"/admin/admin_topic_hot.jsp"
			}
		}]
	},
	{
		"text": "博客管理",
		"children": [{
			"id": 30,
			"text": "博文管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_blog.jsp"
			}
		},{
			"id": 31,
			"text": "评论管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_blog_comment.jsp"
			}
		}]
	},
	{
		"text": "窝窝管理",
		"children": [{
			"id": 40,
			"text": "窝窝管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_group.jsp"
			}
		}]
	},
	{
		"text": "窝窝分类管理",
		"children": [{
			"id": 50,
			"text": "窝窝分类管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_group_category.jsp"
			}
		}]
	},
	{
		"text": "会员管理",
		"children": [{
			"id": 60,
			"text": "会员管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_member.jsp"
			}
		}]
	},
	{
		"text": "爬虫管理",
		"children": [{
			"id": 70,
			"text": "爬虫管理",
			"attributes": {
				"url": global.realPath+"/admin/admin_spider.jsp"
			}
		}]
	}
	]
}];
$(function(){
	var curUrl = window.location.href;
	var content="";
		//先构建一个ul，这个是构建树的基本
		content = $("<ul class='easyui-tree' id='tree' style='padding-bottom:5px;margin-top:-16px;margin-left:-16px'></ul>");
		//具体构建树
		content.tree({
			//树的节点数据
			data:groupManage.menue,
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
						groupManage.refreshTab({tabTitle:node.text,url:node.attributes.url});
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
});

function gotoTreeNode(id){
	var selectNode =$('#tree').tree('find',id);
	$('#tree').tree('select',selectNode.target);
}

//刷新当前选中的tag
groupManage.refreshTab = function(cfg){  
    var refresh_tab = cfg.tabTitle?$('#centerTab').tabs('getTab',cfg.tabTitle):$('#centerTab').tabs('getSelected');  
    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
    	var _refresh_ifram = refresh_tab.find('iframe')[0];  
    	var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
    	_refresh_ifram.contentWindow.location.href=refresh_url;  
    }  
}