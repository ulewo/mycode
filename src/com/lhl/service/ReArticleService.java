package com.lhl.service;

import java.util.List;

import com.lhl.dao.ReArticleDao;
import com.lhl.entity.ReArticle;

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

	public List<ReArticle> queryListByArticleId(int articleId) throws Exception
	{

		return ReArticleDao.getInstance().queryListByArticleId(articleId);
	}

	public void addReArticle(ReArticle reArticle) throws Exception
	{

		String content = reArticle.getContent();
		content = content.replace("<", "&lt;");
		content = content.replace(" ", "&nbsp;");
		content = content.replace("\n", "<br>");

		if (content.contains("[face:"))
		{
			for (int i = 1; i <= 40; i++)
			{
				if (i < 10)
				{
					content = content.replace("[face:000" + i + "]", "<img src='images/face/t_000" + i + ".gif'/>");
				}
				else
				{
					content = content.replace("[face:00" + i + "]", "<img src='images/face/t_00" + i + ".gif'/>");
				}
			}
		}
		reArticle.setContent(content);
		ReArticleDao.getInstance().addReArticle(reArticle);
	}

	public List<ReArticle> queryList(int noStart, int pageSize) throws Exception
	{

		return ReArticleDao.getInstance().queryList(noStart, pageSize);
	}

	public int queryCount()
	{

		return ReArticleDao.getInstance().queryCount();
	}
}
