package com.ulewo.enums;

public enum TaskType {
	QUERYARTICLES(1), SHOWARTICLE(2), QUERYBLOGES(3), SHOWBLOG(4), GROUP(5), SHOWGROUP(
			6), ARTICLECOMMENT(7), USERINFO(8), ADDRECOMMENT(9);

	private final int value;

	public int getValue() {

		return this.value;
	}

	TaskType(int value) {

		this.value = value;
	}
}
