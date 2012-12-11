/**
 * 联系人容器
 * 
 * @param wrapId
 *            需要用作容器的DIV ID
 * @author luojian
 */
function ContactWrap(wrapId) {
	this.wrap = null;
	this.children = [];
	this.init(wrapId);
}
ContactWrap.prototype = {
	init : function(wrapId) {
		var div = $("#" + wrapId);
		div.html("");
		div.addClass("linkInfoList");
		this.wrap = $("<ul />");
		div.append(this.wrap);
	},
	/**
	 * 添加联系人数组，必须是经过JSON解析的数组 示例: var wrap=new ContactWrap("contactWrap");
	 * $(document).ready(function(){ $.get("<%=basePath%>prm/loadContacts.do",function(data){
	 * wrap.addContacts(jQuery.parseJSON(data).contacts); }); });
	 */
	addContacts : function(contacts) {
		for ( var i = 0; i < contacts.length; i++) {
			var contact = contacts[i];
			this.addContact(contact);
		}
	},
	addContact : function(contact) {
		var c = new PrmContact(contact, this);
		this.wrap.append(c.asHtml());
		this.children.push(c);
	},
	clear : function() {
		this.wrap.remove();
	},
	getSelectedContacts : function() {
		var r = new Array();
		for ( var i = 0; i < this.children.length; i++) {
			var c = this.children[i];
			if (c.isChecked()) {
				r.push(c);
			}
		}
		return r;
	},
	getSelectedContactIds : function() {
		var r = new Array();
		for ( var i = 0; i < this.children.length; i++) {
			var c = this.children[i];
			if (c.isChecked()) {
				r.push(c.cid);
			}
		}
		return r;
	}
}
/**
 * PRM联系人组件
 * 
 * @author luojian
 */
