package com.ulewo.vo;

/**
 * 
 * @Title:
 * @Description: 主题文章bean
 * @author luohl
 * @date 2012-3-30
 * @version V1.0
 */
public class TopicVo {
	private Integer topicId; // id

	private Integer gid; // 组id

	private String title; // 标题

	private String content; // 内容

	private Integer userId; // 作者

	private String userName;

	private String userIcon;

	private String summary;

	private String createTime; // 发布时间

	private int readCount; // 阅读次数

	private int commentCount; // 回复数量

	private int lickCount; // 赞数量

	private boolean isLike; // 是否已经赞

	private int collectionCount;// 收藏数量

	private boolean isCollection; // 是否已经收藏

	private String[] topicImages; // 缩略图

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getLickCount() {
		return lickCount;
	}

	public void setLickCount(int lickCount) {
		this.lickCount = lickCount;
	}

	public int getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}

	public String[] getTopicImages() {
		return topicImages;
	}

	public void setTopicImages(String[] topicImages) {
		this.topicImages = topicImages;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

}
