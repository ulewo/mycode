package com.ulewo.enums;

public enum ArticleEssence {
	NoEssence("N"), Essence("Y");

	private String type;

	ArticleEssence(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
