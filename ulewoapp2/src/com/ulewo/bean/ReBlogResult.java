package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReBlogResult implements Serializable {

	// sessionID
	private String sessionId;

	// 是否已经登录
	private boolean isLogin;

	// 回复的内容
	private ReBlog reblog;

	public String getSessionId() {

		return sessionId;
	}

	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
	}

	public boolean isLogin() {

		return isLogin;
	}

	public void setLogin(boolean isLogin) {

		this.isLogin = isLogin;
	}

	public ReBlog getReblog() {
		return reblog;
	}

	public void setReblog(ReBlog reblog) {
		this.reblog = reblog;
	}

	public static ReBlogResult parse(JSONObject jsonobj) throws AppException {

		try {
			ReBlogResult reBlogResult = new ReBlogResult();
			if (!jsonobj.isNull("reBlog")) {
				reBlogResult.setReblog(ReBlog.parse(new JSONObject(jsonobj
						.getString("reBlog"))));
			}
			reBlogResult.setSessionId(jsonobj.getString("sessionId"));
			reBlogResult.setLogin(jsonobj.getBoolean("isLogin"));
			return reBlogResult;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
