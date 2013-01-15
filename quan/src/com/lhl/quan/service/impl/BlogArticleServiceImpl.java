package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.entity.BlogArticle;
import com.lhl.quan.dao.BlogArticleDao;
import com.lhl.quan.service.BlogArticleService;
import com.lhl.util.Constant;
import com.lhl.util.Tools;

public class BlogArticleServiceImpl implements BlogArticleService
{
	private BlogArticleDao blogArticleDao;

	public void setBlogArticleDao(BlogArticleDao blogArticleDao)
	{

		this.blogArticleDao = blogArticleDao;
	}

	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean addBlog(BlogArticle blogArticle)
	{

		try
		{
			String summary = Tools.clearHtml(blogArticle.getContent());
			int length = Tools.getRealLength(summary);
			if (length > Constant.commentLength400)
			{
				summary = summary.substring(0, Constant.commentLength400);
			}
			blogArticle.setSummary(summary);
			blogArticle.setPostTime(format.format(new Date()));
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
	public void deleteArticle(int id)
	{

		blogArticleDao.delete(id);

	}

	@Override
	public List<BlogArticle> queryBlogByUserIdOrItem(String userId, int itemId, int offset, int total)
	{

		if (itemId == 0)
		{
			return blogArticleDao.queryBlogByUserId(userId, offset, total);
		}
		else
		{
			return blogArticleDao.queryBlogByItemId(itemId, offset, total);
		}

	}

	@Override
	public int queryCountByUserIdOrItem(String userId, int itemId)
	{

		if (itemId == 0)
		{
			return blogArticleDao.queryCountByUserId(userId);
		}
		else
		{
			return blogArticleDao.queryCountByItemId(itemId);
		}

	}

	private boolean isCurUser(String userId, int id)
	{

		BlogArticle article = blogArticleDao.queryBlogById(id);
		if (article != null && (article.getUserId().equals(userId)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
