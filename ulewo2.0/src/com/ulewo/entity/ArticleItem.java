package com.ulewo.entity;

/**
 * @createtime  2010-10-24
 * @author lhl
 *
 */
public class ArticleItem {
	private int id; //id

	private String gid; //群ID

	private String itemName; //分类名称

	private int itemCode; //栏目编号

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getGid() {

		return gid;
	}

	public void setGid(String gid) {

		this.gid = gid;
	}

	public String getItemName() {

		return itemName;
	}

	public void setItemName(String itemName) {

		this.itemName = itemName;
	}

	public int getItemCode() {

		return itemCode;
	}

	public void setItemCode(int itemCode) {

		this.itemCode = itemCode;
	}

}
