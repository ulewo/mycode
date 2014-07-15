package com.ulewo.enums;

public enum QueryOrder {
	ASC("asc"), DESC("desc");

	private String order;

	QueryOrder(String order) {

		this.order = order;
	}

	public String getValue() {

		return order;
	}
}
