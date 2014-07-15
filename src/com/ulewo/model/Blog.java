package com.ulewo.model;

import com.ulewo.util.StringUtils;

/**
 * 
 * @Title:
 * @Description: 博客
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class Blog {
	private Integer blogId;

	private Integer userId;

	private String userIcon;

	private String userName;

	private Integer categoryId;

	private String categoryName;

	private String title;

	private String summary;

	private String content;

	private Integer readCount;

	private Integer commentCount;

	private String createTime;

	private String showCreateTime;

	private String keyWord;

	private String allowComment;

	private String blogImage;

	private String blogImageSmall; // 缩略图

	private String[] images;

	private boolean isNewBlog;

	private Integer likeCount;

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		if (StringUtils.isEmpty(categoryName)) {
			return "全部文章";
		}
		return categoryName;
	}

	public void setCategoryName(String categoryName) {

		this.categoryName = categoryName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.showCreateTime = StringUtils.friendly_time(createTime);
		this.createTime = createTime;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getAllowComment() {
		return allowComment;
	}

	public void setAllowComment(String allowComment) {
		this.allowComment = allowComment;
	}

	public String getBlogImage() {
		return blogImage;
	}

	public void setBlogImage(String blogImage) {
		this.blogImage = blogImage;
	}

	public String getShowCreateTime() {
		this.showCreateTime = StringUtils.friendly_time(this.createTime);
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public boolean isNewBlog() {
		return isNewBlog;
	}

	public void setNewBlog(boolean isNewBlog) {
		this.isNewBlog = isNewBlog;
	}

	public String getBlogImageSmall() {
		return blogImageSmall;
	}

	public void setBlogImageSmall(String blogImageSmall) {
		this.blogImageSmall = blogImageSmall;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

}
