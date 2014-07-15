package com.ulewo.enums;

public enum ThemeTypeEnums {
    TYPE0("0"), TYPE1("1");

    private String type;

    ThemeTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
