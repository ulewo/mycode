package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONObject;

import com.ulewo.enums.ResultEnum;

public class RequestResult implements Serializable {
	private ResultEnum resultEnum;

	private JSONObject jsonObject;

	public ResultEnum getResultEnum() {

		return resultEnum;
	}

	public void setResultEnum(ResultEnum resultEnum) {

		this.resultEnum = resultEnum;
	}

	public JSONObject getJsonObject() {

		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {

		this.jsonObject = jsonObject;
	}

}
