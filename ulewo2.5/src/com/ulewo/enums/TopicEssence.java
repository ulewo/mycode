package com.ulewo.enums;

public enum TopicEssence {
	NoEssence("N"), Essence("Y");

	private String type;

	TopicEssence(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
