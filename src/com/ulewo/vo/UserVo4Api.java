package com.ulewo.vo;

public class UserVo4Api {
	private Integer userId; // 用户ID

	private String userName; // 用户名

	private String userIcon; // 用户小图像

	private String registerTime; // 注册时间

	private String previsitTime;

	private Integer mark; // 积分

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

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getPrevisitTime() {
		return previsitTime;
	}

	public void setPrevisitTime(String previsitTime) {
		this.previsitTime = previsitTime;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

}
