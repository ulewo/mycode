package com.ulewo.enums;

public enum CollectionTypeEnums {
    TOPIC("A"), BLOG("B");

    private String type;

    CollectionTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
