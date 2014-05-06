package com.ulewo.enums;

public enum TrendsType {
	ALL(0), TOPIC(1), RE(2), MYTOPIC(3), MYRE(4);

	private int type;

	TrendsType(int type) {

		this.type = type;
	}

	public int getValue() {

		return type;
	}
}
