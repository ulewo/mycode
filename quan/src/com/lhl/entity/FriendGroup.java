package com.lhl.entity;

public class FriendGroup {
	private int id;

	private String gid;

	private String friendGid;

	private String groupName;

	private String groupIcon;

	private int memberCount;

	private int articleCount;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public String getFriendGid() {

		return friendGid;
	}

	public void setFriendGid(String friendGid) {

		this.friendGid = friendGid;
	}

	public String getGroupName() {

		return groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public int getMemberCount() {

		return memberCount;
	}

	public void setMemberCount(int memberCount) {

		this.memberCount = memberCount;
	}

	public int getArticleCount() {

		return articleCount;
	}

	public void setArticleCount(int articleCount) {

		this.articleCount = articleCount;
	}

	public String getGroupIcon() {

		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {

		this.groupIcon = groupIcon;
	}

}
