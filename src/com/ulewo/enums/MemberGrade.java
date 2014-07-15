package com.ulewo.enums;

public enum MemberGrade {
	ADMIN(1), MEMBER(0);

	private int grade;

	MemberGrade(int grade) {

		this.grade = grade;
	}

	public int getValue() {

		return grade;
	}
}
