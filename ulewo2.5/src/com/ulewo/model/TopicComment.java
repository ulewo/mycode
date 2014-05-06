package com.ulewo.model;

import java.util.ArrayList;
import java.util.List;

import com.ulewo.util.StringUtils;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class TopicComment {
    private Integer id;

    private Integer pid;

    private Integer topicId; // 主题帖Id

    private Integer gid;

    private Integer userId;

    private Integer atUserId;

    private String createTime;

    private String content;

    private String simpleContent;

    private String showCreateTime;

    private String sourceFrom;

    private String userName;

    private String userIcon;

    private String atUserName;

    private String atUserIcon;

    private List<TopicComment> childList = new ArrayList<TopicComment>();

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getPid() {
	return pid;
    }

    public void setPid(Integer pid) {
	this.pid = pid;
    }

    public Integer getTopicId() {
	return topicId;
    }

    public void setTopicId(Integer topicId) {
	this.topicId = topicId;
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

    public Integer getAtUserId() {
	return atUserId;
    }

    public void setAtUserId(Integer atUserId) {
	this.atUserId = atUserId;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.createTime = createTime;
    }

    public String getSourceFrom() {
	return sourceFrom;
    }

    public void setSourceFrom(String sourceFrom) {
	this.sourceFrom = sourceFrom;
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

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
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

    public List<TopicComment> getChildList() {
	return childList;
    }

    public void setChildList(List<TopicComment> childList) {
	this.childList = childList;
    }

}
