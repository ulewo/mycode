package com.ulewo.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-26 下午5:30:25
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum MaxLengthEnums {
	MAXLENGTH6(6), MAXLENGTH16(16), MAXLENGTH50(50), MAXLENGTH150(150), MAXLENGTH250(250);
	private int length;

	private MaxLengthEnums(int length) {
		this.length = length;
	}

	public int getLength() {
		return this.length;
	}
}
