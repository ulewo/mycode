package com.ulewo.model;

/**
 * TODO: 增加描述
 * 
 * @author luo.hl
 * @date 2014-5-20 下午3:54:30
 * @version 0.1.0 
 * @copyright yougou.com 
 */
public class GroupCategory {
	private Integer groupCategoryId;
	private Integer pid;
	private String name;
	private Integer rang;

	public Integer getGroupCategoryId() {
		return groupCategoryId;
	}

	public void setGroupCategoryId(Integer groupCategoryId) {
		this.groupCategoryId = groupCategoryId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

}
