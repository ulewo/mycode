package com.lhl.blog.service;

import java.util.List;

import com.lhl.blog.dao.ItemDao;
import com.lhl.blog.entity.Item;

public class ItemService
{
	private ItemService()
	{

	}

	private static ItemService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ItemService getInstance()
	{

		if (instance == null)
		{
			instance = new ItemService();
		}
		return instance;
	}

	public void saveItem(Item item)
	{

		if (item.getId() == 0)
		{
			ItemDao.getInstance().addItem(item);
		}
		else
		{
			ItemDao.getInstance().updateItem(item);
		}

	}

	public void deleteItem(int id)
	{

		ItemDao.getInstance().deleteItem(id);
	}

	public void updateItem(Item item)
	{

		ItemDao.getInstance().updateItem(item);
	}

	public List<Item> queryItems()
	{

		return ItemDao.getInstance().queryItems();
	}
}
