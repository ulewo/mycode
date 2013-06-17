package com.ulewo.enums;

public enum ArticleGrade {
	NORMAL(0), TOP(1);
	private int grade;

	ArticleGrade(int grade) {

		this.grade = grade;
	}

	public int getValue() {

		return grade;
	}
}