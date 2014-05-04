package com.ulewo.model;

import com.ulewo.util.StringUtils;

/**
 * 
 * @Title:
 * @Description: 博客评论
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class BlogComment {

    private Integer id;

    private Integer blogId;

    private Integer blogUserId;

    private String content;

    private String simpleContent;

    private String createTime;

    private String showCreateTime;

    private Integer userId;

    private String userName;

    private String userIcon;

    private Integer atUserId;

    private String atUserName;

    private String atUserIcon;

    private String sourceFrom;

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getBlogId() {
	return blogId;
    }

    public void setBlogId(Integer blogId) {
	this.blogId = blogId;
    }

    public Integer getBlogUserId() {
	return blogUserId;
    }

    public void setBlogUserId(Integer blogUserId) {
	this.blogUserId = blogUserId;
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
	this.createTime = createTime;
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

    public String getAtUserIcon() {
	return atUserIcon;
    }

    public void setAtUserIcon(String atUserIcon) {
	this.atUserIcon = atUserIcon;
    }

    public String getSourceFrom() {
	return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
	this.sourceFrom = sourceFrom;
    }

    public String getUserIcon() {
	return userIcon;
    }

    public void setUserIcon(String userIcon) {
	this.userIcon = userIcon;
    }

    public String getShowCreateTime() {
	this.showCreateTime = StringUtils.friendly_time(this.createTime);
	return showCreateTime;
    }

    public void setShowCreateTime(String showCreateTime) {
	this.showCreateTime = showCreateTime;
    }

    public String getSimpleContent() {
	this.simpleContent = StringUtils.clearHtml(this.content);
	return simpleContent;
    }

    public void setSimpleContent(String simpleContent) {
	this.simpleContent = simpleContent;
    }

}
