package com.ulewo.model;

import com.ulewo.util.StringUtils;

public class User {
	private Integer userId; // 用户ID

	private String email; // 邮箱

	private String userName; // 用户名

	private String password; // 密码

	private String userIcon; // 用户图像

	private String age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private Integer mark; // 积分

	private String address; // 籍贯

	private String work; // 职业

	private String centerTheme; // 空间主题

	private String registerTime; // 注册时间

	private String showRegisterTime;

	private String preVisitTime; // 上次登陆时间

	private String showPreVisitTime;

	private String activationCode; // 状态码

	private Integer topicCount; // 发帖数量

	private Integer topicCommentCount; // 回帖数量

	private String isValid;

	private int fansCount;

	private int focusCount;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCharacters() {
		return characters;
	}

	public void setCharacters(String characters) {
		this.characters = characters;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
		this.showRegisterTime = StringUtils.friendly_time(registerTime);
	}

	public String getPreVisitTime() {
		return preVisitTime;
	}

	public void setPreVisitTime(String preVisitTime) {
		this.preVisitTime = preVisitTime;
		this.showPreVisitTime = StringUtils.friendly_time(preVisitTime);
	}

	public String getActivationCode() {
		return activationCode;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getTopicCommentCount() {
		return topicCommentCount;
	}

	public void setTopicCommentCount(Integer topicCommentCount) {
		this.topicCommentCount = topicCommentCount;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public int getFansCount() {
		return fansCount;
	}

	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}

	public int getFocusCount() {
		return focusCount;
	}

	public void setFocusCount(int focusCount) {
		this.focusCount = focusCount;
	}

	public String getCenterTheme() {
		return centerTheme;
	}

	public void setCenterTheme(String centerTheme) {
		this.centerTheme = centerTheme;
	}

	public String getShowRegisterTime() {
		return showRegisterTime;
	}

	public void setShowRegisterTime(String showRegisterTime) {
		this.showRegisterTime = showRegisterTime;
	}

	public String getShowPreVisitTime() {
		return showPreVisitTime;
	}

	public void setShowPreVisitTime(String showPreVisitTime) {
		this.showPreVisitTime = showPreVisitTime;
	}

}
