package com.lhl.exception;

/**
 * @Title:
 * @Description: 没有权限
 * @author luohl
 * @date 2012-3-29
 * @version V1.0
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = 1L;

	protected int code;

	public BaseException(int code) {

		super();
		this.code = code;
	}

	public BaseException(int code, String msg) {

		super(msg);
		this.code = code;
	}

	public BaseException(String msg) {

		super(msg);
	}

	public BaseException(String msg, Throwable cause) {

		super(msg, cause);
	}

	public BaseException(int code, String msg, Throwable cause) {

		super(msg, cause);
		this.code = code;
	}

	public int getCode() {

		return code;
	}

	@Override
	public String getMessage() {

		String message = super.getMessage();
		return message;
	}
}
