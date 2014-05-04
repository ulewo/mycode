//树菜单，这里由于菜单比较少，没必要写到数据库中，实际项目中，可以写到数据库中，然后读取的时候返回这样一个json对象就可以了。
groupManage.menue = [{
	"id": 1,
	"text": "C",
	"children": [{
		"text": "基本设置",
		"iconCls": 'icon-settings',
		"children": [{
			"id": 10,
			"iconCls": 'icon-basesettings',
			"text": "基本设置",
			"attributes": {
				"url": global.realPath+"/groupmanage/editGroup.action?gid="+groupManage.gid
			}
		},
		{
			"id": 11,
			"iconCls": 'icon-avatar',
			"text": "修改窝窝头像",
			"attributes": {
				"url": global.realPath+"/groupmanage/editGroupIcon?gid="+groupManage.gid
			}
		},
		{
			"iconCls": 'icon-gonggao',
			"id": 12,
			"text": "公告管理",
			"attributes": {
				"url": global.realPath+"/groupmanage/editGroupNotice.action?gid="+groupManage.gid
			}
		}]
	},
	{
		"text": "文章管理",
		"iconCls": 'icon-blog-set',
		"children": [{
			"id": 20,
			"iconCls": 'icon-topic',
			"text": "主题管理",
			"attributes": {
				"url": global.realPath+"/groupmanage/dispatcher/topic.action?gid="+groupManage.gid
			}
		},
		{
			"id": 21,
			"iconCls": 'icon-comment',
			"text": "评论管理",
			"attributes": {
				"url": global.realPath+"/groupmanage/dispatcher/topic_comment.action?gid="+groupManage.gid
			}
		},
		{
			"id": 22,
			"iconCls": 'icon-category',
			"text": "分类管理",
			"attributes": {
				"url": global.realPath+"/groupmanage/dispatcher/topic_category.action?gid="+groupManage.gid
			}
		}]
	},
	{
		"text": "成员管理",
		"iconCls": 'icon-member_manage',
		"children": [{
			"id": 30,
			"iconCls": 'icon-member',
			"text": "窝窝成员",
			"attributes": {
				"url": global.realPath+"/groupmanage/dispatcher/group_member.action?gid="+groupManage.gid
			}
		},{
			"id": 30,
			"iconCls": 'icon-accept_member',
			"text": "成员审批",
			"attributes": {
				"url": global.realPath+"/groupmanage/dispatcher/group_memberapply.action?gid="+groupManage.gid
			}
		}]
	}]
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