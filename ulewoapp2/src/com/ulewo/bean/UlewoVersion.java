package com.ulewo.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class UlewoVersion {
	private String appName;

	private double version;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public static UlewoVersion parse(JSONObject jsonobj) throws AppException {

		try {
			UlewoVersion version = new UlewoVersion();
			version.setAppName(jsonobj.getString("app_name"));
			version.setVersion(jsonobj.getDouble("version"));
			return version;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
