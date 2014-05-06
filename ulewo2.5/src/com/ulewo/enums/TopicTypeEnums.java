package com.ulewo.enums;

public enum TopicTypeEnums {
    COMMON("0"), SURVEY("1");

    private String type;

    TopicTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
