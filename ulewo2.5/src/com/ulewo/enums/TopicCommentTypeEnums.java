package com.ulewo.enums;

public enum TopicCommentTypeEnums {
	COMMENT("C"), SUBCOMMENT("S");

	private String type;

	TopicCommentTypeEnums(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
