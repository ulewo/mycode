package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.BlogItem;
import com.lhl.quan.dao.BlogItemDao;
import com.lhl.quan.service.BlogItemService;

public class BlogItemServiceImpl implements BlogItemService
{

	private BlogItemDao blogItemDao;

	public void setBlogItemDao(BlogItemDao blogItemDao)
	{

		this.blogItemDao = blogItemDao;
	}

	@Override
	public void addItem(BlogItem item)
	{

		blogItemDao.addItem(item);

	}

	@Override
	public boolean update(BlogItem item)
	{

		if (isCurUser(item.getUserId(), item.getId()))
		{
			blogItemDao.update(item);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean delete(String userId, int id)
	{

		if (isCurUser(userId, id))
		{
			blogItemDao.delete(id);
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	public List<BlogItem> queryBlogItemByUserId(String userId) throws Exception
	{

		return blogItemDao.queryItemByUserId(userId);
	}

	@Override
	public List<BlogItem> queryBlogItemAndCountByUserId(String userId) throws Exception
	{

		return blogItemDao.queryBlogItemAndCountByUserId(userId);
	}

	private boolean isCurUser(String userId, int id)
	{

		BlogItem item = blogItemDao.queryBlogItemById(id);
		if (item != null && item.getUserId().equals(userId))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
