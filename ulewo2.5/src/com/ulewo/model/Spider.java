package com.ulewo.model;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-6-4 上午9:47:48
 * @version 0.1.0 
 * @copyright 
 */
public class Spider {
	private String id;
	private String title;
	private String content;
	private String type;
	private String images;
	private String imagesSmall;
	private String createTime;
	private String status; // 初始 0  已经发布1

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getImagesSmall() {
		return imagesSmall;
	}

	public void setImagesSmall(String imagesSmall) {
		this.imagesSmall = imagesSmall;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
