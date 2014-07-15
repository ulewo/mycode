package com.ulewo.vo;

/**
 * @createtime 2010-10-24
 * @author lhl
 * 
 */
public class GroupVo {

	private Integer gid; // 圈子ID

	private String groupName; // 群名称

	private String groupIcon; // 群图标

	private Integer memberCount; // 组成员

	private Integer topicCount; // 主题数量

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

	public String getGroupIcon() {
		return groupIcon;
	}

	public void setGroupIcon(String groupIcon) {
		this.groupIcon = groupIcon;
	}

	public Integer getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

}
