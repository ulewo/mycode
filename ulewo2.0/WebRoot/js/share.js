var share_title = "(分享自 你乐我 ulewo.com)";

function dispatche(_type, content, imgUrl, id) {
	var _data = {};
	_data.content = content;
	_data.imgUrl = imgUrl;
	_data.id = id;
	switch (_type) {
	case 0:
		// 0=分享到QQ空间
		share_qzone(_data)
		break;
	case 1:
		// 1=分享到腾讯微博
		share_tx_wb(_data)
		break;
	case 2:
		// 2分享到新浪
		share_sina_wb(_data)
		break;
	case 3:
		// 3分享到人人网
		share_renren(_data);
		break;
	default:
		window.location = _data.wbUrl;
		break;
	}
}
// 分享到QQ空间
function share_qzone(data) {
	var param = [];
	data.content = data.content + share_title;
	var wburl = "http://ulewo.com/detail?id=" + data.id;
	var _url = "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?";
	param[0] = "url=" + encodeURIComponent(wburl);
	param[1] = "title=" + data.content;
	param[2] = "pics=" + realPath + "upload/big/" + data.imgUrl;
	param[3] = "summary=" + data.content;
	_url += param.join("&");
	forward(_url);
}

// 分享到腾讯微博
function share_tx_wb(data) {
	var param = [];
	var wburl = "http://ulewo.com/detail?id=" + data.id;
	data.content = data.content + share_title;
	var _url = "http://share.v.t.qq.com/index.php?";
	param[0] = "c=share";
	param[1] = "a=index";
	param[2] = "url=" + wburl;
	param[3] = "pic=" + realPath + "upload/big/" + data.imgUrl;
	param[4] = "title=" + data.content;
	_url += param.join("&");
	forward(_url);
}
// 分享到新浪微博
function share_sina_wb(data) {
	var param = [];
	var wburl = "http://ulewo.com/detail?id=" + data.id;
	param[0] = "pic=" + realPath + "upload/big/" + data.imgUrl;
	param[1] = "title=" + data.content;
	param[2] = "url=" + wburl;
	var _url = "http://service.t.sina.com.cn/share/share.php?";
	_url += param.join("&");
	forward(_url);
}

// 分享到人人网
function share_renren(data) {
	var param = [];
	var wburl = "http://ulewo.com/detail?id=" + data.id;
	param[0] = "link=" + wburl;
	param[1] = "description=" + data.content;
	param[2] = "title=" + data.content;
	param[3] = "pic=" + realPath + "upload/big/" + data.imgUrl;
	var _url = "http://widget.renren.com/dialog/share?";
	_url += param.join("&");
	forward(_url);
}

function forward(_url) {
	window.open(_url);
}
