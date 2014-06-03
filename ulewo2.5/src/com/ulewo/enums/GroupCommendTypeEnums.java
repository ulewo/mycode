package com.ulewo.enums;

public enum GroupCommendTypeEnums {
	COMMENT("Y"), NOCOMMENT("N");

	private String type;

	GroupCommendTypeEnums(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
