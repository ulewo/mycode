package com.ulewo.vo;

public class UserVo {
	private Integer userId; // 用户ID

	private String userName; // 用户名

	private String userIcon; // 用户小图像

	private String age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private String address; // 籍贯

	private String work; // 职业

	private String registerTime; // 注册时间

	private String previsitTime;

	private String email;

	private Integer mark; // 积分

	private String sessionId;

	private int fansCount;

	private int focusCount;

	private boolean haveFocus;

	private String centerTheme; // 空间主题

	public String getPrevisitTime() {

		return previsitTime;
	}

	public void setPrevisitTime(String previsitTime) {

		this.previsitTime = previsitTime;
	}

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
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getSessionId() {

		return sessionId;
	}

	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
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

	public boolean isHaveFocus() {

		return haveFocus;
	}

	public void setHaveFocus(boolean haveFocus) {

		this.haveFocus = haveFocus;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getCenterTheme() {
		return centerTheme;
	}

	public void setCenterTheme(String centerTheme) {
		this.centerTheme = centerTheme;
	}

}
