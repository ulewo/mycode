package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class User implements Serializable {
	private String userId; // 用户ID

	private String userName; // 用户名

	private String userLittleIcon; // 用户小图像

	private int age; // 年龄

	private String sex; // 性别

	private String characters; // 个性签名

	private String address; // 籍贯

	private String work; // 职业

	private String registerTime; // 注册时间

	private String previsitTime;

	private int mark; // 积分

	private String sessionId;

	private String password;

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

	public int getAge() {

		return age;
	}

	public void setAge(int age) {

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

	public String getSessionId() {

		return sessionId;
	}

	public void setSessionId(String sessionId) {

		this.sessionId = sessionId;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public static User parse(JSONObject jsonobj) throws AppException {

		try {
			JSONObject obj = null;
			if (!jsonobj.isNull("user")) {
				obj = new JSONObject(jsonobj.getString("user"));
			} else {
				obj = jsonobj;
			}
			User user = new User();
			user.setUserId(obj.getString("userId"));
			user.setUserName(obj.getString("userName"));
			user.setUserLittleIcon(obj.getString("userLittleIcon"));
			if (!"".equals(obj.getString("age"))
					&& !"null".equals(obj.getString("age"))
					&& null != obj.getString("age")) {
				user.setAge(obj.getInt("age"));
			}
			user.setRegisterTime(obj.getString("registerTime"));
			user.setAddress(obj.getString("address"));
			if (!"".equals(obj.getString("mark"))) {
				user.setMark(obj.getInt("mark"));
			}
			user.setSex(obj.getString("sex"));
			user.setWork(obj.getString("work"));
			user.setCharacters(obj.getString("characters"));
			user.setSessionId(obj.getString("sessionId"));
			return user;
		} catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
