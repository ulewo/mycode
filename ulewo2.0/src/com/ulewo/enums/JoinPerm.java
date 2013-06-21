package com.ulewo.enums;

public enum JoinPerm {
	ALL("Y"), APPLY("N");

	private String type;

	JoinPerm(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