function PrmContact(contact, container) {
	this.data = contact;
	this.wrap;// 最外层Li
	this.checkbox;// 复选框
	this.name;// 姓名
	this.photo;// 头像
	this.qq;// QQ
	this.wx;// 微信
	this.msn;// MSN
	this.operArea;// 操作区（修改、删除）
	this.modify;// 修改
	this.delt;// 删除

	this.container = container;
	// 以上对象，在init()方法中已初始化，并且都是jQuery对象，可直接调用jQuery方法
	this.cid = contact.uid;// 联系人的id
	this.groups;// 联系人的分组信息
	this.init();
}
PrmContact.prototype = {
	init : function() {
		this.wrap = $("<li/>");
		// 复选框
		this.checkbox = $("<input>").attr("type", "checkbox").bind("click",
				checkBoxAreaClick);
		this.checkboxArea = $("<div>").addClass("titleSelect selectPadding")
				.append(this.checkbox)
		this.wrap.append(this.checkboxArea);
		// 头像,如果有头像，则请求头像，如果没有，则直接使用默认头像
		var pPath = this.data.imageName ? basePath + "prm/avatar.do?cid="
				+ this.data.uid : basePath + "images/default_avator_s.gif";
		this.imgLink = $("<img />").attr("src", pPath);
		this.photo = $("<div/>").addClass("listPhoto").append(this.imgLink);
		this.wrap.append(this.photo);
		// 个人信息栏
		var iWrap = $("<div/>").addClass("user_list");
		// 姓名及其他信息 ---start----
		var nameWrap = $("<div/>").addClass("friend_head");
		// 姓名
		var nameStr = this.data.fullName;
		this.name = $("<a href='showdetail.jsp?uid=" + this.data.uid + "'/>")
				.addClass("emlink").text(nameStr);
		nameWrap.append(this.name);
		// 其它信息
		var otherWrap = $("<div/>").addClass("info_4th");
		otherWrap.append($("<span/>").addClass("c_tx2 infoFloat").text("其他信息"));
		this.qq = $("<a href='#'/>").addClass("notOnlineQQ");
		this.wx = $("<a href='#'/>").addClass("notOnlineWX");
		this.msn = $("<a href='#'/>").addClass("notOnlineMSN");
		otherWrap.append(this.qq);
		otherWrap.append(this.wx);
		otherWrap.append(this.msn);
		nameWrap.append(otherWrap);
		nameWrap.append('<div style="clear:both;"></div>');
		iWrap.append(nameWrap)
		// 姓名及其他信息 ---end----
		// 公司，职位，生日；手机、邮箱、性别----start----
		var infoWrap = $("<div/>").addClass("info_content");
		// 第一行
		var row1 = $("<div/>").append(
				this.newLabel("info_1th", "公司:", this.data.corpName)).append(
				this.newLabel("info_2th", "职位:", this.data.jobTitle)).append(
				this.newLabel("info_3th", "生日:", this.data.birthday)).append(
				'<div style="clear:both;"></div>');
		infoWrap.append(row1);
		// 第二行
		var sexStr = "";
		if (this.data.sex == "M") {
			sexStr = "男";
		} else if (this.data.sex == "F") {
			sexStr = "女";
		}
		var row2 = $("<div/>").append(
				this.newLabel("info_1th", "手机:", this.data.mobilePhone))
				.append(this.newLabel("info_2th", "邮箱:", this.data.email))
				.append(this.newLabel("info_3th", "性别:", sexStr)).append(
						'<div style="clear:both;"></div>');
		infoWrap.append(row2);
		iWrap.append(infoWrap);
		// 公司，职位，生日；手机、邮箱、性别----end----
		// 操作：编辑、删除----start----
		this.operArea = $("<div/>").addClass("operatAre");
		this.modify = $("<a href='add.jsp?uid=" + this.data.uid + "'/>")
				.addClass("editLinkman");
		this.delt = $("<a href='#'/>").addClass("delLinkman").bind("click", {
			uid : this.data.uid,
			warp : this.wrap,
			obj : this
		}, this.singleDel);
		this.operArea.append(this.modify);
		this.operArea.append(this.delt);
		this.operArea.append('<div style="clear:both;"></div>');
		iWrap.append(this.operArea);
		// 操作：编辑、删除----end----
		this.wrap.append(iWrap);
		this.wrap.append('<div style="clear:both;"></div>');
		this.bindHandler(this.data.uid);
		// 异步加载其他信息
		this.loadOtherInfo();
	},
	/**
	 * 为各对象绑定事件，各关键的对象组件均已在构造器中声明， 需要增加事件，可直接在此方法内添加
	 * 
	 * @author luojian
	 */
	bindHandler : function(uid) {
		// 最外层li加上鼠标滑过事件
		var _this = this;
		this.wrap.hover(function() {
			$(this).addClass("selectLi");
			_this.operArea.show();
		}, function() {
			$(this).removeClass("selectLi");
			_this.operArea.hide();
		});
	},
	singleDel : function(event) {// 单个删除
		var uid = event.data.uid;
		var warp = event.data.warp;
		var obj = event.data.obj;
		$.ajax({
			async : true,
			cache : false,
			type : 'POST',
			dataType : "json",
			data : {
				uid : uid + ","
			},
			url : basePath + "prm/delContact.do",// 请求的action路径
			success : function(data) {
				deleteloadContact();
			}
		});
	},
	/**
	 * 是否已选中
	 */
	isChecked : function() {
		return this.checkbox.is(':checked');
	},
	/**
	 * 设置选中状态,true选中，false不选中
	 */
	setChecked : function(checked) {
		this.checkbox.attr("checked", checked);
	},
	newLabel : function(divClass, key, value) {
		var div = $("<div>").addClass(divClass);
		div.append($("<span>").addClass("c_tx2").text(key));
		div.append($("<span>").text(value));
		var div = $("<div/>").addClass(divClass);
		div.append($("<span/>").addClass("c_tx2").text(key));
		div.append($("<span/>").text(value))
		return div;
	},
	asHtml : function() {
		return this.wrap;
	},
	/*
	 * 异步请求扩展信息，展示图标
	 */
	loadOtherInfo : function() {
		var obj = this;
		$.ajax({
			async : true,
			cache : false,
			type : 'GET',
			dataType : "json",
			url : basePath + "prm/contactDetailInfo.do?uid=" + this.data.uid,// 请求的action路径
			success : function(data) {
				setOtherInfo(data, obj);
				obj.groups = data.groups;
			}
		});
	},
	getUid : function() {
		return this.data.uid;
	},
	removeFromParent : function() {
		this.wrap.remove();
		this.container.children.remove(this);
	},
	/**
	 * 增加分组，
	 * 
	 * @param groups
	 *            JSON数组
	 */
	addGroups : function(groups) {
		for ( var i = 0; i < groups.length; i++) {
			var isExisted = false;
			var g = groups[i];
			for ( var j = 0; j < this.groups.length; j++) {
				if (g.id == this.groups[j].id) {
					isExisted = true;
					break;
				}
			}
			if (!isExisted) {
				this.groups.push(g);
			}
		}
	},
	/**
	 * 删除分组
	 * 
	 * @param groupIds
	 *            组ID的数组
	 */
	delGroups : function(groupIds) {
		for ( var i = 0; i < this.groups.length; i++) {
			var g = this.groups[i];
			for ( var j = 0; j < groupIds.length; j++) {
				if (g.id == groupIds[i]) {
					this.groups.remove(g);
					i--;
					break;
				}
			}
		}
	}
}
/**
 * 异步请求扩展信息，展示图标
 * 
 * @author luohl
 */
