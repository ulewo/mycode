package com.ulewo.util;

import java.util.List;

import com.ulewo.enums.ResultCode;

/**
 * 分页查询公共类
 * 
 * @author luo.hl
 * @date 2013-12-9 下午3:09:30
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class UlewoPaginationResult<T> {
    private SimplePage page;
    private List<T> list;
    private ResultCode resultCode = ResultCode.SUCCESS;// 500 服务器错误
    private StringBuilder msg = new StringBuilder("请求成功");

    public UlewoPaginationResult(SimplePage page, List<T> list,
	    ResultCode resultCode, StringBuilder msg) {
	this.list = list;
	this.page = page;
	this.resultCode = resultCode;
	this.msg = msg;
    }

    public UlewoPaginationResult(SimplePage page, List<T> list) {
	this.list = list;
	this.page = page;
    }

    public UlewoPaginationResult(List<T> list, ResultCode resultCode,
	    StringBuilder msg) {
	this.list = list;
	this.page = page;
	this.resultCode = resultCode;
	this.msg = msg;
    }

    public UlewoPaginationResult() {

    }

    public SimplePage getPage() {
	return page;
    }

    public void setPage(SimplePage page) {
	this.page = page;
    }

    public List<T> getList() {
	return list;
    }

    public void setList(List<T> list) {
	this.list = list;
    }

    public ResultCode getResultCode() {
	return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
	this.resultCode = resultCode;
    }

    public StringBuilder getMsg() {
	return msg;
    }

    public void setMsg(StringBuilder msg) {
	this.msg = msg;
    }
}
