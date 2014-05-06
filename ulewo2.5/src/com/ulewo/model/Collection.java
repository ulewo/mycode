package com.ulewo.model;

public class Collection {
    private Integer userId;

    private Integer articleId;

    private String title;

    private String gid;

    private String articleType; // A 帖子文章 B 博客

    private Integer collectionCount;

    private boolean haveCollection;

    public Integer getUserId() {
	return userId;
    }

    public void setUserId(Integer userId) {
	this.userId = userId;
    }

    public Integer getArticleId() {
	return articleId;
    }

    public void setArticleId(Integer articleId) {
	this.articleId = articleId;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getArticleType() {
	return articleType;
    }

    public void setArticleType(String articleType) {
	this.articleType = articleType;
    }

    public Integer getCollectionCount() {
	return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
	this.collectionCount = collectionCount;
    }

    public boolean isHaveCollection() {
	return haveCollection;
    }

    public void setHaveCollection(boolean haveCollection) {
	this.haveCollection = haveCollection;
    }

    public String getGid() {
	return gid;
    }

    public void setGid(String gid) {
	this.gid = gid;
    }

}
