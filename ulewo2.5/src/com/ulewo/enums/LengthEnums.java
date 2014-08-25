package com.ulewo.enums;

/**
 * 长度
 * 
 * @author luo.hl
 * @date 2014-2-13 下午3:40:41
 * @version 0.1.0
 * @copyright yougou.com
 */
public enum LengthEnums {
	Length5(5), Length8(8), Length30(30), Length50(50), Length100(100), Length150(
			150), Length200(200), Length300(300), Length500(500);
	private int length;

	private LengthEnums(int length) {
		this.length = length;
	}

	public int getLength() {
		return length;
	}
}
