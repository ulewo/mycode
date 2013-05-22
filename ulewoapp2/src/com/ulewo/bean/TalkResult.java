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
public class TalkResult implements Serializable {

	// sessionID
	private String sessionId;

	// 是否已经登录
	private boolean isLogin;

	// 回复的内容
	private Talk talk;

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

	public Talk getTalk() {

		return talk;
	}

	public void setTalk(Talk talk) {

		this.talk = talk;
	}

	public static TalkResult parse(JSONObject jsonobj) throws AppException {

		try {
			TalkResult talkResult = new TalkResult();
			if (!jsonobj.isNull("talk")) {
				talkResult.setTalk(Talk.parse(new JSONObject(jsonobj.getString("talk"))));
			}
			talkResult.setSessionId(jsonobj.getString("sessionId"));
			talkResult.setLogin(jsonobj.getBoolean("isLogin"));
			return talkResult;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
