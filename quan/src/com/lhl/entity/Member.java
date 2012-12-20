package com.lhl.entity;

public class Member {
	/*id*/
	private int id;

	/*组ID*/
	private String gid;

	/*用户ID*/
	private String userId;

	/*用户名称*/
	private String userName;

	/*用户图标*/
	private String userIcon;

	private String isMember;

	/* O 普通成员  1 管理员 2 圈主*/
	private int grade;

	/*加入时间*/
	private String joinTime;

	/*发表的主题帖*/
	private int topicCount;

	/*回复帖子*/
	private int reCount;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
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

	public String getUserIcon() {

		return userIcon;
	}

	public void setUserIcon(String userIcon) {

		this.userIcon = userIcon;
	}

	public String getIsMember() {

		return isMember;
	}

	public void setIsMember(String isMember) {

		this.isMember = isMember;
	}

	public int getGrade() {

		return grade;
	}

	public void setGrade(int grade) {

		this.grade = grade;
	}

	public String getJoinTime() {

		return joinTime;
	}

	public void setJoinTime(String joinTime) {

		this.joinTime = joinTime;
	}

	public int getTopicCount() {

		return topicCount;
	}

	public void setTopicCount(int topicCount) {

		this.topicCount = topicCount;
	}

	public int getReCount() {

		return reCount;
	}

	public void setReCount(int reCount) {

		this.reCount = reCount;
	}

}
