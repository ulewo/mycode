package com.ulewo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulewo.dao.BlogItemDao;
import com.ulewo.entity.BlogItem;
import com.ulewo.service.BlogItemService;

@Service("blogItemService")
public class BlogItemServiceImpl implements BlogItemService {
	@Autowired
	private BlogItemDao blogItemDao;

	public void setBlogItemDao(BlogItemDao blogItemDao) {

		this.blogItemDao = blogItemDao;
	}

	@Override
	public int saveItem(BlogItem item) {

		int id = item.getId();
		if (item.getId() != 0) {
			blogItemDao.update(item);
		}
		else {
			id = blogItemDao.addItem(item);
		}
		return id;
	}

	@Override
	public boolean delete(String userId, int id) {

		if (isCurUser(userId, id)) {
			blogItemDao.delete(id);
			return true;
		}
		else {
			return false;
		}

	}

	@Override
	public List<BlogItem> queryBlogItemByUserId(String userId) {

		return blogItemDao.queryItemByUserId(userId);
	}

	@Override
	public List<BlogItem> queryBlogItemAndCountByUserId(String userId) {

		return blogItemDao.queryBlogItemAndCountByUserId(userId);
	}

	private boolean isCurUser(String userId, int id) {

		BlogItem item = blogItemDao.queryBlogItemById(id);
		if (item != null && item.getUserId().equals(userId)) {
			return true;
		}
		else {
			return false;
		}
	}

	public BlogItem queryBlogItemById(int itemId) {

		return blogItemDao.queryBlogItemById(itemId);
	}

}
