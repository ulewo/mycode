package com.ulewo.enums;

public enum SpiderType {
	OSCHINA("osc", "http://www.oschina.net", "开源中国"), XINWENGE("xwg", "http://xw.qq.com/c/dy/1033/info", "新闻哥"), CNBLOG(
			"cnblog", "http://news.cnblogs.com/n/recommend", "博客园资讯"), QILU("qilu", "http://news.iqilu.com/", "齐鲁网");
	private String type;
	private String url;

	SpiderType(String type, String url, String desc) {
		this.type = type;
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public String getUrl() {
		return url;
	}
}
