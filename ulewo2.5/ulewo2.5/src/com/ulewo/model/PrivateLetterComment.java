package com.ulewo.model;

/**
 * 私信评论
 * 
 * @author luo.hl
 * @date 2013-12-8 下午3:30:47
 * @version 3.0 
 * @copyright www.ulewo.com 
 */
public class PrivateLetterComment {
	private Integer id;
	private Integer letterId;
	private String userId;
	private String createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLetterId() {
		return letterId;
	}
	public void setLetterId(Integer letterId) {
		this.letterId = letterId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
