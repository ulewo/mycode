package com.lhl.entity;

/**
 * 
 * @Title:
 * @Description: 博客分类
 * @author luohl
 * @date 2013-1-4
 * @version V1.0
 */
public class BlogReArticle
{
	private int id;

	private String userId;

	private String itemName;

	private int itemRang;

	public int getId()
	{

		return id;
	}

	public void setId(int id)
	{

		this.id = id;
	}

	public String getUserId()
	{

		return userId;
	}

	public void setUserId(String userId)
	{

		this.userId = userId;
	}

	public String getItemName()
	{

		return itemName;
	}

	public void setItemName(String itemName)
	{

		this.itemName = itemName;
	}

	public int getItemRang()
	{

		return itemRang;
	}

	public void setItemRang(int itemRang)
	{

		this.itemRang = itemRang;
	}

}
