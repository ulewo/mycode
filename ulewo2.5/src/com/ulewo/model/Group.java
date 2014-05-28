package com.ulewo.model;

import java.util.List;

import com.ulewo.util.StringUtils;

/**
 * @createtime 2010-10-24
 * @author lhl
 * 
 */
public class Group {

	private Integer gid; // 圈子ID

	private String groupName; // 群名称

	private String groupDesc; // 群描述

	private String groupNotice;// 公告

	private String groupIcon; // 群图标

	private String groupBanner; // 群logo

	private String joinPerm; // 群加入权限

	private String createTime; // 创建时间

	private String postPerm; // 发帖权限

	private String commendType; // 是否推荐

	private Integer members; // 组成员

	private Integer topicCount; // 主题数量

	private Integer groupUserId; // 群作者

	private String authorName; // 作者名

	private String memberStatus;//Y:是成员  N：已申请没审核   

	private String showCreateTime;

	public List<Topic> topicList;

	public Integer categroyId;

	public Integer pCategroyId;

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getGroupNotice() {
		return groupNotice;
	}

	public void setGroupNotice(String groupNotice) {
		this.groupNotice = groupNotice;
	}

	public String getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	public String getGroupBanner() {
		return groupBanner;
	}

	public void setGroupBanner(String groupBanner) {
		this.groupBanner = groupBanner;
	}

	public String getJoinPerm() {
		return joinPerm;
	}

	public void setJoinPerm(String joinPerm) {
		this.joinPerm = joinPerm;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
		this.showCreateTime = StringUtils.friendly_time(this.createTime);
	}

	public String getPostPerm() {
		return postPerm;
	}

	public void setPostPerm(String postPerm) {
		this.postPerm = postPerm;
	}

	public String getCommendType() {
		return commendType;
	}

	public void setCommendType(String commendType) {
		this.commendType = commendType;
	}

	public Integer getMembers() {
		return members;
	}

	public void setMembers(Integer members) {
		this.members = members;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getGroupUserId() {
		return groupUserId;
	}

	public void setGroupUserId(Integer groupUserId) {
		this.groupUserId = groupUserId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getShowCreateTime() {
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public List<Topic> getTopicList() {
		return topicList;
	}

	public void setTopicList(List<Topic> topicList) {
		this.topicList = topicList;
	}

	public Integer getCategroyId() {
		return categroyId;
	}

	public void setCategroyId(Integer categroyId) {
		this.categroyId = categroyId;
	}

	public Integer getpCategroyId() {
		return pCategroyId;
	}

	public void setpCategroyId(Integer pCategroyId) {
		this.pCategroyId = pCategroyId;
	}

}
