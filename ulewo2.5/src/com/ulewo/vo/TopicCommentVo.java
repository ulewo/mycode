package com.ulewo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:
 * @Description: 回复贴
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class TopicCommentVo {
	private Integer id;

	private Integer pid;

	private Integer topicId; // 主题帖Id

	private Integer gid;

	private String createTime;

	private String content;

	private String userName;

	private Integer userId;

	private String userIcon;

	private Integer atUserId;

	private String atUserName;

	private String atUserIcon;

	private List<TopicCommentVo> childList = new ArrayList<TopicCommentVo>();

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
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

	public List<TopicCommentVo> getChildList() {
		return childList;
	}

	public void setChildList(List<TopicCommentVo> childList) {
		this.childList = childList;
	}

}
