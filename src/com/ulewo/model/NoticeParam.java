package com.ulewo.model;

import java.util.ArrayList;
import java.util.List;

import com.ulewo.enums.NoticeType;

public class NoticeParam {

    // 消息类型
    private NoticeType noticeType;

    // 文章ID
    private Integer articleId;

    // 接受消息的人
    private Integer receivedUserId;

    // 被at的人，也是接受消息的人。
    private List<Integer> atUserIds = new ArrayList<Integer>();

    // 发送消息的人
    private Integer sendUserId;

    // 回复的ID
    private Integer commentId;

    public NoticeType getNoticeType() {
	return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
	this.noticeType = noticeType;
    }

    public Integer getArticleId() {
	return articleId;
    }

    public void setArticleId(Integer articleId) {
	this.articleId = articleId;
    }

    public Integer getReceivedUserId() {
	return receivedUserId;
    }

    public void setReceivedUserId(Integer receivedUserId) {
	this.receivedUserId = receivedUserId;
    }

    public List<Integer> getAtUserIds() {
	return atUserIds;
    }

    public void setAtUserIds(List<Integer> atUserIds) {
	this.atUserIds = atUserIds;
    }

    public Integer getSendUserId() {
	return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
	this.sendUserId = sendUserId;
    }

    public Integer getCommentId() {
	return commentId;
    }

    public void setCommentId(Integer commentId) {
	this.commentId = commentId;
    }

}
