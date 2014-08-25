//实现js replaceAll方法
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};

// 实现js trim方法
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g, "");
};

// 转义html标签
String.prototype.replaceHtml = function() {
	return this.trim().replaceAll(">", "&gt;").replaceAll("<", "&lt;");
};

// 判断是不是数字
String.prototype.isNumber = function() {
	var str = this.trim();
	var reg = /[0-9]+$/;
	if (reg.test(str)) {
		return true;
	} else {
		return false;
	}
};

// 判断字符的长度，中文占2个字符，数字，字母占有一个字符
String.prototype.realLength = function() {
	var val = this;
	var len = 0;
	var arrayVal = val.split("");
	for (var i = 0; i < arrayVal.length; i++) {
		if (arrayVal[i].match(/[^\x00-\xff]/ig) != null) // 全角
		len += 2;
		else len += 1;
	}
	return len;
};

String.prototype.cutString = function(len) {
	var str = this;
	var str_length = 0;
	var str_len = 0;
	str_cut = new String();
	str_len = str.length;
	for (var i = 0; i < str_len; i++) {
		a = str.charAt(i);
		str_length++;
		if (escape(a).length > 4) {
			//中文字符的长度经编码之后大于4
			str_length++;
		}
		str_cut = str_cut.concat(a);
		if (str_length >= len) {

			return str_cut;
		}
	}
	//如果给定字符串小于指定长度，则返回源字符串；
	if (str_length < len) {
		return str;
	}

};

String.prototype.endWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length) return false;
	if (this.substring(this.length - str.length) == str) {
		return true;
	}
	else {
		return false;
	}
	return true;
};

String.prototype.startWith = function(str) {
	if (str == null || str == "" || this.length == 0 || str.length > this.length) return false;
	if (this.substr(0, str.length) == str) {
		return true;
	}
	else {
		return false;
	}
	return true;
};

String.prototype.contains = function(con) {   
	var str = this;  
	if(str.indexOf(con) != -1){
		return true;
	}else{
		return false;
	}
}

//判断是否有内容
String.prototype.haveContent = function() {
	var str = this;
	 if(str.indexOf("img") != -1||str.indexOf("embed") != -1){
		 return true;
	 }else{
		 return false;
	 }
	 
};