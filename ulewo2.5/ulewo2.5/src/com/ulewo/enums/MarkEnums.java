package com.ulewo.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-26 下午4:34:15
 * @version 0.1.0 
 * @copyright www.ulewo.com 
 */
public enum MarkEnums {
	MARK1(1), MARK2(2), MARK5(5), MARK10(10);
	private int mark;

	private MarkEnums(int mark) {
		this.mark = mark;
	}

	public int getMark() {
		return this.mark;
	}
}
