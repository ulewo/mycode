package com.ulewo.entity;

public class AttachedFile {
	private int id;

	private int articleId;

	private String gid;

	private String fileName;

	private String fileUrl;

	private String fileType;

	private int mark;

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

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public String getFileName() {

		return fileName;
	}

	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	public String getFileUrl() {

		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {

		this.fileUrl = fileUrl;
	}

	public String getFileType() {

		return fileType;
	}

	public void setFileType(String fileType) {

		this.fileType = fileType;
	}

	public int getMark() {

		return mark;
	}

	public void setMark(int mark) {

		this.mark = mark;
	}

}
