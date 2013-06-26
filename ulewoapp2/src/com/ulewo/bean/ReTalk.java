package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class ReTalk implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private int talkId;

	private String userId;

	private String userName;

	private String userIcon;

	private String content;

	private String createTime;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getUserIcon() {

		return userIcon;
	}

	public void setUserIcon(String userIcon) {

		this.userIcon = userIcon;
	}

	public String getContent() {

		return content;
	}

	public void setContent(String content) {

		this.content = content;
	}

	public String getCreateTime() {

		return createTime;
	}

	public void setCreateTime(String createTime) {

		this.createTime = createTime;
	}

	public int getTalkId() {
		return talkId;
	}

	public void setTalkId(int talkId) {
		this.talkId = talkId;
	}

	public static ReTalk parse(JSONObject jsonobj) throws AppException {

		try {
			JSONObject obj = null;
			if (!jsonobj.isNull("retalk")) {
				obj = new JSONObject(jsonobj.getString("retalk"));
			} else {
				obj = jsonobj;
			}
			ReTalk talk = new ReTalk();
			talk.setId(obj.getInt("id"));
			talk.setContent(obj.getString("content"));
			talk.setCreateTime(obj.getString("createTime"));
			talk.setUserIcon(obj.getString("userIcon"));
			talk.setUserName(obj.getString("userName"));
			talk.setUserId(obj.getString("userId"));
			return talk;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
