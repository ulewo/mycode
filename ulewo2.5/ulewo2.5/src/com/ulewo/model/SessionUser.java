package com.ulewo.model;

public class SessionUser {
    private Integer userId; // 用户ID

    private String userName; // 用户名

    private String userIcon; // 用户小图像

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

}
