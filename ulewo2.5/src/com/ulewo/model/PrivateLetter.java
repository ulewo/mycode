package com.ulewo.model;

/**
 * 私信
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:29:23
 * @version 3.0 
 * @copyright www.ulewo.com 
 */
public class PrivateLetter {
	private Integer letterId;
	private Integer sendUserId;
	private Integer receiveUserId;
	private String content;
	private String createTime;
	public Integer getLetterId() {
		return letterId;
	}
	public void setLetterId(Integer letterId) {
		this.letterId = letterId;
	}
	public Integer getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(Integer sendUserId) {
		this.sendUserId = sendUserId;
	}
	public Integer getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(Integer receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
