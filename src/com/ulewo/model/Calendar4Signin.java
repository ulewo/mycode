package com.ulewo.model;

import java.util.List;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-4-2 上午11:52:53
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class Calendar4Signin {
	private String month;

	private int monthDays;

	private int fristDay;

	private List<DaySignInfo> dayInfos;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getMonthDays() {
		return monthDays;
	}

	public void setMonthDays(int monthDays) {
		this.monthDays = monthDays;
	}

	public int getFristDay() {
		return fristDay;
	}

	public void setFristDay(int fristDay) {
		this.fristDay = fristDay;
	}

	public List<DaySignInfo> getDayInfos() {
		return dayInfos;
	}

	public void setDayInfos(List<DaySignInfo> dayInfos) {
		this.dayInfos = dayInfos;
	}
}
