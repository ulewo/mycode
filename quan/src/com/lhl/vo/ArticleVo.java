package com.lhl.vo;

public class ArticleVo {
	private int id;

	private String title;

	private String content;

	private String postTime;

	private int reNumber;

	private int readNumber;

	private String authorName;

	private String authorId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public int getReNumber() {
		return reNumber;
	}

	public void setReNumber(int reNumber) {
		this.reNumber = reNumber;
	}

	public int getReadNumber() {
		return readNumber;
	}

	public void setReadNumber(int readNumber) {
		this.readNumber = readNumber;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
}
