package com.ulewo.model;

/**
 * @createtime 2010-10-24
 * @author lhl
 * 
 */
public class TopicCategory {
    private Integer categoryId; // id

    private Integer gid; // 群ID

    private String name; // 分类名称

    private Integer rang; // 栏目编号

    private int topicCount;

    private String allowPost; // 是否允许发帖

    public Integer getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
	this.categoryId = categoryId;
    }

    public Integer getGid() {
	return gid;
    }

    public void setGid(Integer gid) {
	this.gid = gid;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getRang() {
	return rang;
    }

    public void setRang(Integer rang) {
	this.rang = rang;
    }

    public int getTopicCount() {
	return topicCount;
    }

    public void setTopicCount(int topicCount) {
	this.topicCount = topicCount;
    }

    public String getAllowPost() {
	return allowPost;
    }

    public void setAllowPost(String allowPost) {
	this.allowPost = allowPost;
    }

}
