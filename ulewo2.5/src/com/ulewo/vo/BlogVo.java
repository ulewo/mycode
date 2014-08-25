package com.ulewo.vo;

public class BlogVo {
	private Integer blogId;

	private String title;

	private String content;

	private String createTime;

	private Integer readCount;

	private Integer commentCount;

	private Integer lickCount;

	private boolean like; // 是否已经赞

	private boolean collection; // 是否已经收藏

	private Integer collectionCount;

	private String userName;

	private Integer userId;

	private String userIcon;

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getLickCount() {
		return lickCount;
	}

	public void setLickCount(Integer lickCount) {
		this.lickCount = lickCount;
	}

	public Integer getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(Integer collectionCount) {
		this.collectionCount = collectionCount;
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

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean isLike) {
		this.like = isLike;
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean isCollection) {
		this.collection = isCollection;
	}

}
