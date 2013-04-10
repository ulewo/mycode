package com.doov.entity;

import java.util.List;

/**
 * 
 * @Title:
 * @Description: 公告
 * @author luohl
 * @date 2013-4-10
 * @version V1.0
 */
public class Notice {
	private int id;

	private String content;

	private int code;

	private String thumbnailUrl;

	private String createTime;

	private List<Attachment> attatches;

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

	public int getCode() {

		return code;
	}

	public void setCode(int code) {

		this.code = code;
	}

	public String getThumbnailUrl() {

		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {

		this.thumbnailUrl = thumbnailUrl;
	}

	public String getCreateTime() {

		return createTime;
	}

	public void setCreateTime(String createTime) {

		this.createTime = createTime;
	}

	public List<Attachment> getAttatches() {

		return attatches;
	}

	public void setAttatches(List<Attachment> attatches) {

		this.attatches = attatches;
	}

}
