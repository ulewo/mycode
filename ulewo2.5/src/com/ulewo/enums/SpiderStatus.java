package com.ulewo.enums;

public enum SpiderStatus {
	STATUS0("0", "未发布"), STATUS1("1", "已发布");
	;
	private String status;
	private String desc;

	SpiderStatus(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}
}
