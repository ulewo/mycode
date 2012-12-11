function addNote(){
	var userName = $("#userName").val();
	var content = $("#content").val();
	if(userName==""){
		alert("留下大名，告诉我你是谁吧");
		return;
	}
	if(content==""){
		alert("好歹写点东西吧");
		return;
	}
	var quote = ""
	if($("#quote_panle").html()!=null){
		quote = $("#quote_panle").html();
	}
	$("#subBtn").hide();
	$("#load").show();
	$.ajax({
		async : true,
		cache : true,
		type : 'POST',
		dataType : "json",
		data:{
			userName:userName,
			content:content,
			quote:quote
		},
		url : 'note?method=add',// 
		success : function(data) {
			new NotePanle(data.note).asHtml().appendTo($("#note_list"));
			 $("#userName").val("姓名");
			 $("#userName").css({"color":"#666666"});
			 $("#content").val("请输入你想说的");
			 $("#content").css({"color":"#666666"});
			$("#subBtn").show();
			$("#load").hide();
			$("#quote_panle").remove();
		}
	});
}

function loadArticle(page){
	loadNotes(page);
}

function loadNotes(page){
	$("<div>正在奋力为你加载留言.....</div>").appendTo($("#note_list"));
	$.ajax({
		async : true,
		cache : true,
		type : 'POST',
		dataType : "json",
		url : 'note?page='+page,// 
		success : function(data) {
			$("#note_list").html("");
			$(".pageArea").html("");
			var notes =data.list;
			for(var i = 0;i<notes.length;i++){
				var rePanel = new NotePanle(notes[i]).asHtml();
				if(i==0){
					rePanel.css({"marginTop":"0px"});
				}
				rePanel.appendTo($("#note_list"));
			}
			new Pager(data.pageTotal, 10,page).asHtml().appendTo(
					$(".pageArea"));
		}
	});
}

function NotePanle(note){
	this.noteCon = $("<div class='note_tr'></div>");
	var re_name_time = $("<div class='note_name_time'></div>").appendTo(this.noteCon);
	var adminstyle="";
	if(note.type=="A"){
		adminstyle = "adminstyle";
	}
	$("<div class='note_name "+adminstyle+"'>"+note.userName+"</div>").appendTo(re_name_time);
	$("<div class='note_time'>"+note.postTime.substring(0,19)+"</div>").appendTo(re_name_time);
	$("<a href='javascript:void(0)'>引用</a>").bind("click",this.quote).appendTo($("<div class='re_time'></div>").appendTo(re_name_time));
	$("<div class='note_con'>"+note.content+"</div>").appendTo(this.noteCon);
}
NotePanle.prototype={
	asHtml:function(){
		return this.noteCon;
	},
	quote:function(){
		$("#quote_panle").remove();
		var _this = $(this);
		var name = $(this).parent().parent().children().eq(0).html();
		var time = $(this).parent().parent().children().eq(1).html();
		var content = $(this).parent().parent().parent().children().eq(1).html();
		var quote_panle = $("<div id='quote_panle'></div>");
		$("#userName").before(quote_panle);
		var quote = $("<div class='quote'></div>").appendTo(quote_panle);
		var quote_tit = $("<div class='quote_tit'></div>").appendTo(quote);
		$("<img src='images/qbar_iconb24.gif'><span>引用</span>").appendTo(quote_tit);
		$("<span class='quote_name'>"+name+"</span>").appendTo(quote_tit);
		$("<span>在"+time+"的发表</span>").appendTo(quote_tit);
		$("<div class='quote_content'>"+content+"</div>").appendTo(quote);
	}
}