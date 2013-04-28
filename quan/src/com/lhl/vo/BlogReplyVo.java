package com.lhl.vo;

public class BlogReplyVo {
	private int id;

	private int articleId; // 主题帖Id

	private String content; // 回复内容

	private String authorIcon;// 回复者图像

	private String authorid; // 回复作者

	private String authorName; // 名称

	private String reTime; // 回复时间

	private String sourceFrom;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorIcon() {
		return authorIcon;
	}

	public void setAuthorIcon(String authorIcon) {
		this.authorIcon = authorIcon;
	}

	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getReTime() {
		return reTime;
	}

	public void setReTime(String reTime) {
		this.reTime = reTime;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}
}
