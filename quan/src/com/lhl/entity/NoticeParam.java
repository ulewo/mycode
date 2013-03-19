package com.lhl.entity;

import com.lhl.enums.NoticeType;

public class NoticeParam {
	private NoticeType noticeType;

	private int articleId;

	private String userId;

	private String userName;

	private String reUserId;

	private String reUserName;

	public NoticeType getNoticeType() {

		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {

		this.noticeType = noticeType;
	}

	public int getArticleId() {

		return articleId;
	}

	public void setArticleId(int articleId) {

		this.articleId = articleId;
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

	public String getReUserId() {

		return reUserId;
	}

	public void setReUserId(String reUserId) {

		this.reUserId = reUserId;
	}

	public String getReUserName() {

		return reUserName;
	}

	public void setReUserName(String reUserName) {

		this.reUserName = reUserName;
	}

}
