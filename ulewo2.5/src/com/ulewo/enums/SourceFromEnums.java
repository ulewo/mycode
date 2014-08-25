package com.ulewo.enums;

public enum SourceFromEnums {
    PC("P"), Android("A");

    private String type;

    SourceFromEnums(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
