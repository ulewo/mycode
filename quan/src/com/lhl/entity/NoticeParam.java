package com.lhl.entity;

import java.util.ArrayList;
import java.util.List;

import com.lhl.enums.NoticeType;

public class NoticeParam {

	//消息类型
	private NoticeType noticeType;

	//文章ID
	private int articleId;

	//接受消息的人
	private String receiveUserId;

	//被at的人，也是接受消息的人。
	private List<String> atUserIds = new ArrayList<String>();

	//发送消息的人
	private String sendUserId;

	private int reId;

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

	public int getReId() {

		return reId;
	}

	public void setReId(int reId) {

		this.reId = reId;
	}

	public List<String> getAtUserIds() {

		return atUserIds;
	}

	public void setAtUserIds(List<String> atUserIds) {

		this.atUserIds = atUserIds;
	}

	public String getReceiveUserId() {

		return receiveUserId;
	}

	public void setReceiveUserId(String receiveUserId) {

		this.receiveUserId = receiveUserId;
	}

	public String getSendUserId() {

		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {

		this.sendUserId = sendUserId;
	}

}
