package com.ulewo.entity;

public class Favorite {
	private String userId;

	private int articleId;

	private String title;

	private String type; //A 帖子文章  B 博客

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
	}

	public int getArticleId() {

		return articleId;
	}

	public void setArticleId(int articleId) {

		this.articleId = articleId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getType() {

		return type;
	}

	public void setType(String type) {

		this.type = type;
	}

}
