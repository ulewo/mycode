package com.ulewo.bean;

import org.json.JSONObject;

import com.ulewo.util.Constants;

public class Group {

	private String groupIcon;

	private String gid;

	private String gName;

	private String gUserName;

	private String gMember;

	private String gArticleCount;

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

	public String getgUserName() {

		return gUserName;
	}

	public void setgUserName(String gUserName) {

		this.gUserName = gUserName;
	}

	public String getgMember() {

		return gMember;
	}

	public void setgMember(String gMember) {

		this.gMember = gMember;
	}

	public String getgArticleCount() {

		return gArticleCount;
	}

	public void setgArticleCount(String gArticleCount) {

		this.gArticleCount = gArticleCount;
	}

	public Group(JSONObject json) {

		try {
			constructJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void constructJson(JSONObject json) throws Exception {

		groupIcon = Constants.BASEURL + "/upload/"
				+ json.getString("groupIcon");
		gid = json.getString("id");
		gName = json.getString("groupName");
		gUserName = json.getString("authorName");
		gMember = json.getString("members");
		gArticleCount = json.getString("topicCount");
	}
}
