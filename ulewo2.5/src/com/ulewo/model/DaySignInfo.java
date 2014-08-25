package com.ulewo.model;

public class DaySignInfo {
	private String day;

	private boolean signinType;

	private boolean beforeNowDate;

	private boolean curDay;

	public boolean isSigninType() {
		return signinType;
	}

	public void setSigninType(boolean signinType) {
		this.signinType = signinType;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public boolean isBeforeNowDate() {
		return beforeNowDate;
	}

	public void setBeforeNowDate(boolean beforeNowDate) {
		this.beforeNowDate = beforeNowDate;
	}

	public boolean isCurDay() {
		return curDay;
	}

	public void setCurDay(boolean curDay) {
		this.curDay = curDay;
	}

}