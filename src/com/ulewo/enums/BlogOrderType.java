package com.ulewo.enums;

public enum BlogOrderType {
	POSTTIME("time"), READCOUNT("readcount");

	private String order;

	BlogOrderType(String order) {

		this.order = order;
	}

	public String getValue() {

		return order;
	}
}
