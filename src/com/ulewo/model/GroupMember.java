package com.ulewo.model;

import com.ulewo.util.StringUtils;

public class GroupMember {
	/* id */
	private Integer id;

	/* 组ID */
	private Integer gid;

	/* 用户ID */
	private Integer userId;

	/* 用户名称 */
	private String userName;

	/* 用户图标 */
	private String userIcon;

	private String memberType;

	/* O 普通成员 2 管理员 */
	private Integer grade;

	/* 加入时间 */
	private String joinTime;

	private String showJoinTime;

	/* 发表的主题帖 */
	private int topicCount;

	/* 回复帖子 */
	private int commentCount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
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

	public String getMemberType() {
		return memberType;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
		this.showJoinTime = StringUtils.friendly_time(joinTime);
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getShowJoinTime() {
		return showJoinTime;
	}

	public void setShowJoinTime(String showJoinTime) {
		this.showJoinTime = showJoinTime;
	}

}
