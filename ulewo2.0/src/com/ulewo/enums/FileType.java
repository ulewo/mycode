package com.ulewo.enums;

public enum FileType {
	IMAGE("I"), RAR("R");

	private String type;

	FileType(String type) {

		this.type = type;
	}

	public String getValue() {

		return type;
	}
}
