package com.doov.entity;

/**
 * 
 * @Title:
 * @Description: 图片附件
 * @author luohl
 * @date 2013-4-10
 * @version V1.0
 */
public class Attachment {
	private String articleId;

	private String imageUrl;

	private String articleType;

	public String getArticleId() {

		return articleId;
	}

	public void setArticleId(String articleId) {

		this.articleId = articleId;
	}

	public String getImageUrl() {

		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {

		this.imageUrl = imageUrl;
	}

	public String getArticleType() {

		return articleType;
	}

	public void setArticleType(String articleType) {

		this.articleType = articleType;
	}

}
