package com.ulewo.model;

import com.ulewo.util.StringUtils;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-5-14 上午11:35:35
 * @version 0.1.0 
 * @copyright ulewo.com 
 */
public class CommonDynamic {
	private Integer userId;
	private String userName;
	private String userIcon;
	private String title;
	private String gid;
	private String summary;
	private int id;
	private String createTime;
	private String showCreateTime;
	private String type;
	private int commments;
	private String images;
	private String[] imagesArray;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
		this.showCreateTime = StringUtils.friendly_time(this.createTime);
	}

	public String getShowCreateTime() {
		return showCreateTime;
	}

	public void setShowCreateTime(String showCreateTime) {
		this.showCreateTime = showCreateTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCommments() {
		return commments;
	}

	public void setCommments(int commments) {
		this.commments = commments;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
		if (StringUtils.isNotEmpty(images)) {
			this.imagesArray = this.images.split("\\|");
		}
	}

	public String[] getImagesArray() {
		return imagesArray;
	}

	public void setImagesArray(String[] imagesArray) {
		this.imagesArray = imagesArray;
	}
}
