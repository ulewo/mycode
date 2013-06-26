package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

/**
 * @Title:
 * @Description: 发表吐槽
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class ReTalkResult implements Serializable {

	// sessionID
	private String sessionId;

	// 是否已经登录
	private boolean isLogin;

	// 回复的内容
	private ReTalk retalk;

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

	public ReTalk getReTalk() {

		return retalk;
	}

	public void setReTalk(ReTalk retalk) {

		this.retalk = retalk;
	}

	public static ReTalkResult parse(JSONObject jsonobj) throws AppException {

		try {
			ReTalkResult talkResult = new ReTalkResult();
			if (!jsonobj.isNull("retalk")) {
				talkResult.setReTalk(ReTalk.parse(new JSONObject(jsonobj
						.getString("retalk"))));
			}
			talkResult.setSessionId(jsonobj.getString("sessionId"));
			talkResult.setLogin(jsonobj.getBoolean("isLogin"));
			return talkResult;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
