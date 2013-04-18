package com.ulewo.bean;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class Group {

	private String groupIcon;

	private String gid;

	private String gName;

	private String gAuthorName;

	private String gAuthorId;

	private int gMember;

	private int gArticleCount;

	public String getGroupIcon() {

		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {

		this.groupIcon = groupIcon;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public String getgName() {

		return gName;
	}

	public void setgName(String gName) {

		this.gName = gName;
	}

	public String getgAuthorName() {

		return gAuthorName;
	}

	public void setgAuthorName(String gAuthorName) {

		this.gAuthorName = gAuthorName;
	}

	public String getgAuthorId() {

		return gAuthorId;
	}

	public void setgAuthorId(String gAuthorId) {

		this.gAuthorId = gAuthorId;
	}

	public int getgMember() {

		return gMember;
	}

	public void setgMember(int gMember) {

		this.gMember = gMember;
	}

	public int getgArticleCount() {

		return gArticleCount;
	}

	public void setgArticleCount(int gArticleCount) {

		this.gArticleCount = gArticleCount;
	}

	public static Group parse(JSONObject obj) throws AppException {

		try {
			Group group = new Group();
			group.setGid(obj.getString("gid"));
			group.setgName(obj.getString("gName"));
			group.setgArticleCount(obj.getInt("gArticleCount"));
			group.setgAuthorId(obj.getString("gAuthorId"));
			group.setgAuthorName(obj.getString("gAuthorName"));
			group.setgMember(obj.getInt("gMember"));
			group.setGroupIcon(obj.getString("groupIcon"));
			return group;
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
