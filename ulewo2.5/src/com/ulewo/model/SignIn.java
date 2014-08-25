package com.ulewo.model;

import com.ulewo.util.StringUtils;

public class SignIn {
	private Integer userId;
	private String userName;
	private String userIcon;
	private String signDate;
	private String signTime;
	private String showSignTime;
	private String sourceFrom;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
		this.showSignTime = StringUtils.friendly_time(signTime);
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getShowSignTime() {
		return showSignTime;
	}

	public void setShowSignTime(String showSignTime) {
		this.showSignTime = showSignTime;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

}
