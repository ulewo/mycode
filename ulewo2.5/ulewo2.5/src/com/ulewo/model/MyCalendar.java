package com.ulewo.model;

public class MyCalendar {
	private static final int INIYEAR = 1900;
	private static final int LEAPDAYS = 366;
	private static final int NONLEAPDAYS = 365;
	private int thisYear;
	private static MyCalendar calendar;

	static {
		calendar = new MyCalendar();
	}

	public static MyCalendar getInstance() {
		return calendar;
	}

	public static void main(String[] args) {
		MyCalendar.getInstance().yearAllCalendar(2014);
	}

	public void yearAllCalendar(int year) {
		for (int i = 1; i <= 12; i++) {
			monthCalendar(year, i);
		}
	}

	public void monthCalendar(int year, int month) {
		System.out.println("\n\n------------------------" + year + "年" + month + "月" + "-----------------------");
		System.out.println("日\t一\t二\t三\t四\t五\t六");
		int thisMonthDays = oneMonthDays(month);
		int fristDay = (monthOfWeek(year, month) + 1) % 7 - 1;
		System.out.println("*******************" + fristDay + "*******************");
		for (int i = 0; i < fristDay; i++) {
			System.out.print("\t");
		}
		for (int i = 1; i <= thisMonthDays; i++) {
			System.out.print(i + "\t");
			if ((fristDay + i) % 7 == 0) {
				System.out.println();
			}
		}
		System.out.println("\n-----------------------<<<<<<>>>>>---------------------");
	}

	public boolean isLeapYear(int year) {
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			return true;
		}
		return false;
	}

	public int totalDays(int year) {
		int years = year - INIYEAR;
		int toYear = INIYEAR;
		int totalDays = 0;
		if (years - toYear > 0) {
			return -1;
		}
		while (toYear < year) {
			if (isLeapYear(toYear)) {
				totalDays += LEAPDAYS;
			} else {
				totalDays += NONLEAPDAYS;
			}
			toYear++;
		}
		return totalDays;
	}

	public int dayOfWeek(int days) {
		return days % 7;
	}

	public int oneMonthDays(int month) {
		switch (month) {
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (isLeapYear(MyCalendar.getInstance().getThisYear())) {
				return 29;
			} else {
				return 28;
			}
		default:
			return 31;
		}
	}

	public int monthsDays(int month) {
		int monthsDays = 0;
		for (int i = 1; i < month; i++) {
			monthsDays += oneMonthDays(i);
		}
		return monthsDays;
	}

	public int yearAndMonthDays(int year, int month) {
		return totalDays(year) + monthsDays(month);
	}

	public int monthOfWeek(int year, int month) {
		return MyCalendar.getInstance().dayOfWeek(MyCalendar.getInstance().yearAndMonthDays(year, month)) + 1;
	}

	public void setThisYear(int thisYear) {
		this.thisYear = thisYear;
	}

	public int getThisYear() {
		return thisYear;
	}
}