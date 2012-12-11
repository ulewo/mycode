package com.lhl.blog.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.lhl.blog.dao.ArticleDao;
import com.lhl.blog.entity.Article;
import com.lhl.blog.util.Constant;
import com.lhl.blog.util.StringUtil;
import com.lhl.blog.util.SubStringHTML;

public class ArticleService
{
	private ArticleService()
	{

	}

	private static ArticleService instance;

	/**
	 * 构造实例
	 * 
	 * @return
	 */
	public synchronized static ArticleService getInstance()
	{

		if (instance == null)
		{
			instance = new ArticleService();
		}
		return instance;
	}

	public void saveArticle(Article article)
	{

		if (StringUtil.isNotEmpty(article.getId()))
		{
			article.setComment(SubStringHTML.subStringHTML(article.getContent(), Constant.CUT_LENTH, "......"));
			ArticleDao.getInstance().updateArticle(article);
		}
		else
		{
			String uuid = UUID.randomUUID().toString();
			article.setId(uuid);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			article.setPostTime(format.format(new Date()));
			article.setComment(SubStringHTML.subStringHTML(article.getContent(), Constant.CUT_LENTH, "......"));
			ArticleDao.getInstance().addArticle(article);
		}

	}

	public void deleteArticle(String id)
	{

		ArticleDao.getInstance().deleteArticle(id);
	}

	public Article getArticle(String id)
	{

		return ArticleDao.getInstance().getArticle(id);
	}

	public void updateArticle(Article article)
	{

		article.setComment(SubStringHTML.subStringHTML(article.getContent(), Constant.CUT_LENTH, "......"));
		ArticleDao.getInstance().updateArticle(article);
	}

	public void updateArticleReadCount(Article article)
	{

		ArticleDao.getInstance().updateArticleReadCount(article);
	}

	public List<Article> queryList(int itemId, int noStart, int pageSize)
	{

		return ArticleDao.getInstance().queryList(itemId, noStart, pageSize);
	}

	public int queryCountByTime(String startTime)
	{

		String endTime = StringUtil.getNextMonth(startTime);
		return ArticleDao.getInstance().queryCountByTime(startTime + "-00", endTime + "-00");
	}

	public List<Article> queryListByTime(String startTime, int noStart, int pageSize)
	{

		String endTime = StringUtil.getNextMonth(startTime);
		return ArticleDao.getInstance().queryListByTime(startTime + "-00", endTime + "-00", noStart, pageSize);
	}

	public int queryCount(int itemId)
	{

		return ArticleDao.getInstance().queryCount(itemId);
	}

	public List<Map<String, String>> queryArticleTime()
	{

		return ArticleDao.getInstance().queryArticleTime();
	}

}
