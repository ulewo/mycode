package com.ulewo.enums;

public enum MemberType {
	ISMEMBER("Y", "已经是成员"), NOTMEMBER("N", "已经申请，未审核"), ALLMEMBER("", "所有成员");
	private String status;

	MemberType(String status, String desc) {
		this.status = status;
	}

	public String getValue() {
		return status;
	}
}
