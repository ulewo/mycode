package com.ulewo.enums;

public enum SpiderType {
	OSCHINA("osc", "http://www.oschina.net", "开源中国"), XINWENGE("xwg",
			"http://xw.qq.com/c/dy/1033/info", "新闻哥"), CNBLOG("cnblog",
			"http://news.cnblogs.com/n/recommend", "博客园资讯"), QILU("qilu",
			"http://news.iqilu.com/", "齐鲁网"), GB("gb", "http://www.gb1.cn/",
			"逛吧"), GBPIC("gb_pic", "http://www.gb1.cn/", "逛吧图片"), GBGAME(
			"gb_game", "http://www.gb1.cn", "逛吧游戏"), GBMOVIE("gb_movie",
			"http://www.gb1.cn/", "逛吧视频"), GBTOPIC("gb_topic",
			"http://www.gb1.cn/", "逛吧文字"), GBTALK("gb_talk",
			"http://www.gb1.cn/", "逛吧杂谈"), GBJOKE("gb_joke",
			"http://www.gb1.cn/", "逛吧搞笑");
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
