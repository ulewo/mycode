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
public class ReArticleResult implements Serializable {

	//sessionID
	private String sessionId;

	//是否已经登录
	private boolean isLogin;

	//回复的内容
	private ReArticle reArticle;

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

	public ReArticle getReArticle() {

		return reArticle;
	}

	public void setReArticle(ReArticle reArticle) {

		this.reArticle = reArticle;
	}

	public static ReArticleResult parse(JSONObject jsonobj) throws AppException {

		try {
			ReArticleResult reArticleResult = new ReArticleResult();
			if (!jsonobj.isNull("reArticle")) {
				reArticleResult.setReArticle(ReArticle.parse(new JSONObject(jsonobj.getString("reArticle"))));
			}
			reArticleResult.setSessionId(jsonobj.getString("sessionId"));
			reArticleResult.setLogin(jsonobj.getBoolean("isLogin"));
			return reArticleResult;
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
