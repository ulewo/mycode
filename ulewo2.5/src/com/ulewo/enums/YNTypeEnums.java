package com.ulewo.enums;

public enum YNTypeEnums {
    TYPEY("Y"), TYPEN("N");

    private String type;

    YNTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
