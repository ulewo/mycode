package com.ulewo.exception;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-9 下午3:31:04
 * @version 3.0
 * @copyright www.ulewo.com 
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 2874310081615076500L;

	public BusinessException(String message, Throwable e) {
		super(message, e);
	}

	public BusinessException(String message) {
		super(message);
	}
}
