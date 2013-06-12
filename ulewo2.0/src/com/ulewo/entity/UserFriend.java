package com.ulewo.entity;

public class UserFriend {
	private String userId;
	private String friendId;
	private String friendName;
	private String friendIcon;
	private String createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}

	public String getFriendIcon() {
		return friendIcon;
	}

	public void setFriendIcon(String friendIcon) {
		this.friendIcon = friendIcon;
	}

}
