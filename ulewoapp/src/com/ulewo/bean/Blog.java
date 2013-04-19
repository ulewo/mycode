package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;

public class Blog implements Serializable {
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

	public static Blog parse(JSONObject jsonobj) throws AppException {

		try {
			JSONObject obj = null;
			if (!jsonobj.isNull("blog")) {
				obj = new JSONObject(jsonobj.getString("blog"));
			}
			else {
				obj = jsonobj;
			}
			Blog blog = new Blog();
			blog.setId(obj.getInt("id"));
			blog.setTitle(obj.getString("title"));
			blog.setAuthorId(obj.getString("authorId"));
			blog.setAuthorName(obj.getString("authorName"));
			blog.setPostTime(obj.getString("postTime"));
			blog.setReNumber(obj.getInt("reNumber"));
			blog.setReadNumber(obj.getInt("readNumber"));
			return blog;
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
