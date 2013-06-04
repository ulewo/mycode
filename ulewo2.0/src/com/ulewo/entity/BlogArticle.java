package com.ulewo.entity;

/**
 * 
 * @Title:
 * @Description: 博客
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class BlogArticle {
	private int id;

	private String userId;

	private String userName;

	private Integer itemId;

	private String title;

	private String summary;

	private String content;

	private Integer readCount;

	private Integer reCount;

	private String postTime;

	private String keyWord;

	private String itemName;

	private int allowReplay; //0 所有人可评论   2 禁止评论 

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getUserId() {

		return userId;
	}

	public void setUserId(String userId) {

		this.userId = userId;
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

	public void setContent(String conent) {

		this.content = conent;
	}

	public String getPostTime() {

		return postTime;
	}

	public void setPostTime(String postTime) {

		this.postTime = postTime;
	}

	public String getKeyWord() {

		return keyWord;
	}

	public void setKeyWord(String keyWord) {

		this.keyWord = keyWord;
	}

	public int getAllowReplay() {

		return allowReplay;
	}

	public void setAllowReplay(int allowReplay) {

		this.allowReplay = allowReplay;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public Integer getItemId() {

		return itemId;
	}

	public void setItemId(Integer itemId) {

		this.itemId = itemId;
	}

	public Integer getReadCount() {

		return readCount;
	}

	public void setReadCount(Integer readCount) {

		this.readCount = readCount;
	}

	public Integer getReCount() {

		return reCount;
	}

	public void setReCount(Integer reCount) {

		this.reCount = reCount;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


}
