Ext.BLANK_IMAGE_URL = '../ext/resources/images/default/s.gif'
Ext.onReady(function(){ 
				var viewport = new Ext.Viewport({
					layout: 'border',
					items:[
						{region:'north',height:40,html:'<h1>head</hl>'},
						{region:'west',
						 width:200,
						 html:'<div id="tree-div"></div>',rootVisible : false,
						 split:true,title:'系统菜单',
						 collapsible:true},
						 tabs
					]

				});

				//数菜单
				var Tree = Ext.tree;
    
				var tree = new Tree.TreePanel({
					el:'tree-div',
					useArrows:true,
					autoScroll:true,
					animate:true,
					enableDD:true,
					rootVisible : false,
					border:false
				});
				
				//树根节点，需要隐藏
				var root = new Ext.tree.TreeNode();
				tree.setRootNode(root);	
				
				tree.render();
				root.expand();	
				
				//第一个菜单节点
				var root1 = new Tree.TreeNode({
					text: '文章管理',
					draggable:false
				});
				var root11 = new Tree.TreeNode({
					text: '文章列表',
					draggable:false,
					listeners:{    
			        'click':function(node, event) {    
			            event.stopEvent();    
			            var n = tabs.getComponent(node.id);    
			            if (!n) { //判断是否已经打开该面板    
			                 n = tabs.add({    
			                    'id':node.id,    
			                    'title':node.text,    
			                     closable:true,  //通过html载入目标页    
			                     html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="getArticles.jspx"></iframe>'   
			                 });    
			             }    
			             tabs.setActiveTab(n);    
			         }    
					}
				});
				root1.appendChild(root11);
				root.appendChild(root1);
				
				//第二个菜单节点
				var root2 = new Tree.TreeNode({
					text: '群组管理',
					draggable:false
				});
				var root21 = new Tree.TreeNode({
					text: '群组列表',
					draggable:false,
					listeners:{    
			        'click':function(node, event) {    
			            event.stopEvent();    
			            var n = tabs.getComponent(node.id);    
			            if (!n) { //判断是否已经打开该面板    
			                 n = tabs.add({    
			                    'id':node.id,    
			                    'title':node.text,    
			                     closable:true,  //通过html载入目标页    
			                     html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="getGroups.jspx"></iframe>'   
			                 });    
			             }    
			             tabs.setActiveTab(n);    
			         }    
					}
				});
				root2.appendChild(root21);
				root.appendChild(root2);
				
				//第三个菜单节点
				var root3 = new Tree.TreeNode({
					text: '用户管理',
					draggable:false
				});
				var root31 = new Tree.TreeNode({
					text: '用户管理',
					draggable:false,
					listeners:{    
			        'click':function(node, event) {    
			            event.stopEvent();    
			            var n = tabs.getComponent(node.id);    
			            if (!n) { //判断是否已经打开该面板    
			                 n = tabs.add({    
			                    'id':node.id,    
			                    'title':node.text,    
			                     closable:true,  //通过html载入目标页    
			                     html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="queryUsers.jspx"></iframe>'   
			                 });    
			             }    
			             tabs.setActiveTab(n);    
			         }    
					}
				});
				root3.appendChild(root31);
				root.appendChild(root3);
				
				//第四个菜单节点
				var root4 = new Tree.TreeNode({
					text: '首页管理',
					draggable:false
				});
				var root41 = new Tree.TreeNode({
					text: '网站首页',
					draggable:false,
					listeners:{    
			        'click':function(node, event) {    
			            event.stopEvent();    
			            var n = tabs.getComponent(node.id);    
			            if (!n) { //判断是否已经打开该面板    
			                 n = tabs.add({    
			                    'id':node.id,    
			                    'title':node.text,    
			                     closable:true,  //通过html载入目标页    
			                     html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="queryPartArticles.jspx?partName=index&partSub=1"></iframe>'   
			                 });    
			             }    
			             tabs.setActiveTab(n);    
			         }    
					}
				});
				root4.appendChild(root41);
				root.appendChild(root4);
			});

			var tabs = new Ext.TabPanel({
					region:'center',
					margins:'3 3 3 0',
					activeTab:0,
					defaults:{autoScroll:true},
					items:[
					{title:'首页',html:'内容'}
					]
				}); 
