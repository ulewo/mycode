package com.lhl.entity;

import java.util.List;

@SuppressWarnings("rawtypes")
public class Response {
	private int resultCode;

	private Object obj;

	private List list;

	public Response(int resultCode, Object obj, List list) {

		this.resultCode = resultCode;
		this.obj = obj;
		this.list = list;
	}

	public int getResultCode() {

		return resultCode;
	}

	public void setResultCode(int resultCode) {

		this.resultCode = resultCode;
	}

	public Object getObj() {

		return obj;
	}

	public void setObj(Object obj) {

		this.obj = obj;
	}

	public List getList() {

		return list;
	}

	public void setList(List list) {

		this.list = list;
	}

}