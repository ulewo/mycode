package com.lhl.entity;

import java.util.ArrayList;
import java.util.List;

public class Article {
	private int id;

	/** 内容 **/
	private String content;

	/** 发布时间 **/
	private String postTime;

	/** 文章来源 Q:糗事百科 H:haha.mx P:捧腹网 A:用户发布 **/
	private String sourceFrom;

	/** 爬到的文章的ID 由来源+id **/
	private String sourceId;

	/** 抓取的文章的时间 **/
	private String sourceTime;

	/** 用户ID **/
	private String uid;

	/** 用户名 **/
	private String userName;

	/** 用户的图像地址 **/
	private String avatar;

	/** 状态 0:已经审核 1:未审核 **/
	private String status;

	/** 图片地址 **/
	private String imgUrl;

	/** 视频地址 **/
	private String videoUrl;

	/** 媒体类型 **/
	private String medioType; // m：视频 p:图片

	/** 标签 **/
	private String tag;

	/** 标签集合 **/
	private List<String> tags = new ArrayList<String>();

	/** 顶 **/
	private int up;

	/** 踩 **/
	private int down;

	/** 是否已经顶或者踩 **/
	private boolean haveOper;

	/** 回复条数 **/
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

	public String getSourceFrom() {

		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {

		this.sourceFrom = sourceFrom;
	}

	public String getSourceTime() {

		return sourceTime;
	}

	public void setSourceTime(String sourceTime) {

		this.sourceTime = sourceTime;
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

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getImgUrl() {

		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {

		this.imgUrl = imgUrl;
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

	public String getAvatar() {

		return avatar;
	}

	public void setAvatar(String avatar) {

		this.avatar = avatar;
	}

	public List<String> getTags() {

		return tags;
	}

	public void setTags(List<String> tags) {

		this.tags = tags;
	}

	public boolean isHaveOper() {

		return haveOper;
	}

	public void setHaveOper(boolean haveOper) {

		this.haveOper = haveOper;
	}

	public String getTag() {

		return tag;
	}

	public void setTag(String tag) {

		this.tag = tag;
	}

	public String getVideoUrl() {

		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {

		this.videoUrl = videoUrl;
	}

	public int getReCount() {

		return reCount;
	}

	public void setReCount(int reCount) {

		this.reCount = reCount;
	}

	public String getSourceId() {

		return sourceId;
	}

	public void setSourceId(String sourceId) {

		this.sourceId = sourceId;
	}

	public String getMedioType() {
		return medioType;
	}

	public void setMedioType(String medioType) {
		this.medioType = medioType;
	}

}
