package com.ulewo.entity;

/**
 * @createtime 2010-10-24
 * @author lhl
 * 
 */
public class Group {

	private String id; // 圈子ID

	private String groupName; // 群名称

	private String groupDesc; // 群描述

	private String groupNotice;// 公告

	private String groupIcon; // 群图标

	private String groupHeadIcon; // 群logo

	private String joinPerm; // 群加入权限

	private String createTime; // 创建时间

	private String postPerm; // 发帖权限

	private String isCommend; // 是否推荐

	private Integer visitCount; // 访问数量

	private String isValid; // 是否有效 00 有效 01 无效

	private int members; // 组成员

	private int topicCount; // 主题数量

	private String groupAuthor; // 群作者

	private String authorName; // 作者名

	public String getId() {

		return id;
	}

	public void setId(String id) {

		this.id = id;
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

	public String getGroupIcon() {

		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {

		this.groupIcon = groupIcon;
	}

	public String getGroupHeadIcon() {

		return groupHeadIcon;
	}

	public void setGroupHeadIcon(String groupHeadIcon) {

		this.groupHeadIcon = groupHeadIcon;
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
	}

	public String getPostPerm() {

		return postPerm;
	}

	public void setPostPerm(String postPerm) {

		this.postPerm = postPerm;
	}

	public String getIsCommend() {

		return isCommend;
	}

	public void setIsCommend(String isCommend) {

		this.isCommend = isCommend;
	}

	public Integer getVisitCount() {

		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {

		this.visitCount = visitCount;
	}

	public String getIsValid() {

		return isValid;
	}

	public void setIsValid(String isValid) {

		this.isValid = isValid;
	}

	public int getMembers() {

		return members;
	}

	public void setMembers(int members) {

		this.members = members;
	}

	public int getTopicCount() {

		return topicCount;
	}

	public void setTopicCount(int topicCount) {

		this.topicCount = topicCount;
	}

	public String getGroupAuthor() {

		return groupAuthor;
	}

	public void setGroupAuthor(String groupAuthor) {

		this.groupAuthor = groupAuthor;
	}

	public String getAuthorName() {

		return authorName;
	}

	public void setAuthorName(String authorName) {

		this.authorName = authorName;
	}

	public String getGroupNotice() {
		return groupNotice;
	}

	public void setGroupNotice(String groupNotice) {
		this.groupNotice = groupNotice;
	}

}
