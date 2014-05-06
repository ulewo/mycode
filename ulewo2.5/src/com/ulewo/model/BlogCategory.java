package com.ulewo.model;

/**
 * 
 * @Title:
 * @Description: 博客分类
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class BlogCategory {
	private Integer categoryId;

	private Integer userId;

	private String name;

	private Integer rang;

	private Integer blogCount;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRang() {
		return rang;
	}

	public void setRang(Integer rang) {
		this.rang = rang;
	}

	public Integer getBlogCount() {
		return blogCount;
	}

	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}

}
