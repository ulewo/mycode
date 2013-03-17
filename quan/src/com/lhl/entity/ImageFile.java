package com.lhl.entity;

public class ImageFile {
	// 是否是文件夹
	private boolean isDirectory;
	// 文件路径
	private String filePath;
	// 文件名称
	private String fileName;

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
