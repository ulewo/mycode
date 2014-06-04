package com.ulewo.enums;

public enum SpiderType {
	OSCHINA("osc", "http://www.oschina.net");
	private String type;
	private String url;

	SpiderType(String type, String url) {
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
