package com.lhl.vo;

import java.util.ArrayList;
import java.util.List;

public class ArticleVo {
	private int id;

	private String content;

	private String commend;

	private String postTime;

	private String uid;

	private String userName;

	private String avatar;

	private String imgUrl;

	/** 视频地址 **/
	private String videoUrl;

	/** 媒体类型 **/
	private String medioType; // m：视频 p:图片

	private List<String> tags = new ArrayList<String>();

	private int up;

	private int down;

	private boolean haveOper;

	private int reCount;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
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

	public String getUid() {

		return uid;
	}

	public void setUid(String uid) {

		this.uid = uid;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getAvatar() {

		return avatar;
	}

	public void setAvatar(String avatar) {

		this.avatar = avatar;
	}

	public String getImgUrl() {

		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {

		this.imgUrl = imgUrl;
	}

	public List<String> getTags() {

		return tags;
	}

	public void setTags(List<String> tags) {

		this.tags = tags;
	}

	public int getUp() {

		return up;
	}

	public void setUp(int up) {

		this.up = up;
	}

	public int getDown() {

		return down;
	}

	public void setDown(int down) {

		this.down = down;
	}

	public boolean isHaveOper() {

		return haveOper;
	}

	public void setHaveOper(boolean haveOper) {

		this.haveOper = haveOper;
	}

	public int getReCount() {

		return reCount;
	}

	public void setReCount(int reCount) {

		this.reCount = reCount;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getMedioType() {
		return medioType;
	}

	public void setMedioType(String medioType) {
		this.medioType = medioType;
	}

	public String getCommend() {
		return commend;
	}

	public void setCommend(String commend) {
		this.commend = commend;
	}

}
