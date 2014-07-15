package com.ulewo.enums;

public enum LikeType {
	TOPIC("T"), BLOG("B"), BLAST("L");

	private String type;

	LikeType(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
