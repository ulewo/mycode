package com.ulewo.vo;

public class UserVo {
	private String userId; // 用户ID

	private String userName; // 用户名

	private String email;

	private String userLittleIcon; // 用户小图像

	private String age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private int mark; // 积分

	private String address; // 籍贯

	private String work; // 职业

	private String registerTime; // 注册时间

	private String previsitTime; // 上次登陆时间

	private int fansCount; //粉丝数

	private int focusCount; //关注数
	
	private boolean haveFocus;

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getUserLittleIcon() {

		return userLittleIcon;
	}

	public void setUserLittleIcon(String userLittleIcon) {

		this.userLittleIcon = userLittleIcon;
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

	public int getMark() {

		return mark;
	}

	public void setMark(int mark) {

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
	}

	public String getPrevisitTime() {

		return previsitTime;
	}

	public void setPrevisitTime(String previsitTime) {

		this.previsitTime = previsitTime;
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

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public boolean isHaveFocus() {
		return haveFocus;
	}

	public void setHaveFocus(boolean haveFocus) {
		this.haveFocus = haveFocus;
	}

}
