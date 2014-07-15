package com.ulewo.enums;

public enum TopicGrade {
	NORMAL(0), TOP(2);
	private int grade;

	TopicGrade(int grade) {

		this.grade = grade;
	}

	public int getValue() {

		return grade;
	}
}
