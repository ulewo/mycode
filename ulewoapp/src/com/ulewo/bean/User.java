package com.ulewo.bean;

import org.json.JSONObject;

public class User {
	private String userId; // 用户ID

	private String userName; // 用户名

	private String userLittleIcon; // 用户小图像

	private String age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private String address; // 籍贯

	private String work; // 职业

	private String registerTime; // 注册时间

	private String previsitTime;

	private int mark; // 积分

	private String sessionId;

	public String getPrevisitTime() {

		return previsitTime;
	}

	public void setPrevisitTime(String previsitTime) {

		this.previsitTime = previsitTime;
	}

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

	public int getMark() {

		return mark;
	}

	public void setMark(int mark) {

		this.mark = mark;
	}

	public User(JSONObject json) {
		try {
			constructJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	private void constructJson(JSONObject json) throws Exception {
		userId = json.getString("userId");
		userName = json.getString("userName");
		userLittleIcon = json.getString("userLittleIcon");
		age = json.getString("age");
		sex = json.getString("sex");
		characters = json.getString("characters");
		address = json.getString("address");
		work = json.getString("work");
		registerTime = json.getString("registerTime");
		previsitTime = json.getString("previsitTime");
		sessionId = json.getString("sessionId");
	}
}
