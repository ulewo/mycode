Ext.onReady(function(){ 
	Ext.util.CSS.swapStyleSheet("theme","../ext/resources/css/ext-all-css04.css");
	Ext.QuickTips.init(); 
		var cm = new Ext.grid.ColumnModel([ 
		new Ext.grid.RowNumberer(), //行号列 
		{header:'ID',dataIndex:'id',width:50}, 
		{header:'标题',dataIndex:'title',width:500,renderer:showUrl}, 
		{header:'创建时间',dataIndex:'posttime',width:150}, //增加性别，自定义renderer，即显示的样式，可以加html代码，来显示图片等。
		{header:'作者',dataIndex:'authorName',width:150}, 
		{header:'阅读',dataIndex:'readNumber',width:100}, 
		{header:'回复',dataIndex:'reNumber',width:100}
		]); 
		
		
		var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({url:'getList.jspx'}),//调用的动作 
		reader: new Ext.data.JsonReader({root: 'articleList',successProperty :'success'}, 
		[ 
		{name: 'id',mapping:'id',type:'int'}, 
		{name: 'title',mapping:'title',type:'string'},  //列的映射
		{name: 'posttime',mapping:'postTime',type:'string'},
		{name: 'authorName',mapping:'authorName',type:'string'},
		{name: 'readNumber',mapping:'readNumber',type:'string'},
		{name: 'reNumber',mapping:'reNumber',type:'string'}
		])
		}); 
		
		var grid = new Ext.grid.GridPanel({ 
		el: 'grid', 
		ds: ds, 
		cm: cm, 
		height:200,
		bbar: new Ext.PagingToolbar({
            pageSize: 2,
            store: ds,
            displayInfo: true,
            displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
            emptyMsg: "没有记录"
           
			})
		}); 
		//el:指定html元素用于显示grid 
		grid.render();//渲染表格 
		ds.load({params:{start:0, limit:10}}); //加载数据 
	});
	
	function showUrl(value,record){
		var str = "<a href='../group/showArticle.jspx?id="+record.id+"'>"+value+"</a>";
     return str;

/*
var fn = function(value, cellmeta, record, rowIndex, columnIndex, store){
15    var str = "<input type='button' value='查看详细信息' onclick='alert(\""+
16        "这个单元格的值是: " + value + "\\n" +
17        "这个单元格的配置是: {cellId" + cellmeta.cellId + ",Id:" + cellmeta.id + ",css:" + cellmeta.css + "}\\n" +
18        "这个单元格对应行的record是:" + record + ", 一行的数据都在里面\\n" + 
19        "这是第" + rowIndex + "行\\n" +
20        "这是第" + columnIndex + "列\\n" +
21        "这个表格对应的Ext.data.Store在这里:" + store + ", 随便用吧。" +
22        "\")'>";
23    return str;
24}

*/
	}