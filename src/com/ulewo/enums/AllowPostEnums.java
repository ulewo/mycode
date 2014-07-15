package com.ulewo.enums;

public enum AllowPostEnums {
    ALLOW("Y"), NOTALLOW("N");

	private String type;

	AllowPostEnums(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
