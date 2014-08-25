package com.ulewo.vo;

public class BlogCommentVo {
	private Integer id;

	private Integer blogId; // 主题帖Id

	private String content; // 回复内容

	private Integer userId;// 回复者图像

	private String userName; // 回复作者

	private String userIcon; // 名称

	private Integer atUserId;

	private String atUserName;

	private String createTime;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
