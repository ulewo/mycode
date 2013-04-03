package com.ulewo.bean;

import org.json.JSONObject;

import com.ulewo.Enum.ResultEnum;

public class RequestResult {
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
