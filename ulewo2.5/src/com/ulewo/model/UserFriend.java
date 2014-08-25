package com.ulewo.model;

/**
 * 好友
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:46:37
 * @version 3.0
 * @copyright www.ulewo.com
 */
public class UserFriend {
	private Integer userId;
	private Integer friendUserId;
	private String createTime;
	private String friendName;
	private String friendIcon;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFriendUserId() {
		return friendUserId;
	}

	public void setFriendUserId(Integer friendUserId) {
		this.friendUserId = friendUserId;
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
