package com.ulewo.enums;

public enum SearchTypeEnums {
	BLOG("B"), TOPIC("T"), GROUP("G");

	private String type;

	SearchTypeEnums(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
