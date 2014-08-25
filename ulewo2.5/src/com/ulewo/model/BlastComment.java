package com.ulewo.model;

import com.ulewo.util.StringUtils;

public class BlastComment {
    private Integer id;

    private Integer blastId;

    private Integer userId;

    private String userName;

    private String userIcon;

    private String content;

    private String createTime;

    private String showCreateTime;

    private Integer atUserId;

    private String atUserName;

    private String sourceFrom;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

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

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.showCreateTime = StringUtils.friendly_time(createTime);
	this.createTime = createTime;
    }

    public Integer getAtUserId() {
	return atUserId;
    }

    public void setAtUserId(Integer atUserId) {
	this.atUserId = atUserId;
    }

    public String getAtUserName() {
	return atUserName;
    }

    public void setAtUserName(String atUserName) {
	this.atUserName = atUserName;
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
