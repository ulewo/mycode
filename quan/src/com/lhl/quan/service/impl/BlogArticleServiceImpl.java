package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.service.BlogArticleService;

public class BlogArticleServiceImpl implements BlogArticleService
{
	private BlogArticleDao blogArticleDao;

	public void setBlogArticleDao(BlogArticleDao blogArticleDao)
	{

		this.blogArticleDao = blogArticleDao;
	}

	@Override
	public boolean addBlog(BlogArticle blogArticle)
	{

		try
		{
			blogArticleDao.addBlog(blogArticle);
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public BlogArticle queryBlogById(int id)
	{

		return blogArticleDao.queryBlogById(id);
	}

	@Override
	public boolean update(BlogArticle blogArticle)
	{

		if (isCurUser(blogArticle.getUserId(), blogArticle.getId()))
		{
			blogArticleDao.update(blogArticle);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void updateReadCount(BlogArticle blogArticle)
	{

		blogArticleDao.updateReadCount(blogArticle);
	}

	@Override
	public boolean deleteArticle(String userId, int id)
	{

		if (isCurUser(userId, id))
		{
			blogArticleDao.delete(id);
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	public List<BlogArticle> queryBlogByUserId(String userId, int offset, int total)
	{

		return blogArticleDao.queryBlogByUserId(userId, offset, total);
	}

	@Override
	public int queryCountByUserId(String userId)
	{

		return blogArticleDao.queryCountByUserId(userId);
	}

	private boolean isCurUser(String userId, int id)
	{

		BlogArticle article = blogArticleDao.queryBlogById(id);
		if (article != null && article.getUserId().equals(userId))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
