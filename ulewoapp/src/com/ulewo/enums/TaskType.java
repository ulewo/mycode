package com.ulewo.enums;

public enum TaskType {
	QUERYARTICLES(1), SHOWARTICLE(2), QUERYBLOGES(3), SHOWBLOG(4);

	private final int value;

	public int getValue() {
		return this.value;
	}

	TaskType(int value) {
		this.value = value;
	}
}