function setOtherInfo(data, obj) {
	var propertyList = data.propertyList;
	var qq = "";
	var msn = "";
	var wx = "";
	for ( var i = 0; i < propertyList.length; i++) {
		var property = propertyList[i];
		var categoryId = property.categoryId;
		var attributeId = property.attributeId;
		var value = property.value;
		if (categoryId == "ext_communication") {
			if (attributeId == "ext_sys_communication_qq") {
				if (qq == "") {
					qq = value;
				} else {
					qq = qq + ";" + value;
				}
				continue;
			}
			if (attributeId == "ext_sys_communication_msn") {
				if (msn == "") {
					msn = value;
				} else {
					msn = msn + ";" + value;
				}
				continue;
			}
			if (attributeId == "ext_sys_communication_gtalk") {
				if (wx == "") {
					wx = value;
				} else {
					wx = wx + ";" + value;
				}
				continue;
			}
		}
	}
	if (qq != "") {
		obj.qq.removeClass("notOnlineQQ");
		obj.qq.addClass("onlineQQ");
		obj.qq.attr("title", qq);
	}
	if (wx != "") {
		obj.wx.removeClass("notOnlineWX");
		obj.wx.addClass("onlineWX");
		obj.wx.attr("title", wx);
	}
	if (msn != "") {
		obj.msn.removeClass("notOnlineMSN");
		obj.msn.addClass("onlineMSN");
		obj.msn.attr("title", msn);
	}
}
/**
 * 全选，取消全选
 * 
 * @author luohl
 */
function checkBoxAreaClick() {
	var chekecCount = 0;
	var checkbox = $(this);
	if (checkbox.is(':checked')) {
		checkbox.attr("checked", true);
	} else {
		checkbox.attr("checked", false);
	}
	var children = PrmGlobal.contactWrap.children;
	for ( var i = 0; i < children.length; i++) {
		if (children[i].checkbox.is(':checked')) {
			chekecCount++;
		}
	}
	if (children.length == chekecCount) {
		$("#selectAll").attr("checked", true);
	} else {
		$("#selectAll").attr("checked", false);
	}
}

// 获取高亮显示的字母
function getHighLightLetters() {
	var letters = [ "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
			"M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
			"Z", "#" ];
	$("<a href='javascript:searchByLetter(\"\")'>全部</a>").appendTo(
			"#letterList");
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : basePath + "prm/getHighLightLetters.do",// 请求的action路径
		success : function(data) {
			var highlighletter = data.highlighletter;
			var isHave = false;
			for ( var i = 0; i < letters.length; i++) {
				isHave = false;
				for ( var j = 0; j < highlighletter.length; j++) {
					if (highlighletter[j] == letters[i]) {
						isHave = true;
						break;
					}
				}
				if (isHave) {
					$(
							"<a href='javascript:searchByLetter(\""
									+ letters[i] + "\")'>" + letters[i]
									+ "</a>").appendTo("#letterList");
				} else {
					$("<span>" + letters[i] + "</span>")
							.appendTo("#letterList");
				}
			}
		}
	});
}

function searchByLetter(letter) {
	PrmGlobal.orderType = "";
	PrmGlobal.searchType = "letter";
	PrmGlobal.searchValue = letter;
	PrmGlobal.pageIndex = 1;
	loadContact(PrmGlobal.searchValue, PrmGlobal.searchType,
			PrmGlobal.orderType, PrmGlobal.pageIndex, PrmGlobal.groupId);
}

function deleteloadContact() {
	loadContact(PrmGlobal.searchValue, PrmGlobal.searchType,
			PrmGlobal.orderType, PrmGlobal.pageIndex, PrmGlobal.groupId);
}

/** 排序* */
function oderClick(obj) {
	$("#order_panle").hide();
	PrmGlobal.orderType = obj.attr("type");
	var styleimg = "a-z.gif";
	if (PrmGlobal.orderType == "asc") { // a-z
		styleimg = "a-z.gif";
	} else if (PrmGlobal.orderType == "desc") { // z-a
		styleimg = "z-a.gif";
	} else if (PrmGlobal.orderType == "new2old") { // 从新到旧
		styleimg = "new2old.gif";
	} else if (PrmGlobal.orderType == "old2new") {// 从旧到新
		styleimg = "old2new.gif";
	}
	$("#linkLetterUp").css({
		"backgroundImage" : "url(../images/" + styleimg + ")"
	});
	loadContact(PrmGlobal.searchValue, PrmGlobal.searchType,
			PrmGlobal.orderType, 1, PrmGlobal.groupId);
}
function loadContact(searchValue, searchType, type, pageIndex, groupId) {
	PrmGlobal.contactWrap.wrap.html("");
	$(
			"<div class='loading'><img src='../images/loading.gif'/>&nbsp;&nbsp;页面加载中....</div>")
			.appendTo($("#contactWrap"));
	$.ajax({
		async : true,
		cache : false,
		type : 'POST',
		dataType : "json",
		url : basePath + "prm/loadContacts.do",// 请求的action路径
		data : {
			value : searchValue,
			queryType : searchType,
			order : type,
			page : pageIndex,
			groupId : groupId
		},
		success : function(data) {
			$(".loading").remove();
			// 加载数据
			PrmGlobal.contactWrap.addContacts(data.contacts);
			// 分页
			var pagecon = new Pager(data.totalPage, 10, pageIndex);
			$("#page").html(pagecon.asHtml());
		}
	});
}

