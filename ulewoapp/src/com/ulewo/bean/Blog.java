package com.ulewo.bean;

import org.json.JSONObject;

public class Blog {
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

	public Blog(JSONObject json) {

		try {
			constructJson(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void constructJson(JSONObject json) throws Exception {

		id = json.getInt("id");
		title = json.getString("title");
		content = json.getString("content");
		postTime = json.getString("postTime") == null ? "" : json.getString(
				"postTime").substring(0, 16);
		reNumber = json.getInt("reCount");
		authorName = json.getString("userName");
		authorId = json.getString("userId");
	}
}
