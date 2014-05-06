package com.ulewo.enums;

public enum NoticeStatus {
    STATUS0("0", "未读"), STATUS1("1", "已读");

    private String status;

    private NoticeStatus(String status, String desc) {
	this.status = status;
    }

    public String getStauts() {
	return this.status;
    }
}
