package com.ulewo.enums;

public enum QueryType {
	ALL("all"), IMAGE("image"), NOIMAGE("noimage");

	private String type;

	QueryType(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
