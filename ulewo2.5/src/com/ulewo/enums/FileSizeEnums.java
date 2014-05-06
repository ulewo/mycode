package com.ulewo.enums;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-2-13 下午3:40:41
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public enum FileSizeEnums {
	SIZE1024_1024(1048576), SIZE600(600);
	private int size;

	private FileSizeEnums(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
}