// 通过分组查询联系人
function queryContactByGroupId(groupid) {
	PrmGlobal.groupId = groupid;
	if (PrmGlobal.groupId == "0") {// 全部联系人
		$("#letterList").show();
	} else {
		$("#letterList").hide();
	}
	PrmGlobal.searchValue = "";
	PrmGlobal.searchType = "";
	PrmGlobal.orderType = "";
	loadContact(PrmGlobal.searchValue, PrmGlobal.searchType,
			PrmGlobal.orderType, PrmGlobal.pageIndex, PrmGlobal.groupId);
}

function loadArticle(page) {
	PrmGlobal.pageIndex = page;
	loadContact(PrmGlobal.searchValue, PrmGlobal.searchType,
			PrmGlobal.orderType, PrmGlobal.pageIndex, PrmGlobal.groupId);
}

// 分页控件
function Pager(totalPage, pageNum, page) {
	this.ulPanle = $("<ul></ul>");
	if (totalPage <= 1) {
		return;
	}
	// 起始页
	var beginNum = 0;
	// 结尾页
	var endNum = 0;

	if (pageNum > totalPage) {
		beginNum = 1;
		endNum = totalPage;
	}

	if (page - pageNum / 2 < 1) {
		beginNum = 1;
		endNum = pageNum;
	} else {
		beginNum = page - pageNum / 2 + 1;
		endNum = page + pageNum / 2;
	}

	if (totalPage - page < pageNum / 2) {
		beginNum = totalPage - pageNum + 1;
	}
	if (beginNum < 1) {
		beginNum = 1;
	}
	if (endNum > totalPage) {
		endNum = totalPage;
	}
	if (page > 1) {
		$(
				"<li><a href='javascript:loadArticle(1)' class='prePage'>第一页</a></li>")
				.appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + (page - 1)
						+ ")'>上一页</a></li>").appendTo(this.ulPanle);
	} else {
		$("<li><span>第一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>上一页</span></li>").appendTo(this.ulPanle);
	}
	for ( var i = beginNum; i <= endNum; i++) {
		if (totalPage > 1) {
			if (i == page) {
				$("<li id='nowPage'>" + page + "</li>").appendTo(this.ulPanle);
			} else {
				$(
						"<li><a href='javascript:loadArticle(" + i + ")'>" + i
								+ "</a></li>").appendTo(this.ulPanle);
			}
		}
	}
	if (page < totalPage) {
		$(
				"<li><a href='javascript:loadArticle(" + (page + 1)
						+ ")'>下一页</a></li>").appendTo(this.ulPanle);
		$(
				"<li><a href='javascript:loadArticle(" + totalPage
						+ ")' class='prePage'>最后页</a></li>").appendTo(
				this.ulPanle);
	} else {
		$("<li><span>下一页</span></li>").appendTo(this.ulPanle);
		$("<li><span>最后页</span></li>").appendTo(this.ulPanle);
	}
}
Pager.prototype = {
	asHtml : function() {
		return this.ulPanle;
	}
}
/**
 * 列表获取焦点时显示编辑和删除图标
 * 
 * @author zhangyan3 此功能已在组件中实现 luojian
 */
/*
 * $(document).ready(function(){ $(".linkInfoList").find("li").hover(function(){
 * $(this).addClass("selectLi");
 * $(this).children(".user_list").children(".operatAre").show(); },function(){
 * $(this).removeClass("selectLi");
 * $(this).children(".user_list").children(".operatAre").hide(); }); });
 */
/**
 * 用于引入js文件，类似于java的import关键字 可以在需要某js文件时再引用，而不用加载页面时引用
 * 
 * @param jsUrl
 * @author luojian
 */
function importJS(jsUrl) {
	var arr = document.getElementsByTagName("script");
	var js = document.createElement("script");
	js.type = "text/javascript";
	js.src = jsUrl;
	// 页面是否已引用此JS
	for ( var i = 0; i < arr.length; i++) {
		if (js.src == arr[i].src) {// 如果已有，则不加
			return;
		}
	}
	// 如果没有，则加入引用
	document.getElementsByTagName("head")[0].appendChild(js);
}
