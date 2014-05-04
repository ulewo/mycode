package com.ulewo.model;

public class Notice {
    private int id;

    private Integer receivedUserId;

    private int type;

    private String title;

    private String url;

    private String createTime;

    private String status;

    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    public int getType() {

	return type;
    }

    public void setType(int type) {

	this.type = type;
    }

    public String getStatus() {

	return status;
    }

    public void setStatus(String status) {

	this.status = status;
    }

    public String getCreateTime() {
	return createTime;
    }

    public void setCreateTime(String createTime) {
	this.createTime = createTime;
    }

    public Integer getReceivedUserId() {
	return receivedUserId;
    }

    public void setReceivedUserId(Integer receivedUserId) {
	this.receivedUserId = receivedUserId;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

}
