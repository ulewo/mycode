var share_title = "(分享自 有乐窝 www.ulewo.com)";

function dispatche(_type,summary) {
	var title = document.title+share_title;
	var url = window.location.href;
	switch (_type) {
	case 0:
		// 0分享到新浪
		share_sina_wb(title,url);
		break;
	case 1:
		// 1=分享到QQ空间
		share_qzone(title,url,summary);
		
		break;
	case 2:
		// 2=分享到腾讯微博
		share_tx_wb(title,url);
		break;
	case 3:
		// 3分享到人人网
		share_renren(title,url);
		break;
	}
}
//分享到新浪微博
function share_sina_wb(title,url) {
	var param = [];
	param[0] = "title=" + title;
	param[1] = "url=" + encodeURIComponent(url);
	var _url = "http://service.t.sina.com.cn/share/share.php?";
	_url += param.join("&");
	forward(_url);
}
// 分享到QQ空间
function share_qzone(title,url,summary) {
	var param = [];
	var _url = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?";
	param[0] = "url=" + encodeURIComponent(url);
	param[1] = "title=" + title;
	param[2] = "summary=" + summary;
	_url += param.join("&");
	forward(_url);
}

// 分享到腾讯微博
function share_tx_wb(title,url) {
	var param = [];
	var _url = "http://share.v.t.qq.com/index.php?";
	param[0] = "c=share";
	param[1] = "a=index";
	param[2] = "url=" + encodeURIComponent(url);
	param[3] = "title=" + title;
	_url += param.join("&");
	forward(_url);
}

// 分享到人人网
function share_renren(title,url) {
	var param = [];
	param[0] = "link=" + encodeURIComponent(url);
	param[1] = "title=" + title;
	var _url = "http://widget.renren.com/dialog/share?";
	_url += param.join("&");
	forward(_url);
}

function forward(_url) {
	window.open(_url);
}
