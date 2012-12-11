package com.lhl.blog.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lhl.blog.dao.ReArticleDao;
import com.lhl.blog.entity.ReArticle;
import com.lhl.blog.util.StringUtil;

public class ReArticleService
{
	private ReArticleService()
	{

	}

	private static ReArticleService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ReArticleService getInstance()
	{

		if (instance == null)
		{
			instance = new ReArticleService();
		}
		return instance;
	}

	public ReArticle addReArticle(ReArticle reArticle)
	{

		String userName = reArticle.getUserName();
		reArticle.setUserName(StringUtil.formateHtml(userName));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		reArticle.setPostTime(format.format(new Date()));
		ReArticleDao.getInstance().addReArticle(reArticle);
		return reArticle;
	}

	public void deleteReArticle(int id)
	{

		ReArticleDao.getInstance().deleteReArticle(id);
	}

	public List<ReArticle> queryReArticles(int noStart, int pageSize)
	{

		return ReArticleDao.getInstance().queryReArticles(noStart, pageSize);
	}

	public int queryCount()
	{

		return ReArticleDao.getInstance().queryCount();
	}

	public List<ReArticle> queryReArticlesByArticleId(String articleId)
	{

		return ReArticleDao.getInstance().queryReArticlesByArticleId(articleId);
	}

}
