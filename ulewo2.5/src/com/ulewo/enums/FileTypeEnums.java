package com.ulewo.enums;

public enum FileTypeEnums {
    IMAGE("I"), RAR("R");

    private String type;

    FileTypeEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
