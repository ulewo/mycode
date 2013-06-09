package com.ulewo.enums;

public enum MemberStatus {
	ISMEMBER("Y"), NOTMEMBER("N"), ALLMEMBER("");

	private String status;

	MemberStatus(String status) {

		this.status = status;
	}

	public String getValue() {

		return status;
	}
}
