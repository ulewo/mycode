package com.ulewo.enums;

public enum PostPerm {
    ALL("A"), YES("Y");

    private String type;

    PostPerm(String type) {

	this.type = type;
    }

    public String getValue() {

	return type;
    }
}
