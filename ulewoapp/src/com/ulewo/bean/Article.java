package com.ulewo.bean;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.ulewo.AppException;
import com.ulewo.util.StringUtils;

public class Article implements Serializable {
	private static final long serialVersionUID = 2393143064912211800L;

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

	public static Article parse(JSONObject jsonobj) throws AppException {

		try {
			JSONObject obj = null;
			if (!jsonobj.isNull("article")) {
				obj = new JSONObject(jsonobj.getString("article"));
			}
			else {
				obj = jsonobj;
			}
			Article article = new Article();
			article.setId(obj.getInt("id"));
			article.setTitle(obj.getString("title"));
			article.setAuthorId(obj.getString("authorId"));
			article.setAuthorName(obj.getString("authorName"));
			article.setPostTime(StringUtils.friendly_time(obj.getString("postTime")));
			article.setReNumber(obj.getInt("reNumber"));
			article.setContent(obj.getString("content"));
			article.setReadNumber(obj.getInt("readNumber"));
			return article;
		}
		catch (JSONException e) {
			throw AppException.josn(e);
		}
	}
}
