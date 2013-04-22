package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class LoginUser implements Serializable {
	private String loginResult;

	private User user;

	public String getLoginResult() {

		return loginResult;
	}

	public void setLoginResult(String loginResult) {

		this.loginResult = loginResult;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	public static LoginUser parse(JSONObject obj) throws AppException {

		try {
			LoginUser loginUser = new LoginUser();
			if (!obj.isNull("user")) {
				loginUser.setUser(User.parse(new JSONObject(obj.getString("user"))));
			}
			loginUser.setLoginResult(obj.getString("loginResult"));
			return loginUser;
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
