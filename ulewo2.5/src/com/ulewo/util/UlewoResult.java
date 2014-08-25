package com.ulewo.util;


/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2013-12-22 上午11:55:27
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class UlewoResult<T> {
    private String code;
    private T obj;
    private StringBuilder msg;

    public StringBuilder getMsg() {
	return msg;
    }

    public void setMsg(StringBuilder msg) {
	this.msg = msg;
    }

    public T getObj() {
	return obj;
    }

    public void setObj(T obj) {
	this.obj = obj;
    }

    public String getCode() {
	return code;
    }

    public void setCode(String code) {
	this.code = code;
    }

}
