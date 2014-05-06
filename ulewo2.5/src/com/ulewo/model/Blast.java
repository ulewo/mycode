package com.ulewo.model;

import com.ulewo.util.StringUtils;

public class Blast {
    private Integer blastId;

    private Integer userId;

    private String userName;

    private String userIcon;

    private String content;

    private String imageUrl;

    private String createTime;

    private String showCreateTime;

    private Integer commentCount;

    private String sourceFrom;

    public Integer getBlastId() {
	return blastId;
    }

    public void setBlastId(Integer blastId) {
	this.blastId = blastId;
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

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    public String getImageUrl() {
	return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.showCreateTime = StringUtils.friendly_time(createTime);
	this.createTime = createTime;
    }

    public Integer getCommentCount() {
	return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
	this.commentCount = commentCount;
    }

    public String getSourceFrom() {
	return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
	this.sourceFrom = sourceFrom;
    }

    public String getShowCreateTime() {
	return showCreateTime;
    }

    public void setShowCreateTime(String showCreateTime) {
	this.showCreateTime = showCreateTime;
    }
}
