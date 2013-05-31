package com.ulewo.entity;

import java.util.List;

public class Message {

	private int id;

	private String userId;

	private String reUserId;

	private String reUserName;

	private String atUserId;

	private String atUserName;

	private String reUserIcon;

	private String message;

	private String postTime;

	private String quote;

	public String getQuote() {

		return quote;
	}

	public void setQuote(String quote) {

		this.quote = quote;
	}

	private List<Message> reMessages;

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

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public String getPostTime() {

		return postTime;
	}

	public void setPostTime(String postTime) {

		this.postTime = postTime;
	}

	public List<Message> getReMessages() {

		return reMessages;
	}

	public void setReMessages(List<Message> reMessages) {

		this.reMessages = reMessages;
	}

	public String getReUserId() {

		return reUserId;
	}

	public void setReUserId(String reUserId) {

		this.reUserId = reUserId;
	}

	public String getReUserName() {

		return reUserName;
	}

	public void setReUserName(String reUserName) {

		this.reUserName = reUserName;
	}

	public String getReUserIcon() {

		return reUserIcon;
	}

	public void setReUserIcon(String reUserIcon) {

		this.reUserIcon = reUserIcon;
	}

	public String getAtUserId() {
		return atUserId;
	}

	public void setAtUserId(String atUserId) {
		this.atUserId = atUserId;
	}

	public String getAtUserName() {
		return atUserName;
	}

	public void setAtUserName(String atUserName) {
		this.atUserName = atUserName;
	}

}
